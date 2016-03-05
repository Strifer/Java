package hr.fer.zemris.java.complex;

import java.text.DecimalFormat;

/**
 * <code>Complex</code> represents an unmodifiable complex number, defined by
 * its real and imaginary values in the form of
 * <p>
 * <code>z=a+bi<br>a=Re{z}, b=Im{z}</code>
 * </p>
 * It comes equipped with several different ways to define a complex number,
 * either through its real and imaginary part or through its polar form by
 * providing an angle and magnitude.
 * <p>
 * This class also provides getter methods for retrieving a defined complex
 * number's real and imaginary parts as well as its angle and magnitude.
 * <p>
 * Subtraction, addition, multiplication, division and powers methods are also
 * supported.
 * 
 * @author Filip Džidić
 * @version 1.0
 *
 */
public final class Complex {
	/** 0+0i */
	public static final Complex ZERO = new Complex(0, 0);
	/** 1+0i */
	public static final Complex ONE = new Complex(1, 0);
	/** -1+0i */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/** 0+i */
	public static final Complex IM = new Complex(0, 1);
	/** 0-i */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * <code>real</code> represents the real part of the complex number, its
	 * coordinate on the x axis in the Gaussian plane
	 */
	private final double real;
	/**
	 * <code>imaginary</code> represents the imaginary part of the complex
	 * number, its coordinate on the y axis in the Gaussian plane
	 */
	private final double imaginary;

	/**
	 * Constructs a new <code>Complex</code> with user defined real and
	 * imaginary parts
	 * 
	 * @param real
	 *            the real part of the complex number
	 * @param imaginary
	 *            the imaginary part of the complex number
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * This static factory method is used for transforming real numbers into
	 * their <code>Complex</code> representation. Imaginary is set to 0.
	 * 
	 * @param real
	 *            the real part of the complex number
	 * @return reference to newly created <code>Complex</code>
	 */
	public static Complex fromReal(double real) {
		return new Complex(real, 0);
	}

	/**
	 * This static factory method is used for transforming imaginary numbers
	 * into their <code>Complex</code> representation. Real is set to 0.
	 * 
	 * @param imaginary
	 *            the imaginary part of the complex number
	 * @return reference to newly created <code>Complex</code>
	 */
	public static Complex fromImaginary(double imaginary) {
		return new Complex(0, imaginary);
	}

	/**
	 * This static factory method is used for creating a <code>Complex</code> as
	 * defined by its magnitude and angle by the following definition:
	 * <p>
	 * <code>z=magnitude*(cos(angle)+i*sin(angle))</code>
	 * <p>
	 * 
	 * @param magnitude
	 *            magnitude represents the module of a <code>Complex</code> or
	 *            the distance from 0 on the Gaussian plane
	 * @param angle
	 *            angle is define represents the polar angle of a
	 *            <code>Complex</code>
	 * @return reference to the newly created complex number
	 */
	public static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		return new Complex(magnitude * Math.cos(angle), magnitude
				* Math.sin(angle));
	}

	/**
	 * Helper method for parsing.
	 * 
	 * @param s
	 *            <code>String</code> representation of a <code>Complex</code>
	 * @return parsed <code>String</code> representation of either real or
	 *         imaginary part of the number
	 * @throws NumberFormatException
	 *             if <code>String</code> represents an invalid
	 *             <code>double</code> number
	 */
	private static String parseloop(String s) {

		// initialize helper variables
		int start = 0;
		int countPoint = 0;
		int countim = 0;
		// check the beginning
		char c = s.charAt(start);

		// skip the sign if it's there
		if (c == '-' || c == '+') {
			c = s.charAt(++start);
		}

		// keep reading digits, points and i's
		while (Character.isDigit(c) || c == '.' || c == 'i') {
			// count your points
			if (c == '.') {
				countPoint++;
			}
			// count your i's
			if (c == 'i') {
				countim++;
				start++;
			}
			// there can only be 1 decimal point and 1 i
			if (countPoint > 1 || countim > 1) {
				throw new NumberFormatException(
						"Too many decimal points or too many i-units " + s);
			}
			// we're done here so no need to check the next character
			if (start == s.length()) {
				start++;
				break;
			}
			c = s.charAt(start);
			start++;
		}
		// fill a new string with what we've read
		return s.substring(0, start - 1);
	}

	/**
	 * Helper method for parsing. Counts the number of times a user defined
	 * character appears within a <code>String</code>
	 * 
	 * @param s
	 *            the <code>String</code> we are parsing
	 * @param c
	 *            the <code>char</code> we are counting
	 * @return the number of times c appears in s
	 * @throws IllegalArgumentException
	 *             if s is null
	 */
	private static int countChar(String s, char c) {
		int count = 0;
		if (s == null) {
			throw new IllegalArgumentException();
		}
		for (int i = 0, len = s.length(); i < len; i++) {
			if (s.charAt(i) == c) {
				count++;
			}
		}
		return count;
	}

	/**
	 * This static method constructs a new <code>Complex</code> based on its
	 * <code>String</code> representation. All complex numbers must follow a
	 * strict format for this method to be able to parse well.<br>
	 * Format:
	 * <p>
	 * <code>"[sign][double real][sign]i[double imaginary]"</code>
	 * </p>
	 * Special cases:
	 * <p>
	 * <code>"[sign][double real]"<br>"[sign]i[double imaginary]"<br>"[sign][i]"</code>
	 * <p>
	 * Example of usage:<br>
	 * <code>Complex c = Complex.parse("3.123-i2.3");</code>
	 * 
	 * @param s
	 *            the <code>String</code> representation of a complex number
	 * @return newly constructed <code>Complex</code> based on user input
	 * @throws IllegalArgumentException
	 *             if s is null reference
	 * @throws NumberFormatException
	 *             if string does not follow the format
	 */
	public static Complex parse(String s) {
		if (s == null) {
			throw new IllegalArgumentException("can't parse null");
		}
		s = s.replaceAll("\\s+", "");
		// special cases which are allowed to break format
		if (s.equals("i")) {
			return new Complex(0, 1);
		} else if (s.equals("-i")) {
			return new Complex(0, -1);
		}

		if (s.endsWith("i") && countChar(s, 'i') == 1) {
			String first = parseloop(s);
			return new Complex(Double.parseDouble(first), Double.parseDouble(s
					.charAt(s.length() - 2) + "1"));
		}

		// get our first part
		String first = parseloop(s);
		// remember the future index
		int start = first.length() + 1;
		// count the i's
		int countim = countChar(first, 'i');

		// if we've read the whole string it means we've read an imaginary or
		// real number
		if (s.length() == first.length()) {
			// if there's an i it's imaginary
			if (countim == 1) {
				if (first.indexOf('i') == 0 || first.indexOf('-') == 0) { // make
																			// sure
					// the i is
					// in the
					// right
					// place
					first = first.replaceAll("i", ""); // and then
														// get rid
														// of the i
					return new Complex(0, Double.parseDouble(first));
				} else {
					throw new NumberFormatException(
							"i can only come at the beginning " + s);
				}
			} else { // no i so it's real
				return new Complex(Double.parseDouble(first), 0);
			}

			// we have more stuff to read but if we've read an i then that's an
			// invalid input
		} else if (countim != 0) {
			throw new NumberFormatException("Invalid real " + s);
		}

		// at this point we MUST have a sign and a digit following it
		if ((s.charAt(start - 1) != '+' && s.charAt(start - 1) != '-')
				|| !Character.isDigit(s.charAt(start + 1))) {
			throw new NumberFormatException(
					"Invalid format, please check the documentation" + s);
		}

		String x = s.substring(first.length()); // skip the first part

		String second = parseloop(x); // read the second part

		countim = countChar(second, 'i'); // count the i-s

		if (countim != 1) { // we have to have one i in our imaginary part
			throw new NumberFormatException(
					"No i-units found in imaginary part " + s);
		}

		if ((first.length() + second.length()) != s.length() // we can't have
																// more stuff to
																// read and the
																// i has to be
																// after the
																// sign
				|| second.indexOf('i') != 1) {
			throw new NumberFormatException("Invalid expression " + s);
		}

		// get rid of the i and make a new Complex2 number
		second = second.replaceFirst("i", "");
		return new Complex(Double.parseDouble(first),
				Double.parseDouble(second));
	}

	/**
	 * Getter method for the real part of a <code>Complex</code>
	 * 
	 * @return Re{z}
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Getter method for the imaginary part of a <code>Complex</code>
	 * 
	 * @return Im{z}
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Calculates the angle of defined <code>Complex</code> based on its real
	 * and imaginary parts
	 * 
	 * @return arg(z)
	 * @see java.lang.Math#atan2(double, double)
	 */
	public double getAngle() {
		return Math.atan2(imaginary, real);
	}

	/**
	 * Calculates the magnitude of defined <code>Complex</code> based on its
	 * real and imaginary parts
	 * 
	 * @return |z|
	 * @see java.lang.Math#hypot(double, double)
	 */
	public double getMagnitude() {
		return Math.hypot(real, imaginary);
	}

	/**
	 * Creates a new <code>Complex</code> defined as <code>ans=z1+z2</code>
	 * where z1 is the instance we are calling the method on and z2 is the user
	 * provided <code>Complex</code>
	 * 
	 * @param c
	 *            the number we are adding
	 * @return reference to newly constructed <code>Complex</code>
	 */
	public Complex add(Complex c) {
		return new Complex(real + c.getReal(), imaginary + c.getImaginary());
	}

	/**
	 * Creates a new negated <code>Complex</code> defined as <code>ans=-z</code>
	 * where z is the instance upon which we call this method.
	 * 
	 * @return reference to newly constructed <code>Complex</code>
	 */
	public Complex neg() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * Creates a new <code>Complex</code> defined as <code>ans=z1-z2</code>
	 * where z1 is the instance we are calling the method on and z2 is the user
	 * provided <code>Complex</code>
	 * 
	 * @param c
	 *            the number we are subtracting
	 * @return reference to newly constructed <code>Complex</code>
	 */
	public Complex sub(Complex c) {
		return new Complex(real - c.getReal(), imaginary - c.getImaginary());
	}

	/**
	 * Creates a new <code>Complex</code> defined as <code>ans=z1*z2</code>
	 * where z1 is the instance we are calling the method on and z2 is the user
	 * provided <code>Complex</code>
	 * 
	 * @param c
	 *            the number we are multiplying with
	 * @return reference to newly constructed <code>Complex</code>
	 */
	public Complex mul(Complex c) {
		double re = real * c.real - imaginary * c.imaginary;
		double im = real * c.imaginary + imaginary * c.real;
		return new Complex(re, im);
	}

	/**
	 * Creates a new <code>Complex</code> defined as <code>ans=z1/z2</code>
	 * where z1 is the instance we are calling the method on and z2 is the user
	 * provided <code>Complex</code>
	 * 
	 * @param c
	 *            the number we are dividing with
	 * @return reference to newly constructed <code>Complex</code>
	 */
	public Complex div(Complex c) {
		double denom = (Math.pow(c.real, 2) + Math.pow(c.imaginary, 2));
		double re = (real * c.real + imaginary * c.imaginary) / denom;
		double im = (imaginary * c.real - real * c.imaginary) / denom;
		return new Complex(re, im);
	}

	public Complex conjugate() {
		return new Complex(real, -imaginary);
	}

	/**
	 * Creates a new <code>Complex</code> defined as <code>ans=z^n</code> where
	 * z1 is the instance we are calling the method on and n is the user
	 * provided power.
	 * 
	 * @param n
	 *            the power we are raising to, must be a natural number
	 * @return reference to newly constructed <code>Complex</code>
	 * @throws IllegalArgumentException
	 *             if n is negative
	 */
	public Complex pow(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		} else {
			Complex c = Complex.ONE;
			for (int i = 0; i < n; i++) {
				c = c.mul(this);
			}
			return c;
		}
	}

	/**
	 * Returns a <code>String</code> representation of a complex number in the
	 * form:
	 * <p>
	 * <code>"Re{z} + Im{z}i"</code>
	 * <p>
	 * Numbers are rounded to 4 decimals.
	 */
	public String toString() {
		DecimalFormat imagf = new DecimalFormat("+ #.####;- #.####");
		DecimalFormat realf = new DecimalFormat("#.####");
		return realf.format(real) + " " + imagf.format(imaginary) + "i";
	}

}
