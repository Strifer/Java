package hr.fer.zemris.java.tecaj.hw5.observer2;

import hr.fer.zemris.java.tecaj.hw5.observer2.observers.ChangeCounter;
import hr.fer.zemris.java.tecaj.hw5.observer2.observers.DoubleValue;
import hr.fer.zemris.java.tecaj.hw5.observer2.observers.SquareValue;
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
		
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue());
		istorage.addObserver(new SquareValue());
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
