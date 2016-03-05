package hr.fer.zemris.java.tecaj.hw5.db.fields;

import hr.fer.zemris.java.tecaj.hw5.db.data.StudentRecord;
/**
 * This class represents the lastName attribute in our database.
 * @author Filip Džidić
 *
 */
public 	class LastName implements IFieldValueGetter {
	/**
	 * returns the lastName attribute of a given n-tuple
	 */
	public String get (StudentRecord record) {
		return record.getLastName();
	}
}