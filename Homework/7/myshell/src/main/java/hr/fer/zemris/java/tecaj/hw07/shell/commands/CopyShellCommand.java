package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a <code>copy</code> command inside our shell. This
 * command's basic job is to copy and store a source file to the provided
 * destination.
 * <p>
 * This command takes 2 arguments. The first argument must be a file that exists
 * on the disk and is readable.<br>
 * The second argument must either be a directory that exists on the filesystem
 * or a file. If the second argument is a directory the source file will be
 * copied into that directory with the same filename. If the second argument is
 * a file it's pathname must be an existing directory. The source file will be
 * copied into that directory and it will have the name defined in the second
 * argument.
 * 
 * @author Filip Džidić
 *
 */
public class CopyShellCommand implements ShellCommand {
	/** This static variable holds the name of the command. */
	private static final String NAME = "copy";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			if (arguments == null) {
				env.writeln("Two arguments needed");
				return ShellStatus.CONTINUE;
			}

			List<String> list = PathUtility.splitPaths(arguments);
			if (list.size() != 2) {
				env.writeln("Invalid number of arguments");
			}

			File src = Paths.get(PathUtility.trimQuotes(list.get(0))).toFile();
			File dest = Paths.get(PathUtility.trimQuotes(list.get(1))).toFile();

			if (src.equals(dest)) {
				env.writeln("Src and dest cannot have the same name and path");
				return ShellStatus.CONTINUE;
			}

			if (!src.exists()) {
				env.writeln("File " + src.getName() + " does not exist on disk");
				return ShellStatus.CONTINUE;
			}
			if (!src.isFile()) {
				env.writeln("File " + src.getName() + " is not file");
				return ShellStatus.CONTINUE;
			}
			if (!src.canRead()) {
				env.writeln("File " + src.getName() + " is not readable");
				return ShellStatus.CONTINUE;
			}

			if (dest.isDirectory()) {
				dest = Paths.get(dest.getAbsolutePath(), src.getName())
						.toFile();
			}
			if (dest.isFile()) {
				env.writeln("File " + dest.getName()
						+ " already exists. Do you want to overwrite it? (Y/N)");
				env.write(env.getPromptSymbol() + " ");
				String ans = env.readLine().trim().toLowerCase();
				switch (ans) {
					case "n":
						env.writeln("Will not overwrite " + dest.getName());
						return ShellStatus.CONTINUE;
					case "y":
						env.writeln("Overwriting...");
						break;
					default:
						env.writeln("Invalid input. Exiting command");
						return ShellStatus.CONTINUE;
				}
			}
			copy(src, dest);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * This method reads from src and writes the content it has read to dest.
	 * 
	 * @param src
	 *            the file being copied
	 * @param dest
	 *            the copy being created
	 * @throws IOException
	 *             if an IO error occurs
	 */
	private void copy(File src, File dest) throws IOException {
		try (InputStream is = Files.newInputStream(src.toPath(),
				StandardOpenOption.READ);
				FileOutputStream os = new FileOutputStream(dest)) {
			byte[] buff = new byte[4096];
			while (true) {
				int r = is.read(buff);
				if (r == -1)
					break;
				os.write(buff, 0, r);
			}
		}

	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Makes a copy of the first argument and saves it in the location specified by the second argument");
		list.add("Command accepts two arguments.");
		list.add("The first argument must be a readable file");
		list.add("The second argument can be a directory or a path to the location of saving");
		list.add("If the second argument is a directory the file will be copied into that directory with the same name being kept");
		return Collections.unmodifiableList(list);
	}

}
