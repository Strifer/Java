package hr.fer.zemris.java.custom.scripting.exec.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class demonstrates the functionality of our
 * <code>SmartScriptEngine</code> class.
 * 
 * @author Filip Džidić
 *
 */
public class EngineDemo {
	/**
	 * Main method reads script, parses it and executes it using
	 * <code>SmartScriptEngine</code>. Results are written to console.
	 * 
	 * @param args
	 *            no arguments should be provided
	 * @throws IOException
	 *             if an IOError occurs during reading
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Invalid number of arguments");
			return;
		}
		String documentBody = new String(Files.readAllBytes(Paths
				.get("./examples/doc1.txt")));
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters,
						persistentParameters, cookies)).execute();
	}
}
