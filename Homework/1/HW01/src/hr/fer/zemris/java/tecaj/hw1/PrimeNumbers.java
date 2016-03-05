package hr.fer.zemris.java.tecaj.hw1;
/** Calculates first n prime numbers
 * 
 * @author Filip Džidić
 * @version 1.0
 */
public class PrimeNumbers {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err
					.println("Invalid argument. Please provide a single positive integer.");
			System.exit(1);
		}
		int n = Integer.parseInt(args[0]);
		if (n <= 0) {
			System.err
					.println("Invalid argument. The integer must be positive");
			System.exit(1);
		}
		primePrint(n);
	}
	/** Calculates and prints out the first n primes. Treats 2 as a prime.
	 * 
	 * @param n how many primes
	 */
	private static void primePrint(int n) {
		int x = 1;
		int count = 1;
		boolean isPrime;
		System.out.println("You've requested the calculation of " + n
				+ " prime numbers and here they are:");
		System.out.println("1. 2");
		while (n > 0) {
			x += 2;
			isPrime = true;
			for (int i = 3; i <= Math.sqrt(x); i++) {
				if (x % i == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				System.out.println(count + ". " + x);
				n--;
				count++;
			}
		}

	}
}
