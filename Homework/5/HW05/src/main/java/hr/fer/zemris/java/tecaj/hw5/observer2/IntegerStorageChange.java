package hr.fer.zemris.java.tecaj.hw5.observer2;
/**
 * This class is used for keeping track of states in the subject as they are updated and changed.
 * @author Filip Džidić 
 *
 */
public class IntegerStorageChange {
	/** the subject whose states we are keeping track of */
	private IntegerStorage subject;
	/** the old state */
	private int originalValue;
	/** the updated state */
	private int changedValue;
	
	/**
	 * Constructs a new <code>IntegerStorageChange</code> with provided subject and states.
	 * @param subject the subject whose states we are keeping track of
	 * @param original the old state
	 * @param changed the updated state
	 */
	public IntegerStorageChange(IntegerStorage subject, int original, int changed) {
		this.subject=subject;
		originalValue=original;
		changedValue=changed;
	}

	/**
	 * Getter method for the reference of the subject we are tracking
	 * @return the subject
	 */
	public IntegerStorage getSubject() {
		return subject;
	}

	/**
	 * Getter method for the old state
	 * @return the originalValue
	 */
	public int getOriginalValue() {
		return originalValue;
	}

	/**
	 * Getter method for the updated state
	 * @return the changedValue
	 */
	public int getChangedValue() {
		return changedValue;
	}
	
	
}
