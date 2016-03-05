package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * This class represents a single for-loop construct. It inherits from Node. It
 * contains methods for retrieval of attributes and a simple string conversion
 * method used for simple output.
 * <p>
 * A valid for-loop contains a single variable and two or three expressions
 * which follow.
 * 
 * @author Filip Džidić
 *
 */
public class ForLoopNode extends Node {
	/** represents a user defined variable */
	private TokenVariable variable;
	/** represents the for loop's start expression */
	private Token startExpression;
	/** represents the for loop's end expression */
	private Token endExpression;
	/** represents the for loop's step expression, can be null */
	private Token stepExpression;

	/**
	 * Constructor which receives references to user defined for-loop
	 * parameters. StepExpression can be null.
	 * 
	 * @param variable
	 *            the main variable in the for loop
	 * @param startExpression
	 *            the initial value
	 * @param endExpression
	 *            the ending value
	 * @param stepExpression
	 *            the increment
	 */
	public ForLoopNode(TokenVariable variable, Token startExpression,
			Token endExpression, Token stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Method for variable retrieval.
	 * 
	 * @return the for loop's variable
	 */
	public TokenVariable getVariable() {
		return variable;
	}

	/**
	 * Method for startExpression retrieval
	 * 
	 * @return the for loop's start expression
	 */
	public Token getStartExpression() {
		return startExpression;
	}

	/**
	 * Method for endExpression retrieval
	 * 
	 * @return the for loop's end expression
	 */
	public Token getEndExpression() {
		return endExpression;
	}

	/**
	 * Method for stepExpression retrieval
	 * 
	 * @return the for loop's step expression, can be null
	 */
	public Token getStepExpression() {
		return stepExpression;
	}

	/**
	 * This method returns a <code>String</code> representation of the user
	 * defined for-loop.
	 */
	public String toString() {
		String s;
		if (getStepExpression() == null) {
			s = ("{$FOR " + getVariable().asText() + " "
					+ getStartExpression().asText() + " "
					+ getEndExpression().asText() + "$}");
		} else {
			s = ("{$FOR " + getVariable().asText() + " "
					+ getStartExpression().asText() + " "
					+ getEndExpression().asText() + " "
					+ getStepExpression().asText() + "$}");
		}
		s.concat("\n\r");
		for (int i = 0; i < this.numberOfChildren(); i++) {
			s += this.getChild(i).toString();
		}
		s += ("{$END$}");
		return s;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);

	}

}
