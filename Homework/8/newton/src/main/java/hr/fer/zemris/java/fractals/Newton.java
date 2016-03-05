package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.complex.Complex;
import hr.fer.zemris.java.complex.ComplexRootedPolynomial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This main program demonstrates the functionality of our
 * <code>FractalProducer</code> class.
 * 
 * @author Filip Džidić
 * @version 1.0
 *
 */
public class Newton {
	/**
	 * Main method which asks the user for input and shows a graphical image of
	 * the fractal calculated by the Newton-Raphson method.
	 * 
	 * @param args
	 *            no arguments should be provided
	 * @throws IOException
	 *             if something goes terribly wrong with handling input
	 */
	public static void main(String[] args) throws IOException {
		System.out
				.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out
				.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String line = "";

		ArrayList<Complex> roots = new ArrayList<>();
		do {
			System.out.print("Root " + (roots.size() + 1) + "> ");
			line = reader.readLine().trim();
			try {
				Complex c = Complex.parse(line);
				roots.add(c);
			} catch (RuntimeException e) {
			}
		} while (!line.equals("done"));

		Complex[] cplx = new Complex[roots.size()];
		for (int i = 0; i < roots.size(); i++) {
			cplx[i] = roots.get(i);
		}

		FractalProducer t = new FractalProducer(new ComplexRootedPolynomial(
				cplx));
		t.showParallel();
	}
}
