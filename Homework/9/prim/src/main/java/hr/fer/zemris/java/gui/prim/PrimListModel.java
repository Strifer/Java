package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * This class represents a custom implementation of <code>ListModel</code>. It
 * can generate successive prime numbers and provides a list to keep track of
 * them.
 * 
 * @author Filip Džidić
 *
 */
public class PrimListModel implements ListModel<Integer> {
	/** this represents our main data list */
	private List<Integer> primes;
	/** this represents all of the observers that are watching this object */
	private List<ListDataListener> observers;

	public PrimListModel() {
		this.primes = new ArrayList<Integer>();
		this.observers = new ArrayList<ListDataListener>();
		primes.add(1);
	}

	/**
	 * Generates the sucessive prime number and informs all of the observers of
	 * the change.
	 */
	public void next() {
		primes.add(nextPrime(primes.get(primes.size() - 1)));
		for (ListDataListener l : observers) {
			ListDataEvent lde = new ListDataEvent(this,
					ListDataEvent.CONTENTS_CHANGED, primes.size() - 1,
					primes.size() - 1);
			l.intervalAdded(lde);
		}
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	/**
	 * Returns the next prime number after the user provided one.
	 * 
	 * @param previous
	 *            the previous prime number
	 * @return the succeeding prime number
	 */
	static int nextPrime(int previous) {
		if (previous < 2) {
			return 2;
		}
		if (previous == 2) {
			return 3;
		}
		int next = 0;
		int increment = 0;
		switch ((int) (previous % 6)) {
			case 0:
				next = previous + 1;
				increment = 4;
				break;
			case 1:
				next = previous + 4;
				increment = 2;
				break;
			case 2:
				next = previous + 3;
				increment = 2;
				break;
			case 3:
				next = previous + 2;
				increment = 2;
				break;
			case 4:
				next = previous + 1;
				increment = 2;
				break;
			case 5:
				next = previous + 2;
				increment = 4;
				break;
		}
		while (!isPrime(next)) {
			next += increment;
			increment = 6 - increment; // 2, 4 alternating
		}
		return next;
	}

	/**
	 * This method tests if a number is prime or not.
	 * 
	 * @param n
	 *            the prime number being tested
	 * @return true if it is prime
	 */
	private static boolean isPrime(int n) {
		// check if n is a multiple of 2
		if (n % 2 == 0)
			return false;
		// if not, then just check the odds
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		observers.add(l);

	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		observers.remove(l);
	}
}
