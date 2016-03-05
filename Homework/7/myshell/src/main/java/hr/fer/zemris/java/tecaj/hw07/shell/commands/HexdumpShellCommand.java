package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a <code>"hexdump"</code> command inside our shell.
 * <p>
 * This command writes out the content of a file byte by byte in hexadecimal
 * format. A single argument must be provided, the file whose content will be
 * displayed.
 * 
 * @author Filip Džidić
 *
 */
public class HexdumpShellCommand implements ShellCommand {
	/** This static variable holds the name of the command. */
	private final static String NAME = "hexdump";

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
			File file = Paths.get(PathUtility.trimQuotes(list.get(0))).toFile();
			if (!file.exists()) {
				env.writeln("File " + file.getName()
						+ " does not exist on disk");
				return ShellStatus.CONTINUE;
			} else if (!file.isFile()) {
				env.writeln("File must be a file");
				return ShellStatus.CONTINUE;
			} else if (!file.canRead()) {
				env.writeln("Cannot read from file " + file.getName());
				return ShellStatus.CONTINUE;
			}
			env.write(output(file));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method reads the contents of a file and builds a <code>String</code>
	 * representation of our output.
	 * 
	 * @param file
	 *            the file whose contents we're reading
	 * @return a <code>String</code> representation of the file's contents in
	 *         hex format
	 * @throws IOException
	 *             if an IO error occurs
	 */
	private String output(File file) throws IOException {
		StringBuilder build = new StringBuilder();
		try (InputStream is = Files.newInputStream(file.toPath(),
				StandardOpenOption.READ)) {
			byte[] buff = new byte[16];
			int counter = 0;
			while (true) {
				int r = is.read(buff);
				if (r == -1)
					break;
				build.append(String.format("%08X:", counter));
				build.append(bytesFormatter(buff, r) + "\n");
				counter += 16;
			}
		}
		return build.toString();
	}

	/**
	 * This method forms the format of a single line in the hexdump output. It
	 * contains thre main columns.<br>
	 * The first column counts the number of bytes outputted so far.<br>
	 * The second column is the hexadecimal representation of the file's
	 * contents.<br>
	 * The third column is the text representation of the file's contents when
	 * interpreted as characters.
	 * 
	 * @param buff
	 *            a buffer containing the at most 16 bytes whose contents we're
	 *            displaying
	 * @param length
	 *            the actual number of bytes being sent
	 * @return a <code>String</code> representation of a single line in our
	 *         output
	 */
	private String bytesFormatter(byte[] buff, int length) {
		StringBuilder build = new StringBuilder();
		int i;
		for (i = 0; i < length; i++) {
			if (i < 8) {
				build.append(String.format(" %02X", buff[i]));
			} else if (i == 8) {
				build.append("|" + String.format("%02X ", buff[i]));
			} else {
				build.append(String.format("%02X ", buff[i]));
			}
		}
		for (; i < buff.length; i++) {
			if (i == 8) {
				build.append("|   ");
			} else {
				build.append("   ");
			}
		}
		build.append("| ");
		for (int j = 0; j < length; j++) {
			if (buff[j] < 32 || buff[j] > 127) {
				build.append('.');
			} else {
				build.append((char) buff[j]);
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
		list.add("Displays the contents of a single file in hexadecimal format");
		list.add("Accepts a single argument, the argument must be a readable file");
		return Collections.unmodifiableList(list);
	}

}
