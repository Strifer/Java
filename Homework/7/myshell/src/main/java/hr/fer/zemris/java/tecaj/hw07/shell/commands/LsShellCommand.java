package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a <code>"ls"</code> command inside our shell.
 * <p>
 * This command writes all the files and directories contained inside a given
 * directory as well as printing out some basic characteristics of them. <br>
 * This command expects a single argument, a directory that exists the user's
 * file system.
 * 
 * @author Filip Džidić
 *
 */
public class LsShellCommand implements ShellCommand {
	/** This static variable holds the name of the command. */
	private static final String NAME = "ls";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			if (arguments == null) {
				env.writeln("Please provide a valid argument");
				return ShellStatus.CONTINUE;
			}
			List<String> list = PathUtility.splitPaths(arguments);
			if (list.size() != 1) {
				env.writeln("Invalid number of arguments");
				return ShellStatus.CONTINUE;
			}
			String file = list.get(0);
			file = PathUtility.trimQuotes(file);
			File directory = Paths.get(file).toFile();
			// System.out.println(list.get(0));
			if (!directory.exists()) {
				env.writeln(directory.getName() + " does not exist on disk");
				return ShellStatus.CONTINUE;
			}
			if (directory.isFile()) {
				env.writeln("Argument is file, argument must be a directory");
				return ShellStatus.CONTINUE;
			}
			env.write(outputList(directory));
			return ShellStatus.CONTINUE;

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
		ArrayList<String> list = new ArrayList<>();
		list.add("Lists all of the contents of a directory");
		list.add("Displays four main characteristics of every file inside the folder, permissions, date of last modification, size and name");
		return Collections.unmodifiableList(list);
	}

	/**
	 * This method creates forms the characteristics of all the files inside the
	 * provided directory as a formatted <code>String</code>.
	 * 
	 * @param directory
	 *            the directory whose contents we're displaying
	 * @return a <code>String</code> representation of the characteristics of
	 *         all the files found in our directory
	 * @throws IOException
	 *             if an IO error occur
	 */
	private String outputList(File directory) throws IOException {
		StringBuilder sb = new StringBuilder();
		File[] directories = directory.listFiles();
		for (File f : directories) {
			sb.append(getCharacteristics(f));
		}
		return sb.toString();
	}

	/**
	 * This method forms all of the main characteristics of a given file as a
	 * formatted <code>String</code>
	 * 
	 * @param f
	 *            the file whose characteristics we're reading
	 * @return the <code>String</code> representation of all of the file's main
	 *         characteristics
	 * @throws IOException
	 *             if an IO error occurs
	 */
	private String getCharacteristics(File f) throws IOException {
		char isDirectory = f.isDirectory() ? 'd' : '-';
		char isReadable = f.canRead() ? 'r' : '-';
		char isWriteable = f.canWrite() ? 'w' : '-';
		char isExecutable = f.canWrite() ? 'x' : '-';
		Long size = f.length();
		String time = formattedDate(f.toPath());
		String name = f.getName();
		return String.format("%c%c%c%c%10d %s %s%n", isDirectory, isReadable,
				isWriteable, isExecutable, size, time, name);
	}

	/**
	 * This method makes a formatted date representing the last time the file
	 * was modified.
	 * 
	 * @param p
	 *            the path to the file whose date we're creating
	 * @return a <code>String</code> representation of the file's time
	 *         characteristics
	 * @throws IOException
	 *             if an IO error occurs
	 */
	private String formattedDate(Path p) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(p,
				BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		return formattedDateTime;
	}

}
