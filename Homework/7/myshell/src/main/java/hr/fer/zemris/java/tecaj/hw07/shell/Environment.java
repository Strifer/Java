package hr.fer.zemris.java.tecaj.hw07.shell;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;

import java.io.IOException;

/**
 * This interface represents the environment in which our shell operates. It
 * comes equipped with several methods for handling input and output as well as
 * methods for retriving and changing our environment's basic symbols.
 * 
 * @author Filip Džidić
 *
 */
public interface Environment {

	/**
	 * Reads a line of text. A line is considered to be terminated by any one of
	 * a line feed ('\n'), a carriage return ('\r'), or a carriage return
	 * followed immediately by a linefeed.
	 * 
	 * @return A String containing the contents of the line
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	String readLine() throws IOException;

	/**
	 * Writes the provided String.
	 * 
	 * @param text
	 *            the text being written
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	void write(String text) throws IOException;

	/**
	 * Writes the provided String and terminates the line.
	 * 
	 * @param text
	 *            the text being written
	 * @throws IOException
	 *             if na I/O error occurs
	 */
	void writeln(String text) throws IOException;

	/**
	 * Contains all defined commands
	 * 
	 * @return iterable over all the commands
	 */
	Iterable<ShellCommand> commands();

	/**
	 * Getter method for current multi-line symbol being used by the
	 * {@code Environment}. The multi-line symbol signifies the beginning of
	 * each line while inputting multi-line commands.
	 * 
	 * @return current multi-line symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Setter method for current multi-line symbol being used by the
	 * {@code Environment}. The multi-line symbol signifies the beginning of
	 * each line while inputting multi-line commands.
	 * 
	 * @param symbol
	 *            the new multi-line symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Getter method for current prompt symbol being used by the
	 * {@code Environment}. The prompt symbol signifies when the user is able to
	 * enter commands.
	 * 
	 * @return current prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Setter method for current prompt symbol being used by the
	 * {@code Environment}. The prompt symbol signifies when the user is able to
	 * enter commands.
	 * 
	 * @param symbol
	 *            the new prompt symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Getter method for current more-lines symbol which is used for connecting
	 * multi-line commands.
	 * 
	 * @return the current more-lines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Setter method for current more-lines symbol which is used for connecting
	 * multi-line commands.
	 * 
	 * @param symbol
	 *            the new more-lines symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
