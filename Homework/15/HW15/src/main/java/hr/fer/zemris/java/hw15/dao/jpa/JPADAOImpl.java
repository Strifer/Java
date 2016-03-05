package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Concrete implementation of our data model based on OR database technology,
 * hibernate.
 * 
 * @author Filip Džidić
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(
				BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		// BlogUser blogUser = JPAEMProvider.getEntityManager().find(
		// BlogUser.class, nick);
		try {
			BlogUser blogUser = (BlogUser) JPAEMProvider
					.getEntityManager()
					.createQuery("select b from BlogUser as b where b.nick=:n)")
					.setParameter("n", nick).getSingleResult();
			return blogUser;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public BlogComment getBlogComment(Long id) throws DAOException {
		BlogComment blogComment = JPAEMProvider.getEntityManager().find(
				BlogComment.class, id);
		return blogComment;
	}

	@Override
	public List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogEntry> entries = (List<BlogEntry>) JPAEMProvider
				.getEntityManager()
				.createQuery("select b from BlogEntry as b where b.creator=:bu")
				.setParameter("bu", user).getResultList();
		return entries;
	}

	@Override
	public List<BlogComment> getBlogComments(BlogEntry entry)
			throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<BlogComment> comments = (List<BlogComment>) em
				.createQuery(
						"select b from BlogComment as b where b.blogEntry=:be")
				.setParameter("be", entry).getResultList();
		return comments;
	}

	@Override
	public List<BlogUser> getUsers() throws DAOException {
		return JPAEMProvider.getEntityManager()
				.createQuery("select b from BlogUser b", BlogUser.class)
				.getResultList();

	}

	@Override
	public void addBlogEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().persist(entry);
		entry.getCreator().getBlogEntries().add(entry);

	}

	@Override
	public void addBlogUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(user);

	}

	@Override
	public void addBlogComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);
		comment.getBlogEntry().getComments().add(comment);

	}

}
