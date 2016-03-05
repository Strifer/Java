package hr.fer.zemris.java.tecaj.hw5.observer1;

import hr.fer.zemris.java.tecaj.hw5.observer1.observers.ChangeCounter;
import hr.fer.zemris.java.tecaj.hw5.observer1.observers.DoubleValue;
import hr.fer.zemris.java.tecaj.hw5.observer1.observers.SquareValue;
import hr.fer.zemris.java.tecaj.hw5.observer1.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw5.observer1.IntegerStorageObserver;

/**
 * This is a demo class used to show the functionality of our subject and its
 * observers
 * 
 * @author Filip Džidić
 *
 */
public class ObserveExample {
	/**
	 * The main method which demonstrates the functionality of our program.
	 * 
	 * @param args
	 *            no CLI args should be sent
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue());
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
