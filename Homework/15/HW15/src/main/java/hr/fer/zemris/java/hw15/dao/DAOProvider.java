package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * This singleton class remembers which service it needs to offer as a data
 * persistence model.
 * 
 * @author Filip Džidić
 *
 */
public class DAOProvider {
	/**
	 * Concrete data persistence model.
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Getter method for our data persistence model.
	 * 
	 * @return an object which encapsulates access to the data persistence layer
	 */
	public static DAO getDAO() {
		return dao;
	}

}
