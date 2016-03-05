package hr.fer.zemris.java.tecaj.hw5.db.fields;

import hr.fer.zemris.java.tecaj.hw5.db.data.StudentRecord;
/**
 * This class represents the firstName attribute in our database.
 * @author Filip Džidić
 *
 */
public class FirstName implements IFieldValueGetter {
	/**
	 * returns the firstName attribute of a given n-tuple
	 */
	public String get (StudentRecord record) {
		return record.getFirstName();
	}
}
