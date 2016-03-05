package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * This class represents an integer literal inside our document. It comes with
 * methods used for initializing said value, retrieving it and turning into
 * text.
 * 
 * @author Filip Džidić
 *
 */
public class TokenConstantInteger extends Token {
	/** storage for the literal integer */
	private int value;

	/**
	 * Initializes the integer.
	 * 
	 * @param value
	 *            the initial integer
	 */
	public TokenConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Used for retrieving the stored integer.
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns a <code>String</code> representation of the stored integer.
	 */
	@Override
	public String asText() {
		String s = "";
		return s + value;
	}
}
