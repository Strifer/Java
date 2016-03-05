package hr.fer.zemris.java.tecaj.hw1;
/** This class represents a complex number root calculator.
 * 
 * @author Filip Džidić
 * @version 1.0
 */
public class Roots {
	/** The main method demonstrates the program's functionality.
	 * 
	 * @param args three arguments passed through the commandline
	 */
	public static void main(String[] args) {
		if (args.length!=3) {
			System.err.println("Please provide 3 arguments");
			System.exit(1);
		} else {
			double real = Double.parseDouble(args[0]);
			double imaginary = Double.parseDouble(args[1]);
			int root = Integer.parseInt(args[2]);
			if (root<1) {
				System.err.println("The root must be a natural number greater than 1");
				System.exit(1);
			}
			
			complexRoot(real, imaginary, root);
		}
	}
/** Calculates and prints out all the roots of a given complex number.
 * 
 * @param real the real part of the complex number
 * @param imaginary the imaginary part of the complex number
 * @param root which root to calculate
 */
	private static void complexRoot(double real, double imaginary, int root) {
		double module = Math.sqrt(real*real+imaginary*imaginary);
		module=Math.pow(module, 1.0/root);
		double argument = Math.atan2(imaginary, real);
		double x, y;
		for (int i=0; i<root; i++) {
			x=module*Math.cos((2*i*Math.PI+argument)/(root));
			y=module*Math.sin((2*i*Math.PI+argument)/(root));
			System.out.printf("%d)%5.2f%+4.2fi%n",(i+1), x, y);
		}
		
	}
}
