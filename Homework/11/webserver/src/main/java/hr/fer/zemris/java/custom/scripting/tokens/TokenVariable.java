package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * This class represents a variable token inside our document represented as a
 * <code>String</code>
 * 
 * @author Filip Džidić
 *
 */
public class TokenVariable extends Token {
	/** holds the variable's name */
	private String name;

	/**
	 * Initializes and stores the user provided name of the variable.
	 * 
	 * @param name
	 *            name of the variable
	 */
	public TokenVariable(String name) {
		this.name = name;
	}

	/**
	 * Default constructor initializes an unnamed variable.
	 */
	public TokenVariable() {
		this("");
	}

	/**
	 * Returns the variable's name.
	 * 
	 * @return the variable's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the variable name as text. Used for document reconstruction.
	 * 
	 * @return the variable's name
	 */
	@Override
	public String asText() {
		return name;
	}
}
