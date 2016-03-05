package hr.fer.zemris.java.tecaj.hw5.db.fields;

import hr.fer.zemris.java.tecaj.hw5.db.data.StudentRecord;
/**
 * This class represents the jmbag attribute in our database.
 * @author Filip Džidić
 *
 */
public class Jmbag implements IFieldValueGetter {
	/**
	 * returns the jmbag attribute of a given n-tuple
	 */
	public String get (StudentRecord record) {
		return record.getJmbag();
	}
}
