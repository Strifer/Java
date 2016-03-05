package hr.fer.zemris.java.tecaj.hw5.db.data;

import hr.fer.zemris.java.tecaj.hw5.db.filters.IFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents a single instance of our database. Our database
 * consists of a single relation of <code>StudentRecord</code> n-tuples. This
 * class provides two ways of retrieving data, either quickly through it's index
 * or through filtering all records with a user provided query command.
 * 
 * @author Filip Džidić
 * @see hr.fer.zemris.java.tecaj.hw5.db.data.StudentRecord
 *
 */
public class StudentDatabase {
	/** this private collection is used for quick indexed retrieval of entries */
	private HashMap<String, StudentRecord> indexedData;
	/** this private collection is used for filtering through a query command */
	private ArrayList<StudentRecord> data;

	/**
	 * Constructor takes a list of entries and constructs two collections for
	 * future retrieval.
	 * 
	 * @param list
	 *            a list of all of the entries within our database
	 */
	public StudentDatabase(List<String> list) {
		indexedData = new HashMap<String, StudentRecord>();
		data = new ArrayList<StudentRecord>();
		for (String s : list) {
			String[] parameters = s.split("\\t+");
			StudentRecord record = new StudentRecord(parameters[0],
					parameters[2], parameters[1],
					Integer.parseInt(parameters[3]));
			indexedData.put(parameters[0], record);
			data.add(record);
		}
	}

	/**
	 * This method returns a record with a given index.
	 * 
	 * @param jmbag
	 *            unique number assigned to each different entry within our
	 *            database
	 * @return the appropriate record if it exists, returns null if it does not
	 *         exist
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return indexedData.get(jmbag);
	}

	/**
	 * This method goes through all entries within our database and returns a
	 * list of all entries which satisfy certain conditions provided in the
	 * filter.
	 * 
	 * @param filter conditions which a given entry must satisfy to be shown in output
	 * @return a list of all entries which satisfy the provided conditions
	 */
	public List<StudentRecord> filter(IFilter filter) {
		ArrayList<StudentRecord> list = new ArrayList<>();
		for (StudentRecord sr : data) {
			if (filter.accepts(sr)) {
				list.add(sr);
			}
		}
		return list;
	}

}
