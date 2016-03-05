package hr.fer.zemris.java.custom.scripting.tokens;
/** 
 * This class represents a <code>String</code> token inside our document
 * It comes equipped with methods for storing and retrieving the text.
 * @author Filip Džidić
 *
 */
public class TokenString extends Token {
	/** the token's text is stored here */
	private String value;
	
	/** Initializes the value with user defined <code>String</code> */
	public TokenString(String value) {
		this.value=value;
	}
	
	/** Simply returns the text as a <code>String</code>. Used for text reconstruction. */
	@Override
	public String asText() {
		return value;
	}
}
