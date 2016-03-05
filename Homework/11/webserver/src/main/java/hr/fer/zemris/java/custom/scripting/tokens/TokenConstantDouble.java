package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * This class represents a literal decimal inside a document.
 * 
 * @author Filip Džidić
 *
 */
public class TokenConstantDouble extends Token {
	/** holds the exact double value */
	private double value;

	/**
	 * Initialize value with user provided value
	 * 
	 * @param value
	 *            user provided value
	 */
	public TokenConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * Used for retrieving the value
	 * 
	 * @return the exact double value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Returns a <code>String</code> representation of this class.
	 */
	@Override
	public String asText() {
		String s = "";
		return s + value;
	}
}
