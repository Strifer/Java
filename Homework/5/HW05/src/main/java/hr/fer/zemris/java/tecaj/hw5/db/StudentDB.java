package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.data.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.data.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.filters.QueryFilter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class serves as the main program through which we interact with our
 * database. During CLI interaction only two main commands are supported, query
 * and exit.
 * 
 * @author Filip Džidić
 *
 */
public class StudentDB {
	
	
	
	/**
	 * Used for storing output for testing purposes.
	 */
	private static String output;
	
	/**
	 * The main method continuously reads user input and enables interaction
	 * with the database. Two commands are supported and case is important so
	 * keep that in mind.
	 * <p>
	 * <b>query</b> - represents a single query to our database <br>
	 * <b>exit</b> exits the program
	 * <p>General format of <b>query</b> is ...<br>
	 * <p><code>query [field][operator][string literal] and ...</code><br>
	 * <p>[field] - <code>lastName firstName jmbag</code><br>
	 * [operator] - <code> &#60; &#60;= = != &#62;= &#62; </code><br>
	 * [string literal] - only the = operator has special restriction where * in
	 * string represents a meta character signifying a general substring of
	 * characters <br>
	 * <b>and</b> - links multiple expressions with a logical and operation <br>
	 * 
	 * @param args does not retrieve any CLI arguments on first call
	 * @throws IOException if database file is not found
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./prva.txt"),
				StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase(lines);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(System.in)));
		while (true) {
			System.out.print("> ");
			String line = reader.readLine();
			if (line.trim().equals("query")) {
				System.err.println("No arguments provided.");
				continue;
			}
			String[] query = line.split("\\s", 2);
			if (query[0].equals("exit")) {
				break;
			}
			if (!query[0].equals("query")) {
				System.err.print("Undefined command, please try again\n");
				continue;
			}
			QueryFilter filter = null;
			try {
				filter = new QueryFilter(query[1]);
			} catch (IllegalArgumentException e) {
				System.err.print(e.getMessage() + "\nPlease try again\n");
				continue;
			}
			if (filter.getJmbag().isPresent()) {
				StudentRecord record = db.forJMBAG(filter.getJmbag().get());
				if (filter.accepts(record)) {
					output=printWithIndex(record);
				} else {
					output=printWithIndex(null);
				}
				System.out.println(output);
				continue;
			}
			List<StudentRecord> relation = db.filter(filter);
			output=printRelation(relation);
			System.out.print(output);
		}
	}
	
	/**
	 * This method is used to write output when retrieving a record from its jmbag index.
	 * @param record <code>StudentRecord</code> which is being printed
	 * @return <code>String</code> representation of output
	 */
	private static String printWithIndex(StudentRecord record) {
		StringBuilder buildy = new StringBuilder();
		buildy.append("Using index for record retrieval.\n");
		if (record == null) {
			buildy.append("Records selected:0");
			return buildy.toString();
		}
		buildy.append(printBorders(record.getLastName().length(), record.getFirstName()
				.length()));
		buildy.append("| " + record.getJmbag() + " | "
				+ record.getLastName() + " | " + record.getFirstName() + " | "
				+ record.getFinalGrade() + " |\n");
		buildy.append(printBorders(record.getLastName().length(), record.getFirstName()
				.length()));
		buildy.append("Records selected:1\n");
		return buildy.toString();
	}
	
	/**
	 * This method is used to create the output borders
	 * @param maxSurname the longest surname in our list of records
	 * @param maxName the longest first name in our list of records
	 * @return <code>String</code> representation of borders
	 */
	private static String printBorders(int maxSurname, int maxName) {
		StringBuilder buildy = new StringBuilder();
		buildy.append("+============+");
		for (int i = 0; i < maxSurname + 2; i++) {
			buildy.append("=");
		}
		buildy.append("+");
		for (int i = 0; i < maxName + 2; i++) {
			buildy.append("=");
		}
		buildy.append("+===+\n");
		return buildy.toString();
	}
	
	/**
	 * This method is used to print all the records within our filtered relation.
	 * @param relation a list of <code>StudentRecord</code>
	 * @return <code>String</code> representation of output
	 */
	private static String printRelation(List<StudentRecord> relation) {
		int longestName = 0;
		int longestSurname = 0;
		if (relation.size() == 0) {
			return "Records selected:0\n";
		}
		for (int i = 0; i < relation.size(); i++) {
			int nameLength = relation.get(i).getFirstName().length();
			int surnameLength = relation.get(i).getLastName().length();
			if (nameLength > longestName) {
				longestName = nameLength;
			}
			if (surnameLength > longestSurname)
				longestSurname = surnameLength;
		}
		StringBuilder buildy = new StringBuilder();
		buildy.append(printBorders(longestSurname, longestName));
		for (StudentRecord rec : relation) {
			buildy.append("| " + rec.getJmbag() + " | ");
			buildy.append(rec.getLastName());
			for (int i = 0, max = rec.getLastName().length(); i < longestSurname
					- max; i++) {
				buildy.append(" ");
			}
			buildy.append(" | ");
			buildy.append(rec.getFirstName());
			for (int i = 0, max = rec.getFirstName().length(); i < longestName
					- max; i++) {
				buildy.append(" ");
			}
			buildy.append(" | " + rec.getFinalGrade() + " |\n");

		}
		buildy.append(printBorders(longestSurname, longestName));
		buildy.append(("Records selected:" + relation.size()+"\n"));
		return buildy.toString();

	}
	/**
	 * Getter method for output
	 * @return returns created Output
	 */
	public static String getOutput() {
		return output;
	}

}