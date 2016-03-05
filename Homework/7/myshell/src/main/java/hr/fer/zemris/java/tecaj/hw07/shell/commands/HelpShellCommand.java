package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a <code>"help"</code> command inside our shell.
 * <p>
 * If no arguments are provided this command lists all of the available commands
 * in our shell.<br>
 * If a command name is provided as the argument that command's description and
 * usage guidlines will be printed on the screen.
 * 
 * @author Filip Džidić
 *
 */
public class HelpShellCommand implements ShellCommand {
	/** This static variable holds the name of the command. */
	private final static String NAME = "help";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Iterator<ShellCommand> it = env.commands().iterator();
		try {
			if (arguments == null) {
				while (it.hasNext()) {
					env.writeln(it.next().getCommandName());
				}
				return ShellStatus.CONTINUE;
			}
			List<String> list = PathUtility.splitPaths(arguments);
			if (list.size() != 1) {
				env.writeln("Invalid number of arguments");
				return ShellStatus.CONTINUE;
			}
			String command = list.get(0);
			while (it.hasNext()) {
				ShellCommand cm = it.next();
				if (cm.getCommandName().equals(command)) {
					env.write(formatDescrption(cm.getCommandDescription()));
					return ShellStatus.CONTINUE;
				}
			}
			env.writeln(command + ": command not found");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * This private method is used for formatting the description.
	 * 
	 * @param commandDescription
	 *            a list containing all the lines inside a command's description
	 * @return a <code>String</code> representation of the command
	 */
	private String formatDescrption(List<String> commandDescription) {
		StringBuilder sb = new StringBuilder();
		for (String s : commandDescription) {
			sb.append(s + "\n");
		}
		return sb.toString();
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		ArrayList<String> list = new ArrayList<>();
		list.add("Lists the names and descriptions of all available commands in the shell");
		list.add("If no argument is provided, lists all the names");
		list.add("If a commandname is provided, lists the description of that command");
		return Collections.unmodifiableList(list);
	}

}
