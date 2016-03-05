package hr.fer.zemris.java.hw2;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
/** 
 * This class demonstrates the functionality of the parser.
 * 
 * @author Filip Džidić
 *
 */
public class SmartScriptTester {
	/**
	 * The main method receives a single argument which should be the filename to a text document we wish to parse.
	 * We test the functionality of our parser by parsing through the original and then the text representation of the parsed original.
	 * @param args pathname to the document 
	 * @throws IOException if path name to document is invalid
	 * @throws SmartScriptParserException if unable to parse through document
	 * @throws Exception if class failure
	 */
	public static void main(String[] args) throws IOException {
		if (args.length!=1) {
			System.err.println("Please provide a single argument: path to file");
			System.exit(1);
		}
		
		String docBody = new String (Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		SmartScriptParser parsy = null;
		try {
			parsy = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException ex) {
			System.out.println("Unable to parse document");
			System.exit(-1);
		} catch (Exception ex) {
			System.out.println("You have failed this class");
			ex.printStackTrace();
			System.exit(-1);
		}
		String original = parsy.getDocumentNode().toString();
		SmartScriptParser parsy2 = new SmartScriptParser(original);
		String parsedoriginal = parsy2.getDocumentNode().toString(); 
		System.out.println("Parsed documents are equal:"+parsedoriginal.equals(original));
	}
}
