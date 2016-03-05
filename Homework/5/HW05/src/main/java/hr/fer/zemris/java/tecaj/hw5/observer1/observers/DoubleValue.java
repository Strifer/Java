package hr.fer.zemris.java.tecaj.hw5.observer1.observers;

import hr.fer.zemris.java.tecaj.hw5.observer1.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw5.observer1.IntegerStorageObserver;
/**
 * This class defines an observer which doubles the subject's value every time the state changes.
 * Upon being notified two times this observer automatically unregisters itself from the observer list.
 * 
 * @author Filip Džidić
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/** tracks how many times it has been notified */
	int timesCalled;
	
	/**
	 * Prints out double of the subject's current value
	 */
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Double value: "+istorage.getValue()*2);
		timesCalled++;
		if(timesCalled==2) {
			istorage.removeObserver(this);
		}
	}
	
}
