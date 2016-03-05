package hr.fer.zemris.java.hw15.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This utility class offers method for encrypting passwords with the SHA-1
 * algorithm.
 * 
 * @author Filip Džidić
 *
 */
public class DigestUtility {

	/**
	 * This method generates a SHA-1 message digest from a provided
	 * <code>String</code>
	 * 
	 * @param word
	 *            String being converted into SHA-1 digest.
	 * @return SHA-1 encrypted string
	 * @throws NoSuchAlgorithmException
	 *             if a bad algorithm for digest generation is entered
	 */
	public static String generateDigest(String word)
			throws NoSuchAlgorithmException {

		MessageDigest dig = MessageDigest.getInstance("SHA-1");
		dig.update(word.getBytes());
		byte[] updatedData = dig.digest();
		return bytetohex(updatedData);

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
}
