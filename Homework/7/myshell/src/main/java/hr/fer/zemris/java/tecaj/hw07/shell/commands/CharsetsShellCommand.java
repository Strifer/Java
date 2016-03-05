package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

/**
 * This class represents a <code>"charsets"</code> command inside our shell.
 * <p>
 * This command has no arguments. It lists all of the available Charsets found
 * on the user's computer.
 * 
 * @author Filip Džidić
 *
 */
public class CharsetsShellCommand implements ShellCommand {
	/** This static variable holds the name of the command. */
	private static final String NAME = "charsets";

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
		SortedMap<String, Charset> map = Charset.availableCharsets();
		for (Charset s : map.values()) {
			try {
				env.writeln(s.displayName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		list.add("Lists all available charsts found on the system");
		return Collections.unmodifiableList(list);
	}

}
