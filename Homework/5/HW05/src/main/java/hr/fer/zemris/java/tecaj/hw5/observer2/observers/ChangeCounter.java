package hr.fer.zemris.java.tecaj.hw5.observer2.observers;

import hr.fer.zemris.java.tecaj.hw5.observer2.IntegerStorageChange;
import hr.fer.zemris.java.tecaj.hw5.observer2.IntegerStorageObserver;
/**
 * This class defines an observer which follows how many times a subject's state
 * has been changed since registering.
 * 
 * @author Filip Džidić
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/** counts how many times the subject's state has changed */
	private int counter;
	
	/**
	 * Prints out the number of time the subject's state has been changed since
	 * this observer was registered.
	 */
	public void valueChanged(IntegerStorageChange ichange) {
		System.out.println("Number of value changes since tracking: "+(++counter));
	}
}
