package hr.fer.zemris.java.tecaj.hw5.db.comparators;
/**
 * This class defines a "greater than" operator.
 * @author Filip Džidić
 *
 */
public class ComparisonGreater implements IComparisonOperator {

	/**
	 * This method checks if value1 is greater than value2
	 * 
	 * @param value1 <code>String</code> from a field within our database
	 * @param value2 user provided <code>String</code> literal
	 * @return true if value1 is greater
	 */
	public boolean satisfied(String value1, String value2) {
		return value1.compareTo(value2)>0;
	}

}
