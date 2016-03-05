package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * This class represents a function inside our document. It provides methods for storing the function in the form of its name. Which can be later retrieved.
 * @author Filip Džidić
 *
 */
public class TokenFunction extends Token {
	/** holds the function's name */
	private String name;
	
	/** initializes the name to hold the user given function name */
	public TokenFunction (String name) {
		this.name=name;
	}
	/** default constructor will create an unnamed function */
	public TokenFunction () {
		this("");
	}
	/** returns the function's name */
	public String getName() {
		return name;
	}
	
	
	/** returns the function's name as text */
	@Override
	public String asText() {
		return name;
	}
}
