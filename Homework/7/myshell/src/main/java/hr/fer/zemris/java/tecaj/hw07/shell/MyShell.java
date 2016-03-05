package hr.fer.zemris.java.tecaj.hw07.shell;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.TreeShellCommand;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Class <code>MyShell</code> represents the main program through which the user
 * can interact with his computer while executing predefined shell commands.
 * 
 * @author Filip Džidić
 *
 */
public class MyShell {
	/** This map contains all of the available commands in our shell */
	private static Map<String, ShellCommand> commands = new TreeMap<String, ShellCommand>();

	/**
	 * This class represents a concrete implementation of the
	 * <code>Environment</code> interface.
	 * 
	 * @param reader
	 *            object which reads user input, reads System.in by default
	 * @param writer
	 *            object which writes program output, writes on System.out by
	 *            default
	 * @param promptsymbol
	 *            the prompt symbol signifies when the user can enter commands,
	 *            '>' by default
	 * @param morelinessymbol
	 *            the morelinessymbol is added to the end of each inputted line
	 *            when the user wishe to input their command in multiple lines,
	 *            '\' by default
	 * @param multilinesymbol
	 *            the multiline symbol signifies the beginning of each multiline
	 *            command input
	 * @author Filip Džidić
	 *
	 */
	private static class EnvironmentImpl implements Environment {
		/** Reads user input */
		private BufferedReader reader;
		/** Writes program output */
		private BufferedWriter writer;

		/** Represents the prompt symbol. */
		private Character promptsymbol;
		/** Represents the morelines symbol. */
		private Character morelinessymbol;
		/** Represents the multiline symbol. */
		private Character multilinesymbol;

		/**
		 * Default constructor initializes all of the neccessary class variables
		 * to their default values.
		 */
		public EnvironmentImpl() {
			reader = new BufferedReader(new InputStreamReader(System.in));
			writer = new BufferedWriter(new OutputStreamWriter(System.out));
			promptsymbol = Character.valueOf('>');
			morelinessymbol = Character.valueOf('\\');
			multilinesymbol = Character.valueOf('|');
		}

		@Override
		public String readLine() throws IOException {
			return reader.readLine();
		}

		@Override
		public void write(String text) throws IOException {
			writer.write(text);
			writer.flush();
		}

		@Override
		public void writeln(String text) throws IOException {
			writer.write(text.concat("\n"));
			writer.flush();
		}

		@Override
		public Iterable<ShellCommand> commands() {
			return new Iterable<ShellCommand>() {
				@Override
				public Iterator<ShellCommand> iterator() {
					return new Iterator<ShellCommand>() {
						private Iterator<ShellCommand> it = MyShell.commands
								.values().iterator();

						@Override
						public boolean hasNext() {
							return it.hasNext();
						}

						@Override
						public ShellCommand next() {
							try {
								return it.next();
							} catch (NoSuchElementException e) {
								throw new NoSuchElementException(
										"No more commands left.");

							}
						}
					};
				}
			};
		}

		@Override
		public Character getMultilineSymbol() {
			return multilinesymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			multilinesymbol = symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return promptsymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			promptsymbol = symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return morelinessymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			morelinessymbol = symbol;

		}

	}

	/**
	 * This main method executes our program. It continues to run and receive
	 * user input until the user enters the exit command.
	 * 
	 * @param args
	 *            no arguments should be provided
	 * @throws IOException
	 *             if some kind of IO error happens when writing output to a
	 *             file
	 */
	public static void main(String[] args) throws IOException {
		fillCommands();
		MyShell.EnvironmentImpl environment = new EnvironmentImpl();
		environment.writeln("Welcome to MyShell v 1.0");
		while (true) {
			environment.write(environment.getPromptSymbol() + " ");
			String[] line = readLineOrLines(environment).trim()
					.split("\\s+", 2);
			String commandName = line[0].trim();
			ShellCommand command = commands.get(commandName);
			if (command == null) {
				environment.writeln("Unknown command");
				continue;
			}
			ShellStatus status;
			if (line.length == 1) {
				status = command.executeCommand(environment, null);
			} else {

				status = command.executeCommand(environment, line[1].trim());
			}
			if (status == ShellStatus.TERMINATE) {
				break;
			}
		}
	}

	/**
	 * This method reads user input while entering commands. If the user ends
	 * his lines with the multiline symbol he can continue more and more lines
	 * until he stops providing the multiline symbol at the end of each command.
	 * 
	 * @param e
	 *            reference to the environment which is handling the input and
	 *            output
	 * @return a <code>String</code> representation of a single command the user
	 *         has entered
	 * @throws IOException
	 */
	private static String readLineOrLines(Environment e) throws IOException {
		StringBuilder build = new StringBuilder();
		build.append(e.readLine());
		String commandName = build.toString();
		if (commandName.endsWith(" " + e.getMorelinesSymbol().toString())) {
			commandName = commandName.substring(0, commandName.length() - 1);
		}
		while (build.toString().endsWith(e.getMorelinesSymbol().toString())) {
			e.write(e.getMultilineSymbol().toString() + " ");
			build.append(e.readLine());
		}
		return commandName
				+ build.substring(commandName.length()).toString()
						.replace(e.getMorelinesSymbol(), ' ').trim();
	}

	/**
	 * Initializes the ten predefined commands and stores them in a map for
	 * quick retrieval.
	 */
	public static void fillCommands() {
		commands.put("exit", new ExitShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("help", new HelpShellCommand());
	}

}
