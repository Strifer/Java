package hr.fer.zemris.java.tecaj.hw5.db.filters;

import hr.fer.zemris.java.tecaj.hw5.db.comparators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.fields.IFieldValueGetter;

/**
 * This class represents a single expression in a query command. Every valid
 * <code>ConditionalExpression</code> follows the following format
 * <p>
 * <code>[field][operator][string literal]</code> <br>
 * Example:
 * <p>
 * <code>firstName&#62;="A*"</code>
 * <p>
 * Instances of this class hold all three parts of a valid expression and
 * provide getter methods for reading them.
 * 
 * @author Filip Džidić
 *
 */
public class ConditionalExpression {
	/** holds the field part of the expression */
	private IFieldValueGetter field;
	/** holds the literal string part of the expression */
	private String value;
	/** holds the operator part of the expression */
	private IComparisonOperator operator;

	/**
	 * Constructs a <code>ConditionalExpression</code> with its main three parameters.
	 * @param field the field part of the expression
	 * @param value the <code>String</code> literal part of the expression
	 * @param operator the operator part of the expression
	 */
	public ConditionalExpression(IFieldValueGetter field, String value,
			IComparisonOperator operator) {
		this.field = field;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * Getter method for field
	 * 
	 * @return the field
	 */
	protected IFieldValueGetter getField() {
		return field;
	}

	/**
	 * Getter method for String literal
	 * 
	 * @return the value
	 */
	protected String getValue() {
		return value;
	}

	/**
	 * Getter method for operator
	 * 
	 * @return the operator
	 */
	protected IComparisonOperator getOperator() {
		return operator;
	}

}
