package hr.fer.zemris.java.hw14.beans;

/**
 * The class <code>PollOptions</code> models options available for a given
 * <code>Poll</code>. <br>
 * Each <code>Poll</code> has several options and each option has a title and an
 * assigned link.
 * 
 * @author Filip Džidić
 *
 */
public class PollOptions {
	/** the index of a given n-tuple in the poll specified by pollID */
	private long id;
	/** the name of the option the client can vote for */
	private String optionTitle;
	/** a link related to that option */
	private String optionLink;
	/** the index of the poll in which this option is available */
	private long pollID;
	/** votes are stored here */
	private long votesCount;

	/**
	 * Constructs a new instance of <code>PollOptions</code> with user provided
	 * data.
	 * 
	 * @param id
	 *            the index of a given n-tuple in the poll specified by pollID
	 * @param optionTitle
	 *            the name of the option the client can vote for
	 * @param optionLink
	 *            a link related to that option
	 * @param pollID
	 *            the index of the poll in which this option is available
	 * @param votesCount
	 *            votes are counted here
	 */
	public PollOptions(long id, String optionTitle, String optionLink,
			long pollID, long votesCount) {
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}

	/**
	 * Getter method for n-tuple's ID.
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter method for n-tuple's ID.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter method for the poll option's title.
	 * 
	 * @return the optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Setter method for the poll option's title.
	 * 
	 * @param optionTitle
	 *            the optionTitle to set
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Getter method for the poll option title's link.
	 * 
	 * @return the optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Setter method for the poll option title's link.
	 * 
	 * @param optionLink
	 *            the optionLink to set
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
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
	 * Getter method for option's recorded votes.
	 * 
	 * @return the votesCount
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Setter method for option's recorded votes.
	 * 
	 * @param votesCount
	 *            the votesCount to set
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

}
