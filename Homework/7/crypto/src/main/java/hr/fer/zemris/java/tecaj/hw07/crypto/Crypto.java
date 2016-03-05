package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class represents a CLI program capable of encrypting and decrypting a
 * file using the AES algorithm. It also provides a method for checking a file's
 * digest.
 * 
 * @author Filip Džidić
 *
 */
public class Crypto {
	/**
	 * Main method which executes the the encryption, decryption or digest
	 * checking depending on the provided arguments. <br>
	 * The program has three main modes of work depending on which arguments are
	 * received.
	 * <p>
	 * Here's the format<br>
	 * <b>1)Digest mode:</b><br>
	 * <code>checksha [file pathname]</code>
	 * <p>
	 * <b>2)Encryption mode:</b><br>
	 * <code>encrypt [original src file pathname] [encrypted dest file pathname]</code>
	 * <p>
	 * <b>3)Decryption mode:</b><br>
	 * <code>decrypt [encrypted src file pathname] [decrypted dest file pathname]</code>
	 * 
	 * @param args
	 *            the arguments being sent through the commandline
	 * @throws NoSuchAlgorithmException
	 *             if you enter a non existing crypto algorithm
	 * @throws IOException
	 *             if IO error occurs
	 * @throws InvalidKeyException
	 *             if you invalid key is entered
	 * @throws NoSuchPaddingException
	 *             if there is no padding
	 * @throws InvalidAlgorithmParameterException
	 *             if an invalid algorithm parameter is sent
	 * @throws IllegalBlockSizeException
	 *             if you enter an illegal block size
	 * @throws BadPaddingException
	 *             if padding is bad
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException,
			IOException, InvalidKeyException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		if (args[0].equals("checksha")) {
			if (args.length != 2) {
				System.err.println("Invalid number of arguments");
				System.exit(1);
			}

			generateDigest(Paths.get(args[1]), reader);
		} else if (args[0].equals("encrypt")) {
			if (args.length != 3) {
				System.err.println("Invalid number of arguments");
				System.exit(1);
			}

			encryptOrDecrypt(Paths.get(args[1]), Paths.get(args[2]), reader,
					true);
		} else if (args[0].equals("decrypt")) {
			if (args.length != 3) {
				System.err.println("Invalid number of arguments");
				System.exit(1);
			}

			encryptOrDecrypt(Paths.get(args[1]), Paths.get(args[2]), reader,
					false);
		} else {
			System.err.println("Invalid command");
			System.exit(1);
		}
	}

	/**
	 * This is a general method used for handling the user input when sending
	 * parameters.
	 * 
	 * @param reader
	 *            <code>BufferedReader</code> who handles user input
	 * @param parameter
	 *            the parameter which the method is reading
	 * @param fileName
	 *            the fileName whose digest we want to calculate
	 * @return the read line
	 * @throws IOException
	 *             if IO error occurs
	 */
	private static String getInput(BufferedReader reader, String parameter,
			String fileName) throws IOException {
		String greetMessage = "";
		switch (parameter) {
			case "password":
				greetMessage = "Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):";
				break;
			case "vector":
				greetMessage = "Please provide initialization vector as hex-encoded text (32 hex-digits):";
				break;
			case "digest":
				greetMessage = "Please provide expected sha-256 digest for "
						+ fileName + ":";
				break;
			default:
				System.err.println("Invalid parameter");
				System.exit(1);
		}

		String line = null;
		while (true) {
			System.out.println(greetMessage);
			System.out.print("> ");
			line = reader.readLine().trim().toLowerCase();
			if (!line.matches("[0-9a-f]+")) {
				System.out.println("Invalid hex format");
				continue;
			}
			if (parameter.equals("digest")) {
				if (line.length() % 2 == 1) {
					System.out.println("Uneven number of digits");
					continue;
				}
				break;
			}

			if (line.length() != 32) {
				System.out.println("Number of hex digits must be 32");
				continue;
			}
			break;
		}

		return line;
	}

	/**
	 * This general method is used for starting the encryption or decryption
	 * process using the provided arguments.
	 * 
	 * @param path
	 *            src file pathname
	 * @param filename
	 *            dest file pathname
	 * @param reader
	 *            the reader which handles user input
	 * @param crypting
	 *            signifies if we're in encryption or decryption mode
	 * @throws NoSuchAlgorithmException
	 *             if you enter a non existing crypto algorithm
	 * @throws IOException
	 *             if IO error occurs
	 * @throws InvalidKeyException
	 *             if you invalid key is entered
	 * @throws NoSuchPaddingException
	 *             if there is no padding
	 * @throws InvalidAlgorithmParameterException
	 *             if an invalid algorithm parameter is sent
	 * @throws IllegalBlockSizeException
	 *             if you enter an illegal block size
	 * @throws BadPaddingException
	 *             if padding is bad
	 */
	private static void encryptOrDecrypt(Path path, Path filename,
			BufferedReader reader, boolean crypting) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {

		String password = getInput(reader, "password", null);
		String vector = getInput(reader, "vector", null);
		Cipher cipher = getCipher(password, vector, crypting);
		FileInputStream fread = null;
		FileOutputStream fwrite = null;
		try {
			fread = new FileInputStream(path.toFile());
		} catch (IOException e) {
			System.out
					.println("Cannot find file on disk " + path.getFileName());
			System.exit(1);
		}

		try {
			fwrite = new FileOutputStream(filename.toFile());
		} catch (IOException e) {
			System.out.println("Unable to write to file");
			System.exit(1);
		}

		try {
			byte[] buff = new byte[4096];
			while (true) {
				int r = fread.read(buff);
				if (r < 1)
					break;
				byte[] clearText = cipher.update(buff, 0, r);
				fwrite.write(clearText);
			}
			byte[] clearText = cipher.doFinal();
			fwrite.write(clearText);

		} catch (IOException e) {
			System.out.println("Unable to transfer data");
			System.exit(1);
		}
		fread.close();
		fwrite.close();
		System.out.println("Decryption completed. Generated file "
				+ filename.getFileName() + " based on file "
				+ path.getFileName() + ".");

	}

	/**
	 * This static method creates a new Cypher used for encryption/decryption
	 * based on user provided parameters.
	 * 
	 * @param password
	 *            hex encoded password
	 * @param vector
	 *            hex encoded initialization vector
	 * @param encrypting
	 *            signifies if we're in encryption or decryption mode
	 * @return the created Cypher
	 * @throws NoSuchAlgorithmException
	 *             This exception is thrown when a particular cryptographic
	 *             algorithm is requested but is not available in the
	 *             environment.
	 * @throws NoSuchPaddingException
	 *             This exception is thrown when a particular padding mechanism
	 *             is requested but is not available in the environment.
	 * 
	 * @throws InvalidKeyException
	 *             This is the exception for invalid Keys (invalid encoding,
	 *             wrong length, uninitialized, etc).
	 * @throws InvalidAlgorithmParameterException
	 *             This is the exception for invalid or inappropriate algorithm
	 *             parameters.
	 */
	private static Cipher getCipher(String password, String vector,
			boolean encrypting) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {
		SecretKeySpec keySpec = new SecretKeySpec(hextobyte(password), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(
				hextobyte(vector));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(encrypting ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
				keySpec, paramSpec);
		return cipher;
	}

	/**
	 * This method generates a SHA-256 message digest from a provided file and
	 * checks if the user provided one matches.
	 * 
	 * @param path
	 *            src file pathname
	 * @param reader
	 *            reader which handles user input
	 * @throws NoSuchAlgorithmException
	 *             if a bad algorithm for digest generation is entered
	 * @throws IOException
	 *             if an IO error occurs
	 */
	private static void generateDigest(Path path, BufferedReader reader)
			throws NoSuchAlgorithmException, IOException {
		MessageDigest dig = MessageDigest.getInstance("SHA-256");
		String userInput = getInput(reader, "digest", path.getFileName()
				.toString());
		byte[] userResult = hextobyte(userInput);
		try (FileInputStream freader = new FileInputStream(path.toFile())) {
			byte[] buff = new byte[4096];
			while (true) {
				int r = freader.read(buff);
				if (r < 1)
					break;
				dig.update(buff, 0, r);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		byte[] actualResult = dig.digest();
		if (Arrays.equals(actualResult, userResult)) {
			System.out.println("Digesting completed. Digest of "
					+ path.getFileName() + " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of "
					+ path.getFileName()
					+ " does not match the expected digest. Digest was "
					+ bytetohex(actualResult));
		}
	}

	/**
	 * This method converts a <code>String</code> in hex format to an array of
	 * bytes.
	 * 
	 * @param hexString
	 *            <code>String</code> representation of an array of bytes in hex
	 *            format
	 * @return the actual byte array represented by the <code>String</code>
	 */
	public static byte[] hextobyte(String hexString) {
		int len = hexString.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
					.digit(hexString.charAt(i + 1), 16));
		}
		return data;
	}

	/**
	 * This method converts bytes to a <code>String</code> representation in hex
	 * format.
	 * 
	 * @param data
	 *            the bytes being converted
	 * @return bytes in hex format
	 */
	public static String bytetohex(byte[] data) {
		StringBuilder build = new StringBuilder(data.length * 2);
		for (int i = 0; i < data.length; i++) {
			build.append(Integer.toHexString(0x0100 + (data[i] & 0x00FF))
					.substring(1));
		}
		return build.toString();
	}

}
