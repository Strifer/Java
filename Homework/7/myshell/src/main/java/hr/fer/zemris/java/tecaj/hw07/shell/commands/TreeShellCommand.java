package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a <code>"tree"</code> command inside our shell.
 * <p>
 * This command recursively prints out all of the files and subdirectory found
 * in a given directory in a treelike indented format. <br>
 * This command expects a single argument, a pathname to the directory whose
 * contents we wish to print.
 * 
 * @author Filip Džidić
 *
 */
public class TreeShellCommand implements ShellCommand {
	/** This static variable holds the name of the command. */
	private static final String NAME = "tree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null) {
			try {
				env.writeln("Please provide a valid argument");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ShellStatus.CONTINUE;
		}
		List<String> list = PathUtility.splitPaths(arguments);
		if (list.size() != 1) {
			try {
				env.writeln("Invalid number of arguments");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Path root = Paths.get(PathUtility.trimQuotes(list.get(0)));
		try {
			if (!Files.exists(root)) {
				env.writeln("Directory does not exist on the filesystem");
			}
			if (!Files.isDirectory(root)) {
				env.writeln(root + " Not a directory");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		@SuppressWarnings("unused")
		DirectoryVisitor ispis = new DirectoryVisitor(env);
		try {
			Files.walkFileTree(root, new DirectoryVisitor(env));
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
		list.add("Recursively lists all of the directories children inside the filesystem");
		list.add("Command accepts a single argument, a path to an existing directory whose content will be displayed");
		return Collections.unmodifiableList(list);
	}

	/**
	 * This helper class represent a file visitor which is used to recursively
	 * visit every file found inside the user provided directory.
	 * 
	 * @author Filip Džidić
	 *
	 */
	private static class DirectoryVisitor implements FileVisitor<Path> {
		/**
		 * This integer marks the current level of recursion we're in for
		 * formatted output.
		 */
		private int indentLevel;
		/** A reference to the environment we're operating in */
		private Environment env;

		/**
		 * Default constructor initializes our directory visitor
		 * 
		 * @param env
		 *            A reference to the environment we're operating in
		 */
		public DirectoryVisitor(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			if (indentLevel == 0) {
				env.writeln(dir.toString());
			} else {
				env.write(String.format("%" + indentLevel + "s%s%n", "",
						dir.getFileName()));
			}
			indentLevel += 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			env.write(String.format("%" + indentLevel + "s%s%n", "",
					file.getFileName()));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			indentLevel -= 2;
			return FileVisitResult.CONTINUE;
		}

	}

}
