package hr.fer.zemris.java.tecaj.hw5.observer1.observers;

import hr.fer.zemris.java.tecaj.hw5.observer1.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw5.observer1.IntegerStorageObserver;
/**
 * This class defines an observer which squares the subject's value every time the state changes.
 * 
 * @author Filip Džidić
 *
 */
public class SquareValue implements IntegerStorageObserver {
	
	/**
	 * Prints out square of the subject's current value
	 */
	public void valueChanged(IntegerStorage istorage) {
		final int original = istorage.getValue();
		final int square = original*original;
		System.out.println("Provided new value: "+original+", square is "+square);
		
	}
	
}
