package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a <code>"exit"</code> command inside our shell.
 * <p>
 * This command has no arguments. It terminates the shell currently being run.
 * 
 * @author Filip Džidić
 *
 */
public class ExitShellCommand implements ShellCommand {
	private final static String NAME = "exit";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments != null) {
			try {
				env.writeln("Invalid number of arguments");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		ArrayList<String> list = new ArrayList<>();
		list.add("Terminates shell");
		return Collections.unmodifiableList(list);
	}

}
