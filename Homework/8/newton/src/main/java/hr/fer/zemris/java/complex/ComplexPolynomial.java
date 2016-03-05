package hr.fer.zemris.java.complex;

/**
 * <code>ComplexPolynomial</code> represents a polynomial with complex
 * parameters, defined by its complex factors in the form of
 * <p>
 * <code>P(z)=C_n*z^n + c_n-1*z^(n-1) + ... + C0</code><br>
 * Where C is a complex factor and z is a complex variable.
 * <p>
 * It comes equipped with methods for deriving, multiplying and evaluating
 * polynomials.
 * 
 * @author Filip Džidić
 * @version 1.0
 *
 */
public class ComplexPolynomial {
	/** complex factors are stored here */
	Complex[] factors;

	/**
	 * This constructor takes the complex factors which make up our polynomial.<br>
	 * FORMAT: Factors must be entered starting from the exponent 0<br>
	 * Example:
	 * <p>
	 * <code>ComplexPolynomial cp = new ComplexPolynomial(Complex.ONE, new Complex(2,0), new Complex(3,0));<br>
	 * <br>
	 * cp.toString = "(3+0i)*z^2 + (2+0i)*z + (1+0i)"</code>
	 * 
	 * @param factors
	 *            the factors which make up our polynomial
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * Returns the order of our polynomial (the highest exponent)
	 * 
	 * @return the order of the polynomial
	 */
	public short order() {
		short ret = 0;
		for (ret = 0; ret < factors.length - 1; ret++) {
		}
		return ret;
	}

	/**
	 * Multiplies two polynomials.
	 * 
	 * @param p
	 *            the polynomial factor we're multiplying with
	 * @return the resulting product of the two polynomials
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		short order1 = this.order();
		short order2 = p.order();
		int totalOrder = order1 + order2;
		Complex[] result = new Complex[totalOrder + 1];
		for (int i = 0; i < result.length; i++) {
			result[i] = Complex.ZERO;
		}
		for (int i = 0; i < factors.length; i++)
			for (int j = 0; j < p.factors.length; j++) {
				result[i + j] = result[i + j].add(factors[i].mul(p.factors[j]));
			}
		return new ComplexPolynomial(result);
	}

	/**
	 * Derives the polynomial.
	 * 
	 * @return the derivation
	 */
	public ComplexPolynomial derivative() {
		if (this.order() == 0) {
			return new ComplexPolynomial(Complex.ZERO);
		}
		Complex[] result = new Complex[this.factors.length - 1];
		for (int i = factors.length - 1; i >= 1; i--) {
			result[i - 1] = factors[i].mul(Complex.fromReal(i));
		}
		return new ComplexPolynomial(result);
	}

	/**
	 * Evaluates the polynomial value in Complex point z
	 * 
	 * @param z
	 *            the complex argument of our polynomial
	 * @return the value of the polynomial in that point
	 */
	public Complex apply(Complex z) {
		Complex ret = Complex.ZERO;
		for (int i = 0; i < factors.length; i++) {
			ret = z.pow(i).mul(factors[i]).add(ret);
		}
		return ret;
	}

	/**
	 * Returns a <code>String</code> representation of our polynomial.
	 */
	public String toString() {
		StringBuilder buildy = new StringBuilder();
		for (int i = factors.length - 1; i >= 2; i--) {
			if (factors[i].getMagnitude() <= 1E-9) {
				continue;
			}
			buildy.append("(").append(factors[i].toString()).append(")")
					.append("z^" + i).append(" + ");
		}
		if (factors.length >= 2 && (factors[1].getMagnitude() > 1E-9)) {
			buildy.append("(").append(factors[1].toString()).append(")")
					.append("z").append(" + ");
		}
		if (factors.length >= 1 && (factors[0].getMagnitude() > 1E-9)) {
			buildy.append("(").append(factors[0].toString()).append(")");
		}
		return buildy.toString();
	}
}
