package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexCollection;


/** 
 * This class serves as a simple demonstration.
 * @author Filip Džidić
 *
 */
public class ArrayDemo {
	/** 
	 * The main method demonstrates the basic functionality of <code>ArrayBackedIndexCollection</code>
	 * 
	 * @param args no CLI arguments should be provided
	 */
	public static void main(String[] args) {
		ArrayBackedIndexCollection col = new ArrayBackedIndexCollection(2);
		System.out.println("Initial size is "+col.size());
		
		//adds three elements and prints all out
		col.add("This");
		col.add("is");
		col.add("sentence");
		System.out.println("Size after adding three elements is "+col.size());
		print(col);
		
		//inserts two elements and prints all out
		col.insert(2, "a");
		col.insert(col.size(), "!");
		print(col);
		System.out.println("Size after inserting two elements is "+col.size());
		
		
		System.out.println("Collection contains 'is': "+col.contains("is"));
		System.out.println("Collection contains '.': "+col.contains("."));
		
		
		//clears list and prints all out
		col.clear();
		print(col);
	}
	/**
	 * This method prints out every element inside <code>ArrayBackedIndexCollection</code>
	 * Prints empty if no elements are found.
	 * @param arr the collection whose elements we're printing
	 */
	public static void print(ArrayBackedIndexCollection arr) {
		if (arr.size()==0) {
			System.out.println("empty");
			return;
		}
		for (int i=0, size=arr.size(); i<size; i++) {
			System.out.print(arr.get(i)+" ");
		}
		System.out.println();
		System.out.println();
	}
}
