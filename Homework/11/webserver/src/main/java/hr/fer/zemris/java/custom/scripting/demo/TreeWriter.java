package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class serves as a demonstration class showcasing the functionality of
 * the visitor design pattern.
 * 
 * @author Filip Džidić
 *
 */
public class TreeWriter {
	/**
	 * Main method accepts a single argument, path to file which is parsed. The
	 * parsed document is turned back into text and outputted on the screen.
	 * 
	 * @param args
	 *            a single argument path to file being parsed
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Invalid number of arguments");
			return;
		}

		String document = new String(Files.readAllBytes(Paths.get(args[0])));
		SmartScriptParser parser = new SmartScriptParser(document);
		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);
	}

	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.toString());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.toString());

		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node.toString());

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}

		}

	}
}
