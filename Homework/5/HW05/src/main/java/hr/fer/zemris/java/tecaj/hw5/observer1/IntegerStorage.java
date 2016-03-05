package hr.fer.zemris.java.tecaj.hw5.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>IntegerStorage</code> represents a Subject in the Observer pattern
 * which stores a single integer as its state. <br>
 * It provides methods for adding and removing observers as well as updating
 * them when the state is changed.
 * 
 * @author Filip Džidić
 *
 */
public class IntegerStorage {
	/** the state of our subject */
	private int value;
	/** this collection is used to hold registered observers */
	private List<IntegerStorageObserver> observers;

	/**
	 * Constructs an <code>IntegerStorage</code> with an initial state.
	 * 
	 * @param initialValue
	 *            the initial state of our subject
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * This method is used for registering new observers to our subject.
	 * 
	 * @param observer
	 *            the observer being registered
	 * @throws IllegalArgumentException if user tries adding null as an observer
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observers == null) {
			observers = new ArrayList<IntegerStorageObserver>();
		}
		if (observer == null) {
			throw new IllegalArgumentException("null values not allowed");
		}
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * This method unregisters an observer from the observer list.
	 * 
	 * @param observer
	 *            the observer being unregistered
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observers.lastIndexOf(observer));
	}

	/**
	 * This method unregisters all observers in the observer list.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Getter method for the object's current state.
	 * 
	 * @return value of current state
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Updates the state with a new user provided value. Notifies all of its
	 * observers of this change.
	 * 
	 * @param value
	 *            the new state
	 */
	public void setValue(int value) {
		if (this.value != value) {
			this.value = value;
		}
		if (observers != null) {
			for (int i = 0; i < observers.size(); i++) {
				observers.get(i).valueChanged(this);
			}
		}
	}

}
