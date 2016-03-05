package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;

/**
 * This class serves as a storage for connections used by our database.
 * 
 * @author Filip Džidić
 *
 */
public class SQLConnectionProvider {

	/** connections are stored here */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Sets a connection for the current active thread.
	 * 
	 * @param con
	 *            connection to our database
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Retrieves the connection which the current thread can use.
	 * 
	 * @return connection to our database
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}
