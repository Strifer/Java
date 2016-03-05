package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * This class represents a defined operator token inside our document, it comes
 * equipped with methods for storing and retrieving the operator.
 * 
 * @author Filip Džidić
 *
 */
public class TokenOperator extends Token {
	/** used for storing the operator */
	private String symbol;

	/**
	 * Constructor takes user defined symbol and stores it.
	 * 
	 * @param symbol
	 *            user defined symbol
	 */
	public TokenOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Returns the symbol representing the operator.
	 * 
	 * @return the symbol representing the operator
	 */
	public String getName() {
		return symbol;
	}

	/**
	 * Returns the symbol representing the operator, is used in text
	 * reconstruction.
	 * 
	 * @return the symbol representing the operator
	 */
	@Override
	public String asText() {
		return symbol;
	}
}
