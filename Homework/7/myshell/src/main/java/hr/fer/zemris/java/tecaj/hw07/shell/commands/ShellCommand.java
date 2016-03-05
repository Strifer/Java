package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.util.List;

/**
 * This class represent a single command inside our program. It comes with three
 * main methods, a method for executing the command and two methods for
 * retrieving basic information about the command.
 * 
 * @author Filip Džidić
 *
 */
public interface ShellCommand {
	/**
	 * Executes the command using provided arguments.
	 * 
	 * @param env
	 *            the {@code Environment} the command is being executed in
	 * @param arguments
	 *            {@code String} representation of all of the arguments being
	 *            sent to the command
	 * @return {@code ShellStatus} representing the status after execution
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns the command's defined name.
	 * 
	 * @return {@code String} representation of the command's name
	 */
	String getCommandName();

	/**
	 * Returns usage instructions of the command.
	 * 
	 * @return {@code List} containing all of the text descriping the command's
	 *         usage
	 */
	List<String> getCommandDescription();
}
