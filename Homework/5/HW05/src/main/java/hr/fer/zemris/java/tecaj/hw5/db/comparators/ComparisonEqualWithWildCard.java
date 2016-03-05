package hr.fer.zemris.java.tecaj.hw5.db.comparators;
/**
 * This class defines a special equality with wild card operator
 * @author Filip Džidić
 *
 */
public class ComparisonEqualWithWildCard implements IComparisonOperator {

	/**
	 * This method checks if value1 begins with or ends with (or both) value2.
	 * Value2 contains a special asterisk operator which splits the prefix from the suffix.
	 * 
	 * @param value1 <code>String</code> from a field within our database
	 * @param value2 user provided <code>String</code> literal
	 * @return true if the value2 is a prefix or suffix (or both) of value1
	 */
	public boolean satisfied(String value1, String value2) {
		String[] substrings = value2.split("[*]", 2);
		return value1.startsWith(substrings[0]) && value1.endsWith(substrings[1]);
	}

}
