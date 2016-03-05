package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * Interface for the data persistence subsystem.
 * 
 * @author Filip Džidić
 *
 */
public interface DAO {

	/**
	 * Retrieves entry with the provided <code>id</code>. Returns null if such
	 * an entry does not exist.
	 * 
	 * @param id
	 *            key of the entry
	 * @return entry or <code>null</code> if entry does not exist
	 * @throws DAOException
	 *             if an error occurs during fetching data
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Retrieves user with the provided <code>nick</code>. Returns null if such
	 * an user does not exist.
	 * 
	 * @param nick
	 *            nickname of the user
	 * @return user or <code>null</code> if user does not exist
	 * @throws DAOException
	 *             if an error occurs during fetching data
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;

	/**
	 * Retrieves comment with the provided <code>id</code>. Returns null if
	 * comment does not exist.
	 * 
	 * @param id
	 *            key of the comment
	 * @return comment or <code>null</code> if comment does not exist
	 * @throws DAOException
	 *             if an error occurs during fetching data
	 */
	public BlogComment getBlogComment(Long id) throws DAOException;

	/**
	 * Retrieves all blog entries assigned to a given <code>BlogUser</code>.
	 * 
	 * @param user
	 *            the user whose entries we are retrieving
	 * @return a list of all assigned blog entries
	 * @throws DAOException
	 *             if an error occurs during fetching data
	 */
	public List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException;

	/**
	 * Retrieves all blog comments assigned to a given <code>BlogEntry</code>.
	 * 
	 * @param entry
	 *            the entry whose comments we are retrieving
	 * @return a list of all assigned blog entry comments
	 * @throws DAOException
	 *             if an error occurs during fetching data
	 */
	public List<BlogComment> getBlogComments(BlogEntry entry)
			throws DAOException;

	/**
	 * Retrieves all registered users in our data model.
	 * 
	 * @return a list of all registered users
	 * @throws DAOException
	 *             if an error occurs during fetching data
	 */
	public List<BlogUser> getUsers() throws DAOException;

	/**
	 * Adds a new <code>BlogEntry</code> to our data model.
	 * 
	 * @param entry
	 *            the <code>BlogEntry</code> being added
	 * @throws DAOException
	 *             if an error occurs during fetching data
	 */
	public void addBlogEntry(BlogEntry entry) throws DAOException;

	/**
	 * Adds a new <code>BlogUser</code> to our data model.
	 * 
	 * @param user
	 *            the <code>BlogUser</code> being added
	 * @throws DAOException
	 *             if an error occurs during fetching data
	 */
	public void addBlogUser(BlogUser user) throws DAOException;

	/**
	 * Adds a new <code>BlogComment</code> to our data model.
	 * 
	 * @param comment
	 *            the <code>BlogComment</code> being added
	 * @throws DAOException
	 *             if an error occurs during fetching data
	 */
	public void addBlogComment(BlogComment comment) throws DAOException;

}
