package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a <code>cat</code> command inside our shell. This
 * command's basic job is to write the contents of a file on the defined output.
 * <p>
 * This command can take either 1 or 2 arguments. The first argument must be a
 * file that exists on the disk and is readable.<br>
 * The second argument must be a Charset that exists on the user's computer. If
 * it is not provided the system's default charset will be used
 * 
 * @author Filip Džidić
 *
 */
public class CatShellCommand implements ShellCommand {
	/** This static variable holds the name of the command. */
	private final static String NAME = "cat";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			if (arguments == null) {
				env.writeln("Please provide a valid argument");
				return ShellStatus.CONTINUE;
			}
			Charset cs = null;
			List<String> list = PathUtility.splitPaths(arguments);

			if (list.size() > 2) {

				env.writeln("Invalid number of arguments");
				return ShellStatus.CONTINUE;

			} else if (list.size() == 2) {

				String charset = list.get(1);
				if (!Charset.isSupported(charset)) {
					env.writeln("Unsupported charset" + charset);
					return ShellStatus.CONTINUE;
				}

				cs = Charset.forName(charset);
			} else {
				cs = Charset.defaultCharset();
			}
			File file = Paths.get(PathUtility.trimQuotes(list.get(0))).toFile();
			if (file.isDirectory()) {
				env.writeln("Command supports files only");
				return ShellStatus.CONTINUE;
			}

			if (file.canRead()) {
				env.writeln(output(file, cs));
			} else {
				env.writeln("No permission to read");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return ShellStatus.CONTINUE;

	}

	/**
	 * Reads bytes from a file and converts the file's contents to a
	 * <code>String</code>
	 * 
	 * @param file
	 *            the file we're reading
	 * @param cs
	 *            the Charset we're using for interpreting the file's contents
	 * @return the content of the file as a <code>String</code> representation
	 * @throws IOException
	 *             if an IO error occurs while reading
	 */
	private String output(File file, Charset cs) throws IOException {
		StringBuilder build = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(Files.newInputStream(file.toPath(),
						StandardOpenOption.READ)), cs))) {
			char[] buff = new char[4096];
			while (true) {
				int r = reader.read(buff);
				if (r == -1)
					break;
				build.append(buff, 0, r);
			}
		}
		return build.toString();

	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Concatenates file and prints on standard output");
		list.add("Accepts two or one arguments.");
		list.add("The first argument must be a readable file");
		list.add("The second argument is the specified Charset of the file");
		list.add("If no second argument is used the system's default Charset is used for decoding");
		return Collections.unmodifiableList(list);
	}

}
