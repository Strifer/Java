package hr.fer.zemris.java.hw14.dao.sql;

import java.beans.PropertyVetoException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * This WebListener initializes our database so it's ready for use. The database
 * is initialized only if the necessary tables aren't correctly defined.
 * 
 * @author Filip Džidić
 *
 */
@WebListener
public class InitializeDB implements ServletContextListener {

	/** this map is used to store generated poll indices */
	private Map<Long, Long> indices = new HashMap<Long, Long>();

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		String filePath = sce.getServletContext().getRealPath(
				"/WEB-INF/dbsettings.properties");
		if (filePath == null) {
			throw new RuntimeException("No properties found");
		}
		Properties properties = new Properties();
		try (BufferedInputStream str = new BufferedInputStream(
				new FileInputStream(filePath))) {
			properties.load(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String URL = "jdbc:derby://" + properties.getProperty("host") + ":"
				+ properties.getProperty("port") + "/"
				+ properties.getProperty("name") + ";user="
				+ properties.getProperty("user") + ";password="
				+ properties.getProperty("password");

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e) {
			throw new RuntimeException(
					"Error while initializing connection pool.", e);
		}

		cpds.setJdbcUrl(URL);

		sce.getServletContext().setAttribute("dbpool", cpds);

		Connection connection = null;
		try {
			connection = cpds.getConnection();
			createTable("Polls", connection, sce);
			createTable("PollOptions", connection, sce);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce
				.getServletContext().getAttribute("dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Retrieves a specific SQL statement for creating tables based on the table
	 * name.
	 * 
	 * @param tableName
	 *            the name of the table being created
	 * @return an SQL statement for creating the table
	 */
	private String getSQLStatement(String tableName) {
		if (tableName.equals("Polls")) {
			return "CREATE TABLE Polls"
					+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
					+ "title VARCHAR(150) NOT NULL, "
					+ "message CLOB(2048) NOT NULL)";
		} else if (tableName.equals("PollOptions")) {
			return "CREATE TABLE PollOptions"
					+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
					+ "optionTitle VARCHAR(100) NOT NULL, "
					+ "optionLink VARCHAR(150) NOT NULL, " + "pollID BIGINT, "
					+ "votesCount BIGINT, "
					+ "FOREIGN KEY (pollID) REFERENCES Polls(id))";
		} else {
			throw new RuntimeException("Undefined table name");
		}
	}

	/**
	 * This method attempts to create tables inside our database. Tables are
	 * only initialized if they don't already exist or if they are empty.
	 * 
	 * @param tableName
	 *            the name of the table being created
	 * @param connection
	 *            connection to our database
	 * @param sce
	 *            notifies any servlet changes
	 * @throws SQLException
	 *             if a database error occurs
	 */
	private void createTable(String tableName, Connection connection,
			ServletContextEvent sce) throws SQLException {
		DatabaseMetaData dbmd = connection.getMetaData();
		ResultSet rs = dbmd
				.getTables(null, null, tableName.toUpperCase(), null);
		if (rs.next()) {

			try (PreparedStatement statement = connection
					.prepareStatement("SELECT COUNT(*) FROM " + tableName)) {
				try (ResultSet set = statement.executeQuery()) {
					set.next();
					if (set.getString(1).equals("0")) {
						fillData(tableName, connection, statement, sce);
					}
				}

			}
			System.out.println("Table already exists");
		} else {
			System.out.println("Creating tables...");
			try (PreparedStatement statement = connection
					.prepareStatement(getSQLStatement(tableName))) {
				statement.execute();
				fillData(tableName, connection, statement, sce);
			} catch (Exception e) {

			}
		}
		return;
	}

	/**
	 * This method fills the created tables with data from textfiles.
	 * 
	 * @param tableName
	 *            the name of the table being filled
	 * @param connection
	 *            connection to our database
	 * @param statement
	 *            used for storing and executing SQL queries
	 * @param sce
	 *            notifies any servlet changes
	 */
	private void fillData(String tableName, Connection connection,
			PreparedStatement statement, ServletContextEvent sce) {
		try {
			String path = "";
			List<String> data = null;
			switch (tableName) {

				case "Polls":
					path = sce.getServletContext().getRealPath(
							"/WEB-INF/" + tableName + ".txt");
					if (path == null) {
						throw new RuntimeException("Text file not found");
					}
					data = Files.readAllLines(Paths.get(path));
					long i = 1;
					for (String s : data) {
						String[] attributes = s.split("\\t");
						statement = connection
								.prepareStatement(
										"INSERT INTO Polls (title, message) VALUES (?,?)",
										Statement.RETURN_GENERATED_KEYS);
						statement.setString(1, attributes[1]);
						statement.setString(2, attributes[2]);
						statement.execute();
						ResultSet set = statement.getGeneratedKeys();
						if (set != null && set.next()) {
							indices.put(i++, set.getLong(1));
						}
					}

					System.out.println(indices.toString());
					break;

				case "PollOptions":
					path = sce.getServletContext().getRealPath(
							"/WEB-INF/" + tableName + ".txt");
					if (path == null) {
						throw new RuntimeException("Text file not found");
					}
					data = Files.readAllLines(Paths.get(path));
					for (String s : data) {
						String[] attributes = s.split("\\t");
						statement = connection
								.prepareStatement(
										"INSERT INTO PollOptions "
												+ "(optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)",
										Statement.RETURN_GENERATED_KEYS);
						statement.setString(1, attributes[1]);
						statement.setString(2, attributes[2]);
						statement.setLong(3,
								indices.get(Long.parseLong(attributes[3])));
						statement.setDouble(4, Long.parseLong(attributes[4]));
						statement.execute();
					}
					break;

				default:
					throw new RuntimeException();
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

}
