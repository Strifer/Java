package hr.fer.zemris.java.tecaj.hw1;
/** This class demonstrates the functionality of a recursive method in java.
 * 
 * @author Filip Džidić
 * @version 1.0
 */
public class HofstadterQ {
	
	public static void main(String[] args) {
		if (args.length!=1 || Integer.parseInt(args[0])<=0) {
			System.err.println("Invalid argument. Please provide a single positive integer");
		} else {
			long i = Integer.parseInt(args[0]);
			long x = hofQ(i);
			System.out.println("You've requested the calculation of the "+i+". number in the Hofstadter Q sequence. The value of this number is "+x);
		}
		
		
	}
	/** Recursively calculates the i-th element of the Hofstadter Q sequence.
	 * 
	 * @param i represents the order of the number in the sequence
	 * @return the value of that number
	 */
	private static long hofQ(long i) {
		if (i==1 || i==2) {
			return 1;
		}
		return hofQ(i-hofQ(i-1))+hofQ(i-hofQ(i-2));
	}
}
