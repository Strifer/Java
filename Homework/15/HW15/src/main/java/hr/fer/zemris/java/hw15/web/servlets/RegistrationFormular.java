package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.utility.DigestUtility;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * This helper class handles form errors and repeated input within our web
 * application.
 * 
 * @author Filip Džidić
 *
 */
public class RegistrationFormular {
	/**
	 * User's first name.
	 */
	private String firstName;
	/**
	 * User's last name.
	 */
	private String lastName;
	/**
	 * User's email.
	 */
	private String email;
	/**
	 * User's nickname.
	 */
	private String nick;
	/**
	 * User's password.
	 */
	private String pass;

	/**
	 * Collection of all possible errors.
	 */
	Map<String, String> error = new HashMap<>();

	/**
	 * Default constructor initializs all variables to null.
	 */
	public RegistrationFormular() {
	}

	/**
	 * Retrieves an error from our error correction.
	 * 
	 * @param var
	 *            the error type being retrieved
	 * @return the stored error or null if it does not exist
	 */
	public String getError(String var) {
		return error.get(var);
	}

	/**
	 * Checks if any errors have been caught
	 * 
	 * @return true if errors are found
	 */
	public boolean hasErrors() {
		return !error.isEmpty();
	}

	/**
	 * Checks if a single error has been recorded for the provided parameter.
	 * 
	 * @param var
	 *            the parameter whose error we are looking for
	 * @return true if there is an error
	 */
	public boolean hasError(String var) {
		return error.containsKey(var);
	}

	/**
	 * Creates and initializes all the necessary form parameters from a provided
	 * servlet request.
	 * 
	 * @param req
	 *            request holding all our necessary parameters
	 */
	public void createFromRequest(HttpServletRequest req) {
		this.firstName = prepare(req.getParameter("firstName"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.email = prepare(req.getParameter("email"));
		this.nick = prepare(req.getParameter("nick"));
		this.pass = prepare(req.getParameter("pass"));
	}

	/**
	 * Makes a new Blog User from recorded form parameters.
	 * 
	 * @param user
	 *            the new user being created
	 * @throws NoSuchAlgorithmException
	 *             if an invalid crypting algorithm is selected
	 */
	public void makeBlogUser(BlogUser user) throws NoSuchAlgorithmException {
		user.setBlogEntries(null);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setNick(nick);
		user.setPasswordHash(DigestUtility.generateDigest(pass));
	}

	/**
	 * This method validates potential logIn form errors.
	 */
	public void validateLogin() {
		error.clear();
		BlogUser user = null;
		if (this.nick.isEmpty()) {
			this.error.put("nick", "A nickname is mandatory");
		} else {
			user = DAOProvider.getDAO().getBlogUser(nick);
			if (user == null) {
				this.error.put("nick", "The nickname is not valid.");
			}
		}

		if (this.pass.isEmpty()) {
			this.error.put("pass", "This field is mandatory");
		} else if (user != null) {
			try {
				String hashed = DigestUtility.generateDigest(this.pass);
				if (!hashed.equals(user.getPasswordHash())) {
					this.error.put("pass", "Invalid password");
				}
			} catch (NoSuchAlgorithmException ignorable) {
			}
		}

	}

	/**
	 * This method validates potential registration form errors.
	 */
	public void validate() {
		error.clear();

		if (this.nick.isEmpty()) {
			this.error.put("nick", "A nickname is mandatory");
		} else {
			BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
			if (user != null) {
				this.error.put("nick", "This nickname is taken.");
			}
		}

		if (this.firstName.isEmpty()) {
			this.error.put("firstName", "This field is mandatory");
		}

		if (this.lastName.isEmpty()) {
			this.error.put("lastName", "This field is mandatory");
		}

		if (this.pass.isEmpty()) {
			this.error.put("pass", "This field is mandatory");
		}

		if (this.email.isEmpty()) {
			error.put("email", "This field is mandatory");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				error.put("email", "EMail nije ispravnog formata.");
			}
		}

	}

	/**
	 * Getter method for the firstName.
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter method for the firstName.
	 * 
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter method for the lastName.
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter method for the lastName.
	 * 
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter method for the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter method for the email.
	 * 
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter method for the nickname.
	 * 
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter method for the nickname.
	 * 
	 * @param nick
	 *            the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter method for the password.
	 * 
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * Setter method for the password.
	 * 
	 * @param pass
	 *            the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * Prepares provided parameters, if they haven't been provided (they're
	 * null) they are switched into empty strings.
	 * 
	 * @param s
	 *            the provided parameter
	 * @return prepared parameter for further i
	 */
	public String prepare(String s) {
		if (s == null || s.isEmpty()) {
			return "";
		}
		return s;
	}

}
