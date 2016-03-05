package hr.fer.zemris.java.complex;

/**
 * <code>ComplexRootedPolynomial</code> represents a polynomial with complex
 * parameters, defined by its complex roots in the form of
 * <p>
 * <code>P(z)=(z-c_0)(z-c_1)(z-c_2)...(z-c_n)</code><br>
 * Where C is a complex root and z is a complex variable
 * <p>
 * It comes equipped with methods for evaluating and converting to a standard
 * polynomial.
 * 
 * @author Filip Džidić
 * @version 1.0
 *
 */
public class ComplexRootedPolynomial {
	/** complex roots are stored here */
	private Complex[] roots;

	/**
	 * Constructs a new <code>ComplexRootedPolynomial</code> based on inputted
	 * roots.
	 * 
	 * @param roots
	 *            the complex roots which make up the polynomial
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = roots;
	}

	/**
	 * Evaluates the polyonomial value in Complex point z
	 * 
	 * @param z
	 *            the complex argument of our polynomial
	 * @return the value of the polynomial in that point
	 */
	public Complex apply(Complex z) {
		Complex ret = Complex.ONE;
		for (Complex root : roots) {
			ret = z.sub(root).mul(ret);
		}
		return ret;
	}

	/**
	 * Converts the <code>ComplexRootedPolynomial</code> to
	 * <code>ComplexPolynomial</code>.
	 * 
	 * @return the <code>ComplexPolynomial</code> representing the same
	 *         polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial ret = new ComplexPolynomial(
				roots[0].mul(Complex.ONE_NEG), Complex.ONE);
		for (int i = 1; i < roots.length; i++) {
			ret = ret.multiply(new ComplexPolynomial(roots[i]
					.mul(Complex.ONE_NEG), Complex.ONE));
		}
		return ret;
	}

	/**
	 * Finds the index of the closest complex root to a given complex point z.
	 * Treshold defines the maximum distance needed for the point to be
	 * considered "close".
	 * 
	 * @param z
	 *            complex point whose distance we're evaluating
	 * @param treshold
	 *            the maximum distance allowed
	 * @return the index of the closest root if found, -1 if not found
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int currentRoot = -1;
		for (int i = 0; i < roots.length; i++) {
			double distance = Math.hypot(z.getReal() - roots[i].getReal(),
					z.getImaginary() - roots[i].getImaginary());
			if (distance <= treshold) {
				currentRoot = i;
				treshold = distance;
			}
		}
		return currentRoot;
	}

	/**
	 * Returns a <code>String</code> representation of our rooted polynomial.
	 */
	public String toString() {
		StringBuilder buildy = new StringBuilder();
		for (int i = 0; i < roots.length; i++) {
			buildy.append("[z - (").append(roots[i].toString()).append(")]");
		}
		return buildy.toString();
	}
}
