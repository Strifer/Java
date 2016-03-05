package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.dao.sql.SQLDAO;

/**
 * This singleton class remembers which service it needs to offer as a data
 * persistence model.
 * 
 * @author Filip Džidić
 *
 */
public class DAOProvider {

	/** data persistence model */
	private static DAO dao = new SQLDAO();

	/**
	 * Getter method for our data persistence model.
	 * 
	 * @return an object which encapsulates access to the data persistence layer
	 */
	public static DAO getDao() {
		return dao;
	}

}
