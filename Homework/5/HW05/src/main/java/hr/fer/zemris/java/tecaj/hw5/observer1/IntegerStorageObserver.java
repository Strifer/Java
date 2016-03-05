package hr.fer.zemris.java.tecaj.hw5.observer1;

/**
 * This interface defines actions that all registered observers need to perform
 * when the state of the subject is changed.
 * 
 * @author Filip Džidić
 *
 */
public interface IntegerStorageObserver {
	/**
	 * This method defines the action that needs to be performed by the
	 * registered observer every time the state of the subject changes.
	 * 
	 * @param istorage
	 *            the subject whose state we're observing
	 */
	public void valueChanged(IntegerStorage istorage);
}
