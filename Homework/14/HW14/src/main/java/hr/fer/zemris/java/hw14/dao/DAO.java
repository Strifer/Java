package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.beans.Poll;
import hr.fer.zemris.java.hw14.beans.PollOptions;
import java.util.List;

/**
 * Interface for the data persistence subsystem.
 * 
 * @author Filip Džidić
 *
 */
public interface DAO {

	/**
	 * Retrieves all existing concrete polls within our data subsystem.
	 * 
	 * @return a list of {@link Poll} entries
	 * @throws DAOException
	 *             if some error occurs
	 */
	public List<Poll> getPolls() throws DAOException;

	/**
	 * Retrieves all option data from a specified poll within our data
	 * subsystem.
	 * 
	 * @param pollID
	 *            the index of the poll whose data we are retrieving
	 * @return a list of {@link PollOptions}
	 * @throws DAOException
	 *             if some error occurs
	 */
	public List<PollOptions> getPollOptions(long pollID) throws DAOException;

	/**
	 * Retrieves all option data from a specified poll within our data subsystem
	 * sorted by votes in descending order.
	 * 
	 * @param pollID
	 *            the index of the poll whose data we are retrieving
	 * @return a list of {@link PollOptions}
	 * @throws DAOException
	 *             if some error occurs
	 */
	public List<PollOptions> getSortedPollOptions(long pollID)
			throws DAOException;

	/**
	 * Retrieves a specific {@link PollOptions} within our data subsystem as
	 * defined by its id.
	 * 
	 * @param id
	 *            the option's index
	 * @return the specific {@link PollOptions }
	 * @throws DAOException
	 *             if some error occurs
	 */
	public PollOptions getPollOption(long id) throws DAOException;

	/**
	 * Retrieves a specific {@link Poll} within our data subsystem as defined by
	 * its id.
	 * 
	 * @param id
	 *            the poll's index
	 * @return the specific {@link Poll }
	 * @throws DAOException
	 *             if some error occurs
	 */
	public Poll getPoll(long id) throws DAOException;

	/**
	 * Increments the votecount inside {@link PollOptions} by one.
	 * 
	 * @param id
	 *            the primary id of the ntuple inside {@link PollOptions} table
	 */
	public void incrementVote(long id);

}
