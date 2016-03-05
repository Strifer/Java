package hr.fer.zemris.java.tecaj.hw3;

import java.text.DecimalFormat;

/**
 * <code>ComplexNumber</code> represents an unmodifiable complex number, defined
 * by its real and imaginary values in the form of
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
 * Subtraction, addition, multiplication, powers and roots methods are also
 * supported.
 * 
 * @author Filip Džidić
 * @version 1.0
 *
 */
public final class ComplexNumber {
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
	 * Constructs a new <code>ComplexNumber</code> with user defined real and
	 * imaginary parts
	 * 
	 * @param real
	 *            the real part of the complex number
	 * @param imaginary
	 *            the imaginary part of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	/**
	 * This static factory method is used for transforming real numbers into
	 * their <code>ComplexNumber</code> representation. Imaginary is set to 0.
	 * 
	 * @param real
	 *            the real part of the complex number
	 * @return reference to newly created <code>ComplexNumber</code>
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * This static factory method is used for transforming imaginary numbers
	 * into their <code>ComplexNumber</code> representation. Real is set to 0.
	 * 
	 * @param imaginary
	 *            the imaginary part of the complex number
	 * @return reference to newly created <code>ComplexNumber</code>
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * This static factory method is used for creating a
	 * <code>ComplexNumber</code> as defined by its magnitude and angle by the
	 * following definition:
	 * <p>
	 * <code>z=magnitude*(cos(angle)+i*sin(angle))</code>
	 * <p>
	 * 
	 * @param magnitude
	 *            magnitude represents the module of a
	 *            <code>ComplexNumber</code> or the distance from 0 on the
	 *            Gaussian plane
	 * @param angle
	 *            angle is define represents the polar angle of a
	 *            <code>ComplexNumber</code>
	 * @return
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude,
			double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude
				* Math.sin(angle));
	}

	/**
	 * Helper method for parsing.
	 * 
	 * @param s
	 *            <code>String</code> representation of a
	 *            <code>ComplexNumber</code>
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
	public static int countChar(String s, char c) {
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
	 * This static method constructs a new <code>ComplexNumber</code> based on
	 * its <code>String</code> representation. All complex numbers must follow a
	 * strict format for this method to be able to parse well.<br>
	 * Format:
	 * <p>
	 * <code>"[sign][double real][sign][double imaginary]i"</code>
	 * </p>
	 * Special cases:
	 * <p>
	 * <code>"[sign][double real]"<br>"[sign][double imaginary]"<br>"[sign][i]"</code>
	 * <p>
	 * Example of usage:<br>
	 * <code>ComplexNumber c = ComplexNumber.parse("3.123-2.3i");</code>
	 * 
	 * @param s
	 *            the <code>String</code> representation of a complex number
	 * @return newly constructed <code>ComplexNumber</code> based on user input
	 * @throws IllegalArgumentException
	 *             if s is null reference
	 * @throws NumberFormatException
	 *             if string does not follow the format
	 */
	public static ComplexNumber parse(String s) {
		if (s == null) {
			throw new IllegalArgumentException("can't parse null");
		}

		// special cases which are allowed to break format
		if (s.equals("i")) {
			return new ComplexNumber(0, 1);
		} else if (s.equals("-i")) {
			return new ComplexNumber(0, -1);
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
				if (first.lastIndexOf('i') == first.length() - 1) { // make sure
																	// the i is
																	// in the
																	// right
																	// place
					first = first.substring(0, first.length() - 1); // and then
																	// get rid
																	// of the i
					return new ComplexNumber(0, Double.parseDouble(first));
				} else {
					throw new NumberFormatException(
							"i can only come at the end " + s);
				}
			} else { // no i so it's real
				return new ComplexNumber(Double.parseDouble(first), 0);
			}

			// we have more stuff to read but if we've read an i then that's an
			// invalid input
		} else if (countim != 0) {
			throw new NumberFormatException("Invalid real " + s);
		}

		// at this point we MUST have a sign and a digit following it
		if ((s.charAt(start - 1) != '+' && s.charAt(start - 1) != '-')
				|| !Character.isDigit(s.charAt(start))) {
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
																// at the end
				|| second.lastIndexOf('i') != second.length() - 1) {
			throw new NumberFormatException("Invalid expression " + s);
		}

		// get rid of the i and make a new complex number
		second = second.substring(0, second.length() - 1);
		return new ComplexNumber(Double.parseDouble(first),
				Double.parseDouble(second));
	}

	/**
	 * Getter method for the real part of a <code>ComplexNumber</code>
	 * 
	 * @return Re{z}
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Getter method for the imaginary part of a <code>ComplexNumber</code>
	 * 
	 * @return Im{z}
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Calculates the angle of defined <code>ComplexNumber</code> based on its
	 * real and imaginary parts
	 * 
	 * @return arg(z)
	 * @see java.lang.Math#atan2(double, double)
	 */
	public double getAngle() {
		return Math.atan2(imaginary, real);
	}

	/**
	 * Calculates the magnitude of defined <code>ComplexNumber</code> based on
	 * its real and imaginary parts
	 * 
	 * @return |z|
	 * @see java.lang.Math#hypot(double, double)
	 */
	public double getMagnitude() {
		return Math.hypot(real, imaginary);
	}

	/**
	 * Creates a new <code>ComplexNumber</code> defined as
	 * <code>ans=z1+z2</code> where z1 is the instance we are calling the method
	 * on and z2 is the user provided <code>ComplexNumber</code>
	 * 
	 * @param c
	 *            the number we are adding
	 * @return reference to newly constructed <code>ComplexNumber</code>
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.getReal(), imaginary
				+ c.getImaginary());
	}
	/**
	 * Creates a new <code>ComplexNumber</code> defined as
	 * <code>ans=z1-z2</code> where z1 is the instance we are calling the method
	 * on and z2 is the user provided <code>ComplexNumber</code>
	 * 
	 * @param c
	 *            the number we are subtracting
	 * @return reference to newly constructed <code>ComplexNumber</code>
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.getReal(), imaginary
				- c.getImaginary());
	}
	/**
	 * Creates a new <code>ComplexNumber</code> defined as
	 * <code>ans=z1*z2</code> where z1 is the instance we are calling the method
	 * on and z2 is the user provided <code>ComplexNumber</code>
	 * 
	 * @param c
	 *            the number we are multiplying with
	 * @return reference to newly constructed <code>ComplexNumber</code>
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double angle = this.getAngle() + c.getAngle();
		double magnitude = this.getMagnitude() * c.getMagnitude();
		return ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);
	}
	/**
	 * Creates a new <code>ComplexNumber</code> defined as
	 * <code>ans=z1/z2</code> where z1 is the instance we are calling the method
	 * on and z2 is the user provided <code>ComplexNumber</code>
	 * 
	 * @param c
	 *            the number we are dividing with
	 * @return reference to newly constructed <code>ComplexNumber</code>
	 * @throws IllegalArgumentException if |c|=0
	 */
	public ComplexNumber div(ComplexNumber c) {
		if (c.getMagnitude()==0) {
			throw new IllegalArgumentException("cannot divide by 0");
		}
		double angle = this.getAngle() - c.getAngle();
		double magnitude = this.getMagnitude() / c.getMagnitude();
		return ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);
	}
	/**
	 * Creates a new <code>ComplexNumber</code> defined as
	 * <code>ans=z^n</code> where z1 is the instance we are calling the method
	 * on and n is the user provided power.
	 * 
	 * @param n the power we are raising to, must be a natural number
	 * @return reference to newly constructed <code>ComplexNumber</code>
	 * @throws IllegalArgumentException if n<0
	 */
	public ComplexNumber pow(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		} else {
			double magnitude = Math.pow(this.getMagnitude(), n);
			double angle = n * this.getAngle();
			return ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);
		}
	}
	/**
	 * Creates a new array of <code>ComplexNumber</code> roots defined as
	 * <code>ans=z^(1/n)</code>.Z is the instance we are calling the method
	 * on and n is the user provided root we are calculating.
	 * 
	 * @param n the root we are calculating must be greater than 0
	 * @return array of all calculated roots
	 * @throws IllegalArgumentException if n<=0
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("root must be positive");
		} else {
			ComplexNumber[] ret = new ComplexNumber[n];
			double magnitude = Math.pow(this.getMagnitude(), 1.0 / n);
			double angle = this.getAngle();
			for (int i = 0; i < n; i++) {
				ret[i] = ComplexNumber.fromMagnitudeAndAngle(magnitude,
						(angle + 2 * Math.PI * i) / n);
			}
			return ret;
		}
	}
	/**
	 * Returns a <code>String</code> representation of a complex number in the form:
	 * <p><code>"Re{z} + Im{z}i"</code><p>
	 * Numbers are rounded to 4 decimals.
	 */
	public String toString() {
		DecimalFormat imagf = new DecimalFormat("+ #.####;- #.####");
		DecimalFormat realf = new DecimalFormat("#.####");
		return realf.format(real) + " " + imagf.format(imaginary) + "i";
	}
}
