package hr.fer.zemris.java.hw14.beans;

/**
 * The class <code>Poll</code> models a single table holding poll data within
 * our database. <br>
 * Each <code>Poll</code> has a unique ID, a main title and an instruction
 * message.
 * 
 * @author Filip Džidić
 *
 */
public class Poll {
	/** the poll's ID */
	private long pollID;
	/** the poll's main title */
	private String title;
	/** the poll's instruction message */
	private String message;

	/**
	 * Constructs a new instance of <code>Poll</code> with user provided data.
	 * 
	 * @param pollID
	 *            the poll's ID
	 * @param title
	 *            the poll's main title
	 * @param message
	 *            the poll's instruction message
	 */
	public Poll(long pollID, String title, String message) {
		this.pollID = pollID;
		this.title = title;
		this.message = message;
	}

	/**
	 * Getter method for the poll's ID.
	 * 
	 * @return the pollID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Setter method for the poll's ID.
	 * 
	 * @param pollID
	 *            the pollID to set
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Getter method for the poll's main title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter method for the poll's main title.
	 * 
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter method or the poll's instruction message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter method for the poll's instruction message.
	 * 
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
