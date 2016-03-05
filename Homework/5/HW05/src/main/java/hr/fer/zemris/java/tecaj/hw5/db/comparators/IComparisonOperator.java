package hr.fer.zemris.java.tecaj.hw5.db.comparators;

/**
 * This interface defines legal operators within the query command of our
 * database. Each operator is equipped with a single method which compares two
 * values with a certain logic.
 * 
 * @author Filip Džidić
 *
 */
public interface IComparisonOperator {
	/**
	 * This method checks if the provided values satisfy the relation as defined
	 * by the specific operator which implements this interface.
	 * 
	 * @param value1 <code>String</code> from a field within our database
	 * @param value2 user provided <code>String</code> literal
	 * @return true if the operation is satisfied
	 */
	public boolean satisfied(String value1, String value2);
}
