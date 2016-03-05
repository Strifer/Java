package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * This class offers two easy methods for keeping track of created entity
 * manager factories.
 * 
 * @author Filip Džidić
 *
 */
public class JPAEMFProvider {

	/**
	 * The entity manager factory is stored here. It is used for interacting
	 * with the persistence unit.
	 */
	public static EntityManagerFactory emf;

	/**
	 * Getter method for <code>EntityManagerFactory</code>.
	 * 
	 * @return stored entity manager factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Setter method for <code>EntityManagerFactory</code>.
	 * 
	 * @param emf
	 *            the new entity manager factory
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}
