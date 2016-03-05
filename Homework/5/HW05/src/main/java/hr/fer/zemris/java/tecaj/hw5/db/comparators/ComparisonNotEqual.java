package hr.fer.zemris.java.tecaj.hw5.db.comparators;
/**
 * This class defines an inequality operator.
 * @author Filip Džidić
 *
 */
public class ComparisonNotEqual implements IComparisonOperator {
	
	/**
	 * This method checks if the provided values are not equal.
	 * 
	 * @param value1 <code>String</code> from a field within our database
	 * @param value2 user provided <code>String</code> literal
	 * @return true if the both values are not equal
	 */
	public boolean satisfied(String value1, String value2) {
		return !value1.equals(value2);
	}

}
