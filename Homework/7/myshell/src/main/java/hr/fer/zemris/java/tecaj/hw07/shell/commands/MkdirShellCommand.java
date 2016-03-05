package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a <code>"mkdir"</code> command inside our shell.
 * <p>
 * This command creates a directory provided by the user.<br>
 * This command expects a single argument, a path to the directory the user
 * wishes to create. This path must represent a non existing directory. All of
 * the necessary subdirectories inside the pathname will be created as well.
 * 
 * @author Filip Džidić
 *
 */
public class MkdirShellCommand implements ShellCommand {
	/** This static variable holds the name of the command. */
	private static final String NAME = "mkdir";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			if (arguments == null) {
				env.writeln("Please provide a single argument");
				return ShellStatus.CONTINUE;
			}
			List<String> list = PathUtility.splitPaths(arguments);
			if (list.size() != 1) {
				env.writeln("Invalid number of arguments");
				return ShellStatus.CONTINUE;
			}
			File file = Paths.get(PathUtility.trimQuotes((list.get(0))))
					.toFile();
			if (file.exists()) {
				env.writeln("Cannot make directory, "
						+ file.getAbsolutePath().toString() + " already exists");
				return ShellStatus.CONTINUE;
			}
			if (!file.mkdirs()) {
				env.writeln("Error in creating " + file.getAbsolutePath());
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Makes new directory on the file system along with all of it's subdirectories if needed");
		list.add("Command accepts a single argument, a valid pathname to the new directory");
		return list;
	}

}
