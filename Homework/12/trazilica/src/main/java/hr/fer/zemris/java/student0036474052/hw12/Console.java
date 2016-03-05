package hr.fer.zemris.java.student0036474052.hw12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * <code>Console</code> represents a simple console program which enables a user
 * to search through a database of text documents. The database is searched with
 * the query command.
 *
 * @author Filip Džidić
 */
public class Console {
	/**
	 * Main method which runs the program.
	 * 
	 * @param args
	 *            a single path to the root file containing all documents
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public static void main(String[] args) throws IOException {
		Searcher srch = new Searcher(Paths.get(args[0]));
		System.out.println("Building vocabulary...");
		System.out.println("Vocabulary size is " + srch.wordCount()
				+ "words.\n");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		while (true) {
			System.out.print("Enter command > ");
			String line = reader.readLine().trim().toLowerCase();
			String[] command = line.split("\\s+", 2);
			if (command[0].equals("query")) {
				if (command.length != 2) {
					System.out.println("Invalid arguments detected");
					continue;
				}
				srch.buildQuery(command[1]);
				System.out.println("Query is: " + srch.getQuerySet());
				System.out.println("The top 10 results:");
				srch.buildTop10();
				srch.printResults();
				continue;
			} else if (command[0].equals("results")) {
				if (command.length != 1) {
					System.out
							.println("This command should not have any arguments");
					continue;
				}
				srch.printResults();
				continue;
			} else if (command[0].equals("type")) {
				if (command.length != 2) {
					System.out.println("Invalid arguments detected");
					continue;
				}
				int i = 0;
				try {
					i = Integer.parseInt(command[1].trim());
				} catch (NumberFormatException e) {
					System.out
							.println("Invalid argument, please provide a valid index");
					continue;
				}
				TreeMap<Double, Path> results = srch.getResults();
				if (i > 9) {
					System.out
							.println("Invalid argument, please provide a valid index");
					continue;
				}
				int index = 0;
				for (Entry<Double, Path> entry : results.entrySet()) {
					if (index > 9) {
						break;
					}
					if (index != i) {
						index++;
						continue;
					} else {
						System.out
								.println("----------------------------------------------------------------");
						System.out.println("Document "
								+ entry.getValue().toString());
						System.out.println(new String(Files.readAllBytes(entry
								.getValue()), StandardCharsets.UTF_8));
						System.out
								.println("----------------------------------------------------------------");
						break;
					}

				}
				continue;
			} else if (command[0].equals("exit")) {
				if (command.length == 1) {
					break;
				} else {
					System.out
							.println("Invalid argument. This command should have no arguments");
					continue;
				}
			} else {
				System.out.println("Unknown command.");
				continue;
			}
		}
	}
}
