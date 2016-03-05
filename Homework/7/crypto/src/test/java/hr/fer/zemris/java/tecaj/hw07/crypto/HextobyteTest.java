package hr.fer.zemris.java.tecaj.hw07.crypto;

import org.junit.Assert;
import org.junit.Test;

public class HextobyteTest {

	@Test
	public void HexToByteOutputTest() {
		String hextext = "0a00120102030405060b"; // 10 0 18 1 2 3 4 5 6 11
		byte[] output = Crypto.hextobyte(hextext);
		byte[] expectedResult = new byte[] { 10, 0, 18, 1, 2, 3, 4, 5, 6, 11 };
		Assert.assertArrayEquals(expectedResult, output);
	}

	@Test
	public void ByteToHexOutputTest() {
		byte[] input = new byte[] { 10, 0, 18, 1, 2, 3, 4, 5, 6, 11 };
		String output = Crypto.bytetohex(input);
		String expectedResult = "0a00120102030405060b";
		Assert.assertTrue(output.equals(expectedResult));
	}

}
