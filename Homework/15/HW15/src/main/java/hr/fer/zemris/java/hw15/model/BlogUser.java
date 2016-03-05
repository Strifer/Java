package hr.fer.zemris.java.hw15.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This java bean class models a single registered user in our blogging web
 * application.
 * 
 * @author Filip Džidić
 *
 */
@Entity
@Table(name = "blog_users")
public class BlogUser {
	/**
	 * The primary key of this class.
	 */
	private Long id;
	/**
	 * The user's first name.
	 */
	private String firstName;
	/**
	 * The user's last name.
	 */
	private String lastName;
	/**
	 * The user's nickname.
	 */
	private String nick;
	/**
	 * The user's email.
	 */
	private String email;
	/**
	 * The user's hashed account password.
	 */
	private String passwordHash;
	/**
	 * List of all blog entries the user has made.
	 */
	private List<BlogEntry> blogEntries;

	/**
	 * Getter method for the id.
	 * 
	 * @return the id of the user
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
	 *            the new id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter method for the first name.
	 * 
	 * @return the firstName of the user
	 */
	@Column(length = 30, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter method for the first name.
	 * 
	 * @param firstName
	 *            the new firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter method for the last name.
	 * 
	 * @return the lastName of the user
	 */
	@Column(length = 30, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter method for the last name.
	 * 
	 * @param lastName
	 *            the new lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter method for the user's nickname.
	 * 
	 * @return the nick of the user
	 */
	@Column(length = 30, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}

	/**
	 * Setter method for the user's nickname.
	 * 
	 * @param nick
	 *            the new nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter method for the user's email.
	 * 
	 * @return the email of the user
	 */
	@Column(length = 256, nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * Setter method for the user's email.
	 * 
	 * @param email
	 *            the new email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter method for the hashed password of the user.
	 * 
	 * @return the passwordHash of the user
	 */
	@Column(length = 40, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
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
		if (!(obj instanceof BlogUser))
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Setter method for the hashed password of the user.
	 * 
	 * @param passwordHash
	 *            the new passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Getter method for list of entries assigned to this user.
	 * 
	 * @return the blogEntries assigned to this user
	 */
	@OneToMany(mappedBy = "creator", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * Setter method for list of entries assigned to this user.
	 * 
	 * @param blogEntries
	 *            the new blogEntries to set
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

}
