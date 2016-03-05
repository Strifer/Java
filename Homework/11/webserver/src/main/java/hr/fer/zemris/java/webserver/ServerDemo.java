package hr.fer.zemris.java.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class serves as a demonstrating class which showcases our server's
 * capabilities.
 * 
 * @author Filip Džidić
 *
 */
public class ServerDemo {
	/**
	 * The main method enables two commands for interacting with the server. <br>
	 * 1)start - starts the server <br>
	 * 2)stop - shuts down the server
	 * 
	 * @param args
	 *            one argument must be provided containing the path name to the
	 *            proper server properties
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Invalid number of arguments");
		}
		boolean serverStarted = false;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		SmartHttpServer server = new SmartHttpServer(args[0]);
		System.out
				.println("Welcome to the server demo program, please enter your commands");
		while (true) {
			System.out.print("> ");
			String line = reader.readLine().trim().toLowerCase();
			if (line.equals("start")) {
				if (serverStarted) {
					System.out.println("Server is already started.");
					continue;
				}
				server.start();
				serverStarted = true;
			} else if (line.equals("stop")) {
				if (!serverStarted) {
					System.out.println("Server hasn't been started yet.");
					continue;
				}
				server.stop();
			} else {
				System.out.println("Undefined command.");
			}
		}

	}
}
