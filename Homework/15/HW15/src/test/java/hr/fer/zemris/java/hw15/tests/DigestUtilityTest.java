package hr.fer.zemris.java.hw15.tests;

import static org.junit.Assert.*;
import hr.fer.zemris.java.hw15.utility.DigestUtility;

import java.security.NoSuchAlgorithmException;
import org.junit.Test;

public class DigestUtilityTest {

	@Test
	public void SHA_1ValidityTest() {
		String password = "123";
		String slightlyDifferentPassword = "123 ";
		try {
			assertTrue(DigestUtility.generateDigest(password).equals(
					DigestUtility.generateDigest("123")));
			assertTrue(!DigestUtility.generateDigest(password).equals(
					DigestUtility.generateDigest(slightlyDifferentPassword)));
		} catch (NoSuchAlgorithmException ignorable) {
		}
	}

	@Test
	public void HexToByteTest() {
		String hex = "abcdef";
		byte[] data = DigestUtility.hextobyte(hex);
		assertTrue(DigestUtility.bytetohex(data).equals(hex));
	}

}
