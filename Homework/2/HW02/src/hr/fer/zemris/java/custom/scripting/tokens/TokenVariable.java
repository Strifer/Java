package hr.fer.zemris.java.custom.scripting.tokens;
/** 
 * This class represents a variable token inside our document represented as a <code>String</code>
 * 
 * @author Filip Džidić
 *
 */
public class TokenVariable extends Token {
	/** holds the variable's name */
	private String name;
	
	
	/** initialize and stores the user provided name of the variable */
	public TokenVariable (String name) {
		this.name=name;
	}
	/** default constructor initializes an unnamed variable */
	public TokenVariable () {
		this("");
	}
	
	/** returns the variable's name */
	public String getName() {
		return name;
	}
	
	/** Returns the variable name as text. Used for document reconstruction */
	@Override
	public String asText() {
		return name;
	}
}
