package hr.fer.zemris.java.hw14.dao.sql;

import hr.fer.zemris.java.hw14.beans.Poll;
import hr.fer.zemris.java.hw14.beans.PollOptions;
import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is an implementation of our data persistence subsystem based on
 * SQL relational database technology.
 * 
 * @author Filip Džidić
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> ntuples = new LinkedList<>();
		Connection connection = SQLConnectionProvider.getConnection();
		try (PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM Polls ORDER BY ID")) {
			try (ResultSet result = statement.executeQuery()) {
				while (result != null && result.next()) {
					Poll poll = new Poll(result.getLong(1),
							result.getString(2), result.getString(3));
					ntuples.add(poll);
				}
			}
		} catch (Exception e) {
			throw new DAOException("Error while accessing database.");
		}
		return ntuples;
	}

	@Override
	public List<PollOptions> getPollOptions(long pollID) throws DAOException {
		List<PollOptions> ntuples = new LinkedList<>();
		Connection connection = SQLConnectionProvider.getConnection();
		try (PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM PollOptions WHERE pollID = ?")) {
			statement.setLong(1, pollID);
			try (ResultSet result = statement.executeQuery()) {
				while (result != null && result.next()) {
					PollOptions option = new PollOptions(result.getLong(1),
							result.getString(2), result.getString(3),
							result.getLong(4), result.getLong(5));
					ntuples.add(option);
				}
			}
		} catch (Exception e) {
			throw new DAOException("Error while accessing database.");
		}
		return ntuples;
	}

	@Override
	public PollOptions getPollOption(long id) throws DAOException {
		Connection connection = SQLConnectionProvider.getConnection();
		PollOptions option = null;
		try (PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM PollOptions WHERE ID = ?")) {
			statement.setLong(1, id);
			try (ResultSet result = statement.executeQuery()) {
				while (result != null && result.next()) {
					option = new PollOptions(result.getLong(1),
							result.getString(2), result.getString(3),
							result.getLong(4), result.getLong(5));
				}
			}
		} catch (Exception e) {
			throw new DAOException("Error while accessing database.");
		}
		return option;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection connection = SQLConnectionProvider.getConnection();
		try (PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM Polls WHERE ID = ?")) {
			statement.setLong(1, id);
			try (ResultSet result = statement.executeQuery()) {
				while (result != null && result.next()) {
					poll = new Poll(result.getLong(1), result.getString(2),
							result.getString(3));
				}
			}
		} catch (Exception e) {
			throw new DAOException("Error while accessing database.");
		}
		return poll;
	}

	@Override
	public void incrementVote(long id) throws DAOException {
		Connection connection = SQLConnectionProvider.getConnection();
		try (PreparedStatement statement = connection
				.prepareStatement("UPDATE PollOptions SET VOTESCOUNT = VOTESCOUNT+1 WHERE ID=?")) {
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new DAOException("Error while accessing database.");
		}

	}

	@Override
	public List<PollOptions> getSortedPollOptions(long pollID)
			throws DAOException {
		List<PollOptions> ntuples = new LinkedList<>();
		Connection connection = SQLConnectionProvider.getConnection();
		try (PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM PollOptions WHERE pollID = ? ORDER BY VOTESCOUNT DESC")) {
			statement.setLong(1, pollID);
			try (ResultSet result = statement.executeQuery()) {
				while (result != null && result.next()) {
					PollOptions option = new PollOptions(result.getLong(1),
							result.getString(2), result.getString(3),
							result.getLong(4), result.getLong(5));
					ntuples.add(option);
				}
			}
		} catch (Exception e) {
			throw new DAOException("Error while accessing database.");
		}
		return ntuples;
	}

}
