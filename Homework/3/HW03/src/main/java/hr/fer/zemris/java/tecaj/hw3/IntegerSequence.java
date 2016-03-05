package hr.fer.zemris.java.tecaj.hw3;

import java.util.Iterator;
/**
 * The class <code>IntegerSequence</code> represents a set of natural numbers defined as
 * <code>[begin, end, step]</code> where begin and end are bounds and step is the distance between each two subsequent elements within the set.
 * The step can be positive or negative but keep in mind that you are always counting from begin so if the step is negative the end must be a lower number and vice versa.
 * @author Filip Džidić
 * @version 1.0
 */
public class IntegerSequence implements Iterable<Integer> {
	/** defines where we start counting from */
	private int begin;
	/** defines what we count towards */
	private int end;
	/** defines by how much we are counting, if negative end must be lower than begin */
	private int step;
	
//	public static void main(String[] args) {
//		IntegerSequence range = new IntegerSequence(3, 12, 4);
//		for (int i : range) {
//			for (int j : range) {
//				System.out.format("[%d][%d]\n", i, j);
//			}
//		}
//	}
	/**
	 * The general constructors takes three parameters.
	 * 
	 * @param begin we start counting from here
	 * @param end we count towards this
	 * @param step how much we increment
	 * @throws IllegalArgumentException if step iz zero
	 * @throws IllegalArgumentException if step is negative and end is greater than begin
	 * @throws IllegalArgumentException if step is positive and end is smaller than begin
	 */
	public IntegerSequence(int begin, int end, int step) {
		if(step>=0 && (end<begin)) {
			throw new IllegalArgumentException("Cannot reach the end condition with given parameters");
		}
		if(step<=0 && (begin<end)) {
			throw new IllegalArgumentException("Cannot reach the end condition with given parameters");
		}
		this.begin=begin;
		this.end=end;
		this.step=step;
	}
	/**
	 * We begin counting from 0 by 1 until we reach end
	 * @param end what we are counting towards
	 */
	public IntegerSequence(int end) {
		this(0,end,1);
	}
	/**
	 * We count from begin to end by 1.
	 * @param begin where we start counting from 
	 * @param end what we are counting towards
	 */
	public IntegerSequence(int begin, int end) {
		this(begin, end, 1);
	}
	
	/**
	 * Factory method makes a new iterator to enable for:each loops
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new SequenceIterator();
	}
	
	/**
	 * <code>SequenceIterator</code> is a private class used so that we can traverse through our sequence ina  for : each loop.
	 * @author Filip Džidić
	 *
	 */
	private class SequenceIterator implements Iterator<Integer> {
		/** current counted number */
		private int current;
		/** our limit we stop counting after this */
		private int limit;
		
		/** We count towards the end starting from begin */
		public SequenceIterator() {
			this.limit=end;
			this.current=begin;
		}
		/**
		 * Returns true as long as we have more numbers to count.
		 */
		@Override
		public boolean hasNext() {
			if(step>0) {
				return current<=limit;
			}
			else { 
				return current>=limit;
			}
		}
		
		/**
		 * Sets current to the next number in our sequence
		 */
		@Override
		public Integer next() {
			int value = current;
			current+=step;
			return value;
		}
		
	}
	
}
