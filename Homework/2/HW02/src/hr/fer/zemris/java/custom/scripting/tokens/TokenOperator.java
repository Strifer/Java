package hr.fer.zemris.java.custom.scripting.tokens;
/** 
 * This class represents a defined operator token inside our document, it comes equipped with methods for storing and retrieving the operator.
 * @author Filip Džidić
 *
 */
public class TokenOperator extends Token {
	/** used for storing the operator */
	private String symbol;
	/** constructor takes user defined symbol and stores it */
	public TokenOperator (String symbol) {
		this.symbol=symbol;
	}
	/** returns the symbol representing the operator */
	public String getName() {
		return symbol;
	}
	/** returns the symbol representing the operator, is used in text reconstruction */
	@Override
	public String asText() {
		return symbol;
	}
}
