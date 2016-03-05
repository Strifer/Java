package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a <code>"symbol"</code> command inside our shell.
 * <p>
 * This command can take either 1 or 2 arguments.<br>
 * The first argument represents a symbol VARIABLE of our shell. It can be on of
 * the following three options.<br>
 * <b>PROMPT</b> - the prompt symbol<br>
 * <b>MORELINES</b> - the morelines symbol<br>
 * <b>MULTILINE</b> - the multiline symbol<br>
 * The second argument mut be a single character.
 * <p>
 * Giving only 1 argument will prompt the command to print out the current value
 * of the provided symbol variable.<br>
 * Giving 2 arguments will change the variable's default value to the provided
 * character.
 * 
 * @author Filip Džidić
 *
 */
public class SymbolShellCommand implements ShellCommand {
	/** This static variable holds the name of the command. */
	private static final String NAME = "symbol";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		try {
			if (arguments == null) {
				env.writeln("Invalid number of arguments");
				return ShellStatus.CONTINUE;
			}
			String[] args = arguments.split("\\s+");
			if (args.length == 1) {
				switch (args[0]) {
					case "PROMPT":
						env.writeln("Symbol for " + args[0] + " is '"
								+ env.getPromptSymbol().charValue() + "'");
						break;
					case "MORELINES":
						env.writeln("Symbol for " + args[0] + " is '"
								+ env.getMorelinesSymbol().charValue() + "'");
						break;
					case "MULTILINE":
						env.writeln("Symbol for " + args[0] + " is '"
								+ env.getMultilineSymbol().charValue() + "'");
						break;
					default:
						env.writeln("Undefined parameter " + args[0]);
				}
			} else if (args.length == 2) {
				char[] c = args[1].toCharArray();
				if (c.length != 1) {
					env.writeln("New variable must be a single character: "
							+ args[1]);
					return ShellStatus.CONTINUE;
				}
				switch (args[0]) {
					case "PROMPT":
						env.writeln("Symbol for " + args[0] + " changed from '"
								+ env.getPromptSymbol().charValue() + "' to '"
								+ c[0] + "'");
						env.setPromptSymbol(Character.valueOf(c[0]));
						break;
					case "MORELINES":
						env.writeln("Symbol for " + args[0] + " changed from '"
								+ env.getMorelinesSymbol().charValue()
								+ "' to '" + c[0] + "'");
						env.setMorelinesSymbol(Character.valueOf(c[0]));
						break;
					case "MULTILINE":
						env.writeln("Symbol for " + args[0] + " changed from '"
								+ env.getMultilineSymbol().charValue()
								+ "' to '" + c[0] + "'");
						env.setMultilineSymbol(Character.valueOf(c[0]));
						break;
					default:
						env.writeln("Undefined parameter " + args[0]);
						break;
				}
			} else {
				env.writeln("Invalid arguments");
			}

		} catch (IOException e) {
			e.printStackTrace();
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
		list.add("Displays or changes the default prompt and multiline symbls used by the shell");
		list.add("Command accepts two argument, the first one being the symbol variable and the (optional) second one being a character representing the new symbol");
		return list;
	}

}
