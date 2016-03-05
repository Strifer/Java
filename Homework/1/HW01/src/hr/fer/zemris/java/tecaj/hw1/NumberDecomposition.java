package hr.fer.zemris.java.tecaj.hw1;

/**
 * Decomposes a user inputted number into its prime factors
 * 
 * @author Filip Džidić
 * @version 1.0
 */
public class NumberDecomposition {
	/**
	 * The main method demonstrates the program's functionality.
	 * 
	 * @param args
	 *            a single natural number greater than 1
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err
					.println("Invalid argument. Please provide one natural number greater than 1");
			System.exit(1);
		} else {
			long n = Integer.parseInt(args[0]);
			if (n <= 1) {
				System.err
						.println("Invalid argument. Please provide one natural number greater than 1");
				System.exit(1);
			}
			System.out.println("You've requested the decomposition of " + n
					+ " onto prime factors. Here they are:");
			int count = 1;
			for (long i = 2; i <= n; i++) {
				while (n % i == 0) {
					System.out.println(count + ". " + i);
					n = n / i;
					count++;
				}
			}
		}
	}
}
