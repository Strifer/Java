package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The class <code>SmartHTTPServer</code> models a simple HTTP server offering
 * several basic capablities.
 * <p>
 * Clients can connect and view text, images and even execute
 * <code>SmartScript</code> scripts. The server also supports distributing
 * cookies for recognizing and keeping track of session users.
 * 
 * @author Filip Džidić
 *
 */
public class SmartHttpServer {
	/** the server's address */
	@SuppressWarnings("unused")
	private String address;
	/** the server's port */
	private int port;
	/** number of threads used in parallelization */
	private int workerThreads;
	/** lifetime of cookies */
	private int sessionTimeout;
	/**
	 * collection which holds all the possible mimetypes that our server can
	 * recognize
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/** the main server thread which serves clients */
	private ServerThread serverThread;
	/** the main executor pool which executes small delegated threads */
	private ExecutorService threadPool;
	/** path to file containing all our images and scripts */
	private Path documentRoot;
	/**
	 * collection which holds all the possible web workers defined in our server
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<String, IWebWorker>();
	/** collection which holds all client session for quick retrieval */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/** random number generator */
	private Random sessionRandom = new Random();
	/** timer used for deleting old session cookies */
	private final Timer timer = new Timer();

	/**
	 * This object's main jobs is to remove every cookie who has lived past its
	 * magic cage.
	 */
	private final TimerTask killSessions = new TimerTask() {

		@Override
		public void run() {
			for (SessionMapEntry entry : sessions.values()) {
				if (entry.validUntil > System.currentTimeMillis() / 1000) {
					sessions.remove(entry.sid);
				}
			}
		}

	};

	/**
	 * Constructs all of the necessary server component from provided
	 * properties.
	 * 
	 * @param configFileName
	 *            path to server configuration file
	 * @throws FileNotFoundException
	 *             if file not found
	 * @throws IOException
	 *             if an IO error happens
	 */
	public SmartHttpServer(String configFileName) throws FileNotFoundException,
			IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(configFileName));
		this.address = properties.getProperty("server.address");
		this.port = Integer.parseInt(properties.getProperty("server.port"));
		this.workerThreads = Integer.parseInt(properties
				.getProperty("server.workerThreads"));
		this.sessionTimeout = Integer.parseInt(properties
				.getProperty("session.timeout"));
		this.documentRoot = Paths.get(properties
				.getProperty("server.documentRoot"));
		Properties mimeProperties = new Properties();
		mimeProperties.load(new FileInputStream(properties
				.getProperty("server.mimeConfig")));
		for (Object mimeName : mimeProperties.keySet()) {
			mimeTypes.put((String) mimeName,
					mimeProperties.getProperty((String) mimeName));
		}
		Properties workerProperties = new Properties();
		workerProperties.load(new FileInputStream(properties
				.getProperty("server.workers")));
		for (Object workerName : workerProperties.keySet()) {
			Object newObject = null;
			try {
				Class<?> referenceToClass = this
						.getClass()
						.getClassLoader()
						.loadClass(
								workerProperties.getProperty(workerName
										.toString()));
				newObject = referenceToClass.newInstance();
			} catch (ReflectiveOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IWebWorker iww = (IWebWorker) newObject;
			workersMap.put(workerName.toString(), iww);
		}
		timer.schedule(killSessions, 60 * 1000, 5 * 60 * 1000);
		serverThread = null;
	}

	/**
	 * Starts server by starting the main server thread.
	 */
	protected synchronized void start() {
		if (serverThread != null) {

		}
		serverThread = new ServerThread();
		threadPool = Executors.newFixedThreadPool(workerThreads);
		System.out.println("Starting server...");
		serverThread.start();
	}

	/**
	 * Shuts down the server by closing all active and inactive threads.
	 */
	protected synchronized void stop() {
		if (serverThread.isAlive()) {
			while (true) {
				serverThread.interrupt();
				break;
			}
			System.out.println("Exiting server...");
			threadPool.shutdown();
			timer.cancel();
			while (!threadPool.isTerminated()) {
				try {
					threadPool.awaitTermination(800, TimeUnit.MILLISECONDS);
				} catch (InterruptedException ignorable) {
				}

			}
			System.out.println("Server is shutting down.");
			System.exit(1);

		}
	}

	/**
	 * This helper class keeps track of single session cookie entries.
	 * 
	 * @author Filip Džidić
	 *
	 */
	private static class SessionMapEntry {
		String sid;
		long validUntil;
		Map<String, String> map;
	}

	/**
	 * The <code>ServerThread</code> is a class which delegates most of its work
	 * to helper threads
	 * 
	 * @author Filip Džidić
	 *
	 */
	protected class ServerThread extends Thread {

		private ServerSocket socket;

		@Override
		public void interrupt() {
			super.interrupt();
			try {
				socket.close();
			} catch (IOException ignorable) {

			}
		}

		@Override
		public void run() {
			try {
				socket = new ServerSocket();
				socket.bind(new InetSocketAddress((InetAddress) null, port));

			} catch (IOException e) {
				System.err
						.println("Unable to create new server socket. Exitting program");
				return;
			}
			while (true) {
				try {
					Socket client = socket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);

				} catch (SocketException ex) {
					break;
				} catch (IOException e) {
					System.err
							.println("Unable to create new client socket. Exiting program");
					return;
				}
			}
		}
	}

	/**
	 * This class is the main backbone worker of our program. It handles all
	 * main IO operations between a client as well as keeping track of cookies.
	 * 
	 * @author Filip Džidić
	 *
	 */
	private class ClientWorker implements Runnable {
		/** name of the default ServerWorker classess */
		private final static String WORKER_PACKAGE = "hr.fer.zemris.java.webserver.workers";
		/** name of the extension tag used when executing certain worker classes */
		private final static String CONVENTION = "/ext/";
		/** a normal endpoint communication link between two machines */
		private Socket csocket;
		/** used for handling input */
		private PushbackInputStream istream;
		/** used for handling output */
		private OutputStream stream;
		/** HTTP header version is stored here */
		private String version;
		/** HTTP header method is stored here */
		private String method;
		/** collection of general parameters used by our server */
		private Map<String, String> params = new HashMap<String, String>();
		/** collection of permanent parameters used by our server */
		private Map<String, String> permParams = null;
		/** collection of all the defined cookies used by our server */
		private List<RequestContext.RCCookie> outputCookies = new ArrayList<>();
		/** session ID of current client */
		private String SID;

		/**
		 * Basic constructor intializes our <code>ClientWorker</code> with a
		 * normal socket.
		 * 
		 * @param csocket
		 *            the <code>Socket</code> between our client and the server
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				stream = csocket.getOutputStream();
				List<String> requests = readRequest();
				if (requests.isEmpty()) {
					sendError(stream, 400, "Bad HTTP Request");
					return;
				}
				String requestedPath = extract(requests.get(0));
				if (requestedPath == null) {
					return;
				}
				checkSession(requests);
				String[] pathAndParams = requestedPath.split("\\?");
				String path;
				String paramString;
				if (pathAndParams.length == 2) {
					path = pathAndParams[0];
					paramString = pathAndParams[1];
					parseParameters(paramString);
				} else {
					path = requestedPath;
				}
				Path reqPath = (Paths.get(documentRoot.toString() + path));
				if (!reqPath.toAbsolutePath().toString()
						.startsWith(documentRoot.toAbsolutePath().toString())) {
					sendError(stream, 403, "Forbidden access");
					return;
				}
				if (checkConventions(path)) {
					return;
				}
				if (workersMap.get(path) != null) {
					workersMap.get(path).processRequest(
							new RequestContext(stream, params, permParams,
									outputCookies));
					return;
				}
				if (!Files.exists(reqPath) || !Files.isReadable(reqPath)) {
					sendError(stream, 404, "File not found");
					return;
				}

				String extension = reqPath
						.getFileName()
						.toString()
						.substring(
								reqPath.getFileName().toString()
										.lastIndexOf('.') + 1);

				String mimeType = SmartHttpServer.this.mimeTypes.get(extension);

				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}

				if (extension.equals("smscr")) {
					String documentBody = new String(
							Files.readAllBytes(reqPath));
					new SmartScriptEngine(
							new SmartScriptParser(documentBody)
									.getDocumentNode(),
							new RequestContext(stream, params, permParams,
									outputCookies)).execute();

					return;
				}

				RequestContext rc = new RequestContext(stream, params,
						permParams, outputCookies);
				rc.setMimeType(mimeType);
				rc.setStatusCode(200);
				rc.setStatusText("OK");
				rc.write(Files.readAllBytes(reqPath));
				stream.flush();
				csocket.close();

			} catch (IOException e) {

			} finally {
				try {
					csocket.close();
				} catch (IOException e) {

				}
			}

		}

		/**
		 * Checks whether or not a provided HTTP path follows the standard
		 * extension format.
		 * 
		 * @param path
		 *            the HTTP path being tested
		 * @return boolean indicating status of check
		 * @throws IOException
		 *             if an IO error occurs
		 */
		private boolean checkConventions(String path) throws IOException {
			if (!path.startsWith(CONVENTION)) {
				return false;
			}
			path = path.replaceFirst(CONVENTION, "");
			String packageName = WORKER_PACKAGE + '.' + path;
			Object newObject;
			try {
				Class<?> referenceToClass = this.getClass().getClassLoader()
						.loadClass(packageName);
				newObject = referenceToClass.newInstance();
			} catch (ReflectiveOperationException e) {
				sendError(stream, 404, "Undefined worker");
				return true;
			}
			IWebWorker iww = (IWebWorker) newObject;
			iww.processRequest(new RequestContext(stream, params, permParams,
					outputCookies));

			return true;
		}

		/**
		 * Parses a cookie and builds an associative map for every parameter
		 * defined within.
		 * 
		 * @param paramString
		 *            <code>String</code> representation of a cookie entry
		 */
		private void parseParameters(String paramString) {
			String[] params = paramString.split("\\&");
			for (String parameter : params) {
				String[] entry = parameter.split("=");
				this.params.put(entry[0], entry[1]);
			}

		}

		/**
		 * This method extracts default member variables from a given cookie
		 * 
		 * @param line
		 *            <code>String</code> holding the names and values of all
		 *            the parameters
		 * @return path if each element is correct
		 * @throws IOException
		 *             if an IO error occurs
		 */
		private String extract(String line) throws IOException {
			String[] fields = line.split(" ");
			if (fields.length != 3) {
				sendError(stream, 400, "Bad HTTP Request");
				csocket.close();
				return null;
			}
			method = fields[0].toUpperCase();
			if (!method.equals("GET")) {
				sendError(stream, 400, "Unsupported method");
				csocket.close();
				return null;
			}
			String requestedPath = fields[1];
			version = fields[2].toUpperCase();
			if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
				sendError(stream, 400, "Unsupported HTTP version");
				csocket.close();
				return null;
			}
			return requestedPath;

		}

		/**
		 * This method reads the HTTP header and splits it into lines one by
		 * one.
		 * 
		 * @return <code>List</code> of all the lines contained within the
		 *         generated HTTP header
		 * @throws IOException
		 *             if an IO error occurs
		 */
		private List<String> readRequest() throws IOException {
			byte[] header = readHeader();
			if (header == null) {
				sendError(stream, 400, "Bad HTTP Request");
			}
			String request = new String(header, StandardCharsets.ISO_8859_1);
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : request.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * This final state automata keeps reading bytes until it hits the
		 * substring of \A\A or \r\n\r\n.
		 * 
		 * The end result is a fully complete HTTP header.
		 * 
		 * @return a parsed HTTP header in bytes
		 * @throws IOException
		 *             if an IO error occurs
		 */
		private byte[] readHeader() throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = istream.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
					case 0:
						if (b == 13) {
							state = 1;
						} else if (b == 10)
							state = 4;
						break;
					case 1:
						if (b == 10) {
							state = 2;
						} else
							state = 0;
						break;
					case 2:
						if (b == 13) {
							state = 3;
						} else
							state = 0;
						break;
					case 3:
						if (b == 10) {
							break l;
						} else
							state = 0;
						break;
					case 4:
						if (b == 10) {
							break l;
						} else
							state = 0;
						break;
				}
			}
			return bos.toByteArray();
		}

		/**
		 * Generates a default error message with certain standard parameters.
		 * 
		 * @param cos
		 *            the outputStream
		 * @param statusCode
		 *            statuseCode which specifically describes men
		 * @param statusText
		 * @throws IOException
		 */
		private void sendError(OutputStream cos, int statusCode,
				String statusText) throws IOException {
			RequestContext rc = new RequestContext(stream, params, permParams,
					outputCookies);
			rc.setStatusCode(statusCode);
			rc.setStatusText(statusText);
			rc.write("HTTP/1.1 " + statusCode + " " + statusText + "\r\n"
					+ "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n"
					+ "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n");
			rc.setMimeType("text/plain");

		}

		/**
		 * Generates a randomly produced <code>String</code> which is 20
		 * characters long.
		 * 
		 * @return
		 */
		private String getRandomString() {
			char[] letters = new char[20];
			for (int i = 0; i < letters.length; i++) {
				letters[i] = (char) (sessionRandom.nextInt(26) + 65);
			}
			return new String(letters);
		}

		/**
		 * Updates all the necessary components whenever a new session entry is
		 * replaced or created.
		 */
		private void updateSessionEntry() {
			String sid = getRandomString();
			SessionMapEntry entry = new SessionMapEntry();
			entry.sid = sid;
			entry.validUntil = System.currentTimeMillis() / 1000
					+ sessionTimeout;
			entry.map = new ConcurrentHashMap<String, String>();
			this.SID = sid;
			this.permParams = entry.map;
			this.outputCookies.add(new RCCookie("sid", SID, null, "127.0.0.1",
					"/", true));
			sessions.put(sid, entry);
		}

		/**
		 * This method keeps track and assigns session cookies to clients to
		 * enable differentiation.
		 * 
		 * @param List
		 *            of all the lines contained in the HTTPHeader
		 */
		private synchronized void checkSession(List<String> request) {

			String sidCandidate = null;
			for (String headerLine : request) {
				if (!headerLine.startsWith("Cookie:")
						&& !headerLine.startsWith("Set-Cookie:")) {
					continue;
				}
				String[] cookies = headerLine.split(";");
				for (String cookie : cookies) {
					String[] cookieParts = cookie.split(" ");
					if (cookieParts[1].split("=")[0].equals("sid")) {
						sidCandidate = cookieParts[1].split("=")[1].replaceAll(
								"\"", "");
						break;
					} else {
						continue;
					}
				}
			}

			// no session cookies so we create one
			if (sidCandidate == null) {
				updateSessionEntry();
			}

			else {

				SessionMapEntry sessionMapEntry = sessions.get(sidCandidate);

				if (sessionMapEntry == null) {
					updateSessionEntry();
				}

				else if (System.currentTimeMillis() / 1000 > sessionMapEntry.validUntil) {
					sessions.remove(sessionMapEntry.sid);
					updateSessionEntry();
				}

				else {
					sessionMapEntry.validUntil = System.currentTimeMillis()
							/ 1000 + sessionTimeout;
					this.permParams = sessionMapEntry.map;

				}

			}

		}
	}

}
