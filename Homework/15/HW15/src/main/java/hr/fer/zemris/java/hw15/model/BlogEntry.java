package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This java bean class models a single entry on a blog user's page in our
 * blogging web application.
 * 
 * @author Filip Džidić
 *
 */
@Entity
@Table(name = "blog_entries")
@NamedQueries({ @NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Cacheable(true)
public class BlogEntry {
	/**
	 * Primary key of this class.
	 */
	private Long id;
	/**
	 * List of comments assigned to this entry.
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * Date the entry was created at.
	 */
	private Date createdAt;
	/**
	 * Data the entry was last modified at.
	 */
	private Date lastModifiedAt;
	/**
	 * Title of the entry.
	 */
	private String title;
	/**
	 * The text of the entry.
	 */
	private String text;
	/**
	 * The owner of this blog entry
	 */
	private BlogUser creator;

	/**
	 * Getter method for ID
	 * 
	 * @return the id of this class
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter method for id.
	 * 
	 * @param id
	 *            the new id being set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter method for comments.
	 * 
	 * @return all of the comments assigned to this entry
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Setter method for new convicts.
	 * 
	 * @param comments
	 *            the new comment list being set
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Getter method for the date the entry was created on.
	 * 
	 * @return the date the entry was created on
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter method for the date the entry was created on.
	 * 
	 * @param createdAt
	 *            the new date being set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Getter method for the date this entry was last modified on.
	 * 
	 * @return the date this entry was last mofmo
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Setter method for the date this entry was last modified on.
	 * 
	 * @param lastModifiedAt
	 *            the new lastModifiedAt to set
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Getter method for the entry's title.
	 * 
	 * @return the title of the entry
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Setter method for the entry's title.
	 * 
	 * @param title
	 *            the new title being set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter method for the entry's text.
	 * 
	 * @return the text of the entry
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Setter method for the entry's text.
	 * 
	 * @param text
	 *            the new text being set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter method for the creator and owner of this entry.
	 * 
	 * @return the creator and owner of this entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Setter method for the creator of this entry.
	 * 
	 * @param creator
	 *            the new creator to set
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
