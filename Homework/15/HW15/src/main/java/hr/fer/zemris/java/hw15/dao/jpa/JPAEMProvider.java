package hr.fer.zemris.java.hw15.dao.jpa;

import hr.fer.zemris.java.hw15.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * This class serves as a storage for connections used by our database. It's job
 * is also to prepare and close off entity managers used for data transactions.
 * 
 * @author Filip Džidić
 *
 */
public class JPAEMProvider {

	/**
	 * Connections are kept and stored here.
	 */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Initializes and retrieves local <code>EntityManager</code> for data
	 * transaction.
	 * 
	 * @return local entity manager
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if (ldata == null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * This method attempts to commit and finish data transactions.
	 * 
	 * @throws DAOException
	 *             if an error occurs during fetching data
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if (ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

	/**
	 * This class serves as a storage class for our <code>EntityManagaer</code>.
	 * 
	 * @author Filip Džidić
	 *
	 */
	private static class LocalData {
		/**
		 * Stores our entity manager through which we interact with our
		 * persistence context.
		 */
		EntityManager em;
	}

}
