package hr.fer.zemris.java.tecaj.hw5.db.filters;

import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonEqual;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonEqualWithWildCard;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonGreater;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonGreaterEqual;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonLesser;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonLesserEqual;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonNotEqual;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.data.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.fields.FirstName;
import hr.fer.zemris.java.tecaj.hw5.db.fields.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fields.Jmbag;
import hr.fer.zemris.java.tecaj.hw5.db.fields.LastName;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a filter for the query command in our database. A query
 * command is a list of <code>ConditionalExpression</code> chained together by
 * the logical and operator. This class provides methods for breaking up a query
 * command into its base components and provides a way of testing if a certain
 * <code>StudentRecord</code> satisfies all its defined conditions.
 * <p>
 * Queries which contain a <code>ConditionalExpression</code> defined as:
 * <p>
 * <code>jmbag=[string literal with no wild cards]</code>
 * <p>
 * are considered a special case where retrieval is expected to be performed
 * using indexing.
 * 
 * @author Filip Džidić
 *
 */
public class QueryFilter implements IFilter {
	/**
	 * this collections holds all of the conditionals found in our query command
	 */
	private ArrayList<ConditionalExpression> expressions;
	/**
	 * we record the special case index here if such a conditional expression is
	 * present
	 */
	private String jmbag;

	/**
	 * Constructs a filter for our database based on a user provided query
	 * command.
	 * <p>
	 * General format of <b>query</b> is ...<br>
	 * <p>
	 * <code>query [field][operator][string literal] and ...</code><br>
	 * <p>
	 * [field] - <code>lastName firstName jmbag</code><br>
	 * [operator] - <code>&#60; &#60;= = != &#62;= &#62; </code><br>
	 * [string literal] - only the = operator has special restriction where * in
	 * string represents a meta character signifying a general substring of
	 * characters <br>
	 * <b>and</b> - links multiple expressions with a logical and operation <br>
	 * 
	 * @param query
	 *            user provided query command
	 */
	public QueryFilter(String query) {
		// String[] fields = query.split("and(?![\\s\\wčćžšđČĆŽŠĐ\\.-]*\")");
		// old regex, have it here for some testing purposes, be careful if
		// you're going to play around with it
		jmbag = null;
		int counter = 0;
		String[] fields = query.split("and(?![^=<>!=]*\")"); // matches all
																// "and" which
																// don't have
																// operators
																// after them
		expressions = new ArrayList<ConditionalExpression>(fields.length);
		for (String expression : fields) {
			ConditionalExpression exp = makeConditional(expression.trim());
			expressions.add(exp);
			if (counter == 0 && exp.getField() instanceof Jmbag
					&& exp.getOperator() instanceof ComparisonEqual) {
				jmbag = exp.getValue();
				counter++;
			}
		}
	}

	/**
	 * This method is used for parsing and creating
	 * <code>ConditionalExpression</code> based on <code>String</code> input.
	 * 
	 * @param expression
	 *            the <code>String</code> representation of a
	 *            <code>ConditionalExpression</code>
	 * @return newly created <code>ConditionalExpression</code>
	 * @throws IllegalArgumentException
	 *             if the expression has invalid format
	 * @see hr.fer.zemris.java.tecaj.hw5.db.filters.ConditionalExpression
	 */
	private ConditionalExpression makeConditional(String expression) {
		String source = expression;
		
		
		Matcher m = Pattern.compile("(<=?|=|>=?|!=)").matcher(source);
		//we split our expression around by matching its operator and storing the three main values
		//inside this collection
		ArrayList<String> elements = new ArrayList<>();
		
		int pos;
		for (pos = 0; m.find(); pos = m.end()) {
			elements.add(source.substring(pos, m.start()));
			elements.add(m.group());
		}
		elements.add(source.substring(pos, source.length()));
		//this loop stores field, operator, and everything following the operator (string literal)
		//inside the collection
		
		//if we have more than three elements that means we've had multiple operators, that's not allowed
		if (elements.size() != 3) {
			throw new IllegalArgumentException("Invalid expression " + elements);
		}
		
		//now we make our new expression with appropriate methods that check format and deal with specifics
		return new ConditionalExpression(makeField(elements.get(0).trim()),
				makeValue(elements.get(2).trim()), makeOperator(elements.get(1)
						.trim(), elements.get(2).trim()));

	}

	/**
	 * This method retrieves the recorded index if it was found
	 * 
	 * @return the recorded index
	 */
	public Optional<String> getJmbag() {
		return Optional.ofNullable(jmbag);
	}

	/**
	 * This method is used for checking if a provided field is valid.
	 * 
	 * @param field
	 *            <code>String</code> representation of a field
	 * @return newly created valid <code>IFieldValueGetter</code>
	 * @see hr.fer.zemris.java.tecaj.hw5.db.fields.IFieldValueGetter
	 * @throws IllegalArgumentException
	 *             if field is undefined
	 */
	private IFieldValueGetter makeField(String field) {
		switch (field) {
		case "firstName":
			return new FirstName();
		case "lastName":
			return new LastName();
		case "jmbag":
			return new Jmbag();
		default:
			throw new IllegalArgumentException("undefined field " + field);
		}
	}

	/**
	 * This method is used for checking if a provided operator is valid. Makes a
	 * new operator if it is valid.
	 * 
	 * @param operator
	 *            <code>String</code> representation of an operator
	 * @param <code>String</code> literal part of the expression, used for
	 *        finding wildcard operators
	 * @return newly constructed <code>IComparisonOperator</code>
	 * @throws IllegalArgumenteException
	 *             if undefined operator
	 * @see hr.fer.zemris.java.tecaj.hw5.db.comparators.IComparisonOperator
	 */
	private IComparisonOperator makeOperator(String operator, String literal) {
		switch (operator) {
		case "<":
			return new ComparisonLesser();
		case ">":
			return new ComparisonGreater();
		case "<=":
			return new ComparisonLesserEqual();
		case ">=":
			return new ComparisonGreaterEqual();
		case "=":
			int wildCardCount = wildCardCount(literal);
			if (wildCardCount == 0) {
				return new ComparisonEqual();
			} else if (wildCardCount != 1) {
				throw new IllegalArgumentException(
						"only one wildCard is allowed in string literal: "
								+ literal);
			}
			return new ComparisonEqualWithWildCard();

		case "!=":
			return new ComparisonNotEqual();
		default:
			throw new IllegalArgumentException("undefined operator");
		}
	}

	/**
	 * This method is used for checking if the <code>String</code> literal part
	 * of the expression is valid
	 * 
	 * @param value
	 *            the <code>String</code> literal part of the expression
	 * @return value if valid literal
	 * @throw IllegalArgumentException if invalid format is detected
	 * @see hr.fer.zemris.java.tecaj.hw5.db.filters.ConditionalExpression
	 */
	private String makeValue(String value) {
		if (value.charAt(0) != '\"' || value.charAt(value.length() - 1) != '\"') {
			throw new IllegalArgumentException(
					"string literal must be enclosed in quotes: " + value);
		}
		if (value.contains("<") || value.contains(">") || value.contains("=")) {
			throw new IllegalArgumentException(
					"operators not allowed in string literal: " + value);
		}
		String reducedvalue = value.substring(1, value.length() - 1);
		if (reducedvalue.contains("\"")) {
			throw new IllegalArgumentException(
					"quotes are not allowed within string literal: " + value);
		}
		return reducedvalue;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		if (record == null) {
			return false;
		}
		boolean isAccepted = true;
		for (ConditionalExpression exp : expressions) {
			isAccepted &= exp.getOperator().satisfied(
					exp.getField().get(record), exp.getValue());
		}
		return isAccepted;
	}

	/**
	 * This method counts the special wildcard character '*' within
	 * <code>String</code> literals.
	 * 
	 * @param literal
	 * @return number of wildcard characters within the <code>String</code>
	 */
	private int wildCardCount(String literal) {
		char[] literalChars = literal.toCharArray();
		int count = 0;
		for (char c : literalChars) {
			if (c == '*') {
				count++;
			}
		}
		return count;
	}
}
