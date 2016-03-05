package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This java bean class models a single comment on a blog entry in our blogging
 * web application.
 * 
 * @author Filip Džidić
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * Primary key of this class.
	 */
	private Long id;
	/**
	 * The blog entry on which the comment is posted.
	 */
	private BlogEntry blogEntry;
	/**
	 * The email of the user that made this comment.
	 */
	private String usersEMail;
	/**
	 * The comment's message.
	 */
	private String message;
	/**
	 * The date the comment was posted on.
	 */
	private Date postedOn;

	/**
	 * Getter method for ID.
	 * 
	 * @return the id of the comment
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter method for ID.
	 * 
	 * @param id
	 *            the new id being set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter method for BlogEntry.
	 * 
	 * @return the entry to which this comment is assigned to
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Setter method for <code>BlogENntry</code>
	 * 
	 * @param blogEntry
	 *            the new blog entry being set
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter method for blog comment poster's email.
	 * 
	 * @return the email of the person who made this comment
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter method for blog comment poster's email.
	 * 
	 * @param usersEMail
	 *            the new email being set
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter method for blog message.
	 * 
	 * @return the message being written on this commne
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Setter method for blog message.
	 * 
	 * @param message
	 *            the new message being set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter method for date of posting.
	 * 
	 * @return the date the blog was poted on.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter method for date of posting.
	 * 
	 * @param postedOn
	 *            the new date of posting
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
