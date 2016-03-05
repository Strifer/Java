package hr.fer.zemris.java.tecaj.hw5.db.fields;

import hr.fer.zemris.java.tecaj.hw5.db.data.StudentRecord;
/**
 * This interface  defines legal fields within the query command of
 * our database. Each field is associated with a given attribute in our database.
 * Currently legal fields are
 * <br>firstName<br>
 * lastName<br>
 * jmbag<br>
 * @author Filip Džidić
 *
 */
public interface IFieldValueGetter {
	/**
	 * Returns the value of the specified attribute of a given n-tuple.
	 * @param record the n-tuple whose value we are extracting
	 * @return the value of specified attribute
	 */
	public String get (StudentRecord record);
}
