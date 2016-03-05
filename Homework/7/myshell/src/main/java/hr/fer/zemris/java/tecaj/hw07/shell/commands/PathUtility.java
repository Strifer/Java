package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This utility class offers two methods used for parsing user input when
 * providing commands and arguments.
 * 
 * @author Filip Džidić
 *
 */
public class PathUtility {
	/**
	 * Parses the argument part of user's input. Arguments are divided by
	 * whitespaces unless they're in quotes. Quotes within quotes must be
	 * escaped with the '\' character.
	 * 
	 * @param args
	 *            <code>String</code> representation of all the arguments we're
	 *            passing to the command
	 * @return a <code>List</code> of all the arguments this method has parsed
	 */
	public static List<String> splitPaths(String args) {
		List<String> matchList = new ArrayList<String>();

		// this regex matches everything except whitespaces, ignores whitespaces
		// within quotes
		// you CAN try breaking it by giving a very... unorthodox name to your
		// files if you really want to unfortunately windows users won't be able
		// to
		// play with things like quotes in filenames but linux users are welcome
		// to try
		Pattern regex = Pattern
				.compile("([\"'])(?:(?=(\\\\?))\\2.)*?\\1+|\\S+");

		Matcher regexMatcher = regex.matcher(args);
		while (regexMatcher.find()) {
			matchList.add(regexMatcher.group());
		}
		return matchList;
	}

	/**
	 * Trims quotes from a directory or file name.
	 * 
	 * @param directory
	 *            the directory or file path which is encased in quotes
	 * @return the same path but without the quotes
	 */
	public static String trimQuotes(String directory) {
		if (directory.charAt(0) == '"'
				&& directory.charAt(directory.length() - 1) == '"') {
			return directory.substring(1, directory.length() - 1).replaceAll(
					"\\\\\"", "\"");
		} else {
			return directory;
		}
	}

}
