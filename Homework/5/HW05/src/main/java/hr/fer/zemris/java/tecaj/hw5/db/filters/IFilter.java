package hr.fer.zemris.java.tecaj.hw5.db.filters;

import hr.fer.zemris.java.tecaj.hw5.db.data.StudentRecord;
/**
 * This interface defines filters which can be used to form queries in our database.
 * It guarantees that every filter will contain a single method used for determining whether or not
 * a given record will be displayed or not.
 * @author Filip Džidić
 *
 */
public interface IFilter {
	/**
	 * This method determines whether or not a given n-tuple will pass through the filter.
	 * @param record the n-tuple being filtered
	 * @return true if record satisfies all of the filter's conditions
	 */
	public boolean accepts(StudentRecord record);
}
