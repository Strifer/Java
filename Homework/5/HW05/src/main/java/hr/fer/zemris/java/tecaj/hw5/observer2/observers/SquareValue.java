package hr.fer.zemris.java.tecaj.hw5.observer2.observers;

import hr.fer.zemris.java.tecaj.hw5.observer2.IntegerStorageChange;
import hr.fer.zemris.java.tecaj.hw5.observer2.IntegerStorageObserver;
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
	public void valueChanged(IntegerStorageChange ichange){
		System.out.println("Provided new value: "+ichange.getChangedValue()+", square is "+ichange.getChangedValue()*ichange.getChangedValue());
		
	}
	
}
