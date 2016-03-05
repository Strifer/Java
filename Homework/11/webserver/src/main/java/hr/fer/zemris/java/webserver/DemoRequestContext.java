package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import java.io.IOException;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This demonstration class is used for exemplifying the functionality of the
 * <code>RequestContext</code> class.
 * 
 * @author Filip Džidić
 *
 */
public class DemoRequestContext {
	/**
	 * The main method invokes the two generating methods and creates new files
	 * which showcase the program's functionality.
	 * 
	 * @param args
	 *            no arguments should be provided
	 * @throws IOException
	 *             if an IOError occurs
	 */
	public static void main(String[] args) throws IOException {
		demo1("./primjer1.txt", "ISO-8859-2");
		demo1("./primjer2.txt", "UTF-8");
		demo2("./primjer3.txt", "UTF-8");
	}

	/**
	 * Generates an HTTP header example using <code>RequestContext</code>. The
	 * first generating method, it does not use any cookies.
	 * 
	 * @param filePath
	 *            the file in which we're creating the file
	 * @param encoding
	 *            the encoding used for encoding the data
	 * @throws IOException
	 *             if an IOError occurs during writing
	 */
	private static void demo1(String filePath, String encoding)
			throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}

	/**
	 * Generates an HTTP header example using <code>RequestContext</code>. The
	 * second generating method, it uses cookies.
	 * 
	 * @param filePath
	 *            the file in which we're creating the file
	 * @param encoding
	 *            the encoding used for encoding the data
	 * @throws IOException
	 *             if an IOError occurs during writing
	 */
	private static void demo2(String filePath, String encoding)
			throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1",
				"/", false));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/", false));
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
}