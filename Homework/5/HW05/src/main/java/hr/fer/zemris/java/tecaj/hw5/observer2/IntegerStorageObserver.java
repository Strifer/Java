package hr.fer.zemris.java.tecaj.hw5.observer2;
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
	 * @param ichange
	 *            an object which tracks the changes within our subject
	 */
	public void valueChanged(IntegerStorageChange ichange);
}
