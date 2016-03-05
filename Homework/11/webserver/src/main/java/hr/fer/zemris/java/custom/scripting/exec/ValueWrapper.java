package hr.fer.zemris.java.custom.scripting.exec;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * This class represents a wrapper for three main objects: String, Integer and
 * Double. It comes equipped with four methods used for calculation and a
 * comparison method to compare values. When performing arithmetic operations if
 * any of the two parameters involved is of type double the result will be
 * stored as double, otherwise if both are integers the resulting value will be
 * stored as an integer.
 * 
 * @author Filip Džidić
 */
public class ValueWrapper {
	/** the value being stored wrapped */
	private Object value;
	/**
	 * this boolean keeps track if we're using values of type double inside our
	 * arithmetic operations
	 */
	private boolean operatingWithDoubles;

	/**
	 * Sets the initial value to what the user provides. This constructor can
	 * only accept values of type <code>Integer</code>, <code>Double</code>,
	 * <code>String</code>
	 * 
	 * @param value
	 *            the initial parameter
	 * @throws RuntimeException
	 *             if value is of illegal type
	 */
	public ValueWrapper(Object value) {
		setValue(value);
	}

	/**
	 * Getter method for value.
	 * 
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter method for value. Accepted types are <code>Integer</code>,
	 * <code>Double</code>, <code>String</code>. Null is treated as an integer
	 * of value 0. Strings are parsed immediately and converted to their
	 * appropriate format.
	 * 
	 * @param value
	 *            the value being set
	 */
	public void setValue(Object value) {
		if (checkLegalEntry(value)) {
			if (value == null) {
				this.value = Integer.valueOf(0);
			} else if (value instanceof String) {
				Number val = convertFromString((String) value);
				if (val.doubleValue() % 1 == 0) {
					this.value = Integer.valueOf(val.intValue());
				} else {
					this.value = Double.valueOf(val.doubleValue());
				}
			} else {
				this.value = value;
			}
		}
	}

	/**
	 * Checks if a provided value is of a legal type.
	 * 
	 * @param value
	 *            the value being tested
	 * @return true if it is of a legal type
	 * @throws RuntimeException
	 *             if value is of an illegal type
	 */
	private boolean checkLegalEntry(Object value) {
		if (value == null || value instanceof Integer
				|| value instanceof Double || value instanceof String) {
			return true;
		} else {
			throw new RuntimeException(
					"illegal type of value inside ValueWrapper: "
							+ value.getClass().toString());
		}
	}

	/**
	 * Performs addition on the stored value. <br>
	 * <code>ans=value+incValue</code>
	 * 
	 * @param incValue
	 *            the value we're adding
	 */
	public void increment(Object incValue) {
		performOperation('+', incValue);
	}

	/**
	 * Performs subtraction on the stored value. <br>
	 * <code>ans=value-incValue</code>
	 * 
	 * @param decValue
	 *            the value we're subtracting
	 */
	public void decrement(Object decValue) {
		performOperation('-', decValue);
	}

	/**
	 * Performs multiplication on the stored value. <br>
	 * <code>ans=value*incValue</code>
	 * 
	 * @param mulValue
	 *            the value we're multiplying with
	 */
	public void multiply(Object mulValue) {
		performOperation('*', mulValue);
	}

	/**
	 * Performs division on the stored value. <br>
	 * <code>ans=value/incValue</code>
	 * <p>
	 * Do keep in mind that if both values are of type <code>Integer</code> this
	 * means this method will perform integer division.
	 * 
	 * @param divValue
	 *            the value we're dividing with
	 */
	public void divide(Object divValue) {
		performOperation('/', divValue);
	}

	/**
	 * Compares stored value with user provided value.
	 * 
	 * @param withValue
	 *            the value we're comparing the stored value with
	 * @return a negative integer if the original is smaller 0 if both numbers
	 *         are equal and a positive integer if the stored value is greater
	 */
	public int numCompare(Object withValue) {
		withValue = decideOperation(withValue);
		if (operatingWithDoubles) {
			return Double.compare(((Number) value).doubleValue(),
					((Number) withValue).doubleValue());
		} else {
			return Double.compare(((Number) value).intValue(),
					((Number) withValue).intValue());
		}
	}

	/**
	 * This method is used to prepare and rearrange the types of our parameters
	 * as necessary. If arg is null it is treated as an integer with value 0 If
	 * arg is a String it is parsed to its appropriate format, either a double
	 * or an integer.
	 * 
	 * @param arg
	 *            the user provided value used in arithmetic operations
	 * @return returns reformatted argument
	 */
	private Object decideOperation(Object arg) {
		operatingWithDoubles = false;
		if (checkLegalEntry(arg)) {
			if (arg == null) {
				arg = Integer.valueOf(0);
			} else if (arg instanceof String) {
				Number num = convertFromString((String) arg);
				if (num.doubleValue() % 1 == 0) {
					arg = new Integer(num.intValue());
				} else {
					arg = new Double(num.doubleValue());
				}
			}
		}

		if (arg instanceof Double || this.value instanceof Double) {
			operatingWithDoubles = true;
		}
		return arg;
	}

	/**
	 * Performs the appropriate arithmetic operation as decided by the given
	 * operator.
	 * 
	 * @param operator
	 *            defines the operation being performed
	 * @param arg
	 *            the second operand in our arithmetic operations
	 */
	private void performOperation(char operator, Object arg) {
		arg = decideOperation(arg);

		switch (operator) {
			case '+':
				execute(operatingWithDoubles, (Number) value, (Number) arg, (x,
						y) -> (x + y));
				break;
			case '-':
				execute(operatingWithDoubles, (Number) value, (Number) arg, (x,
						y) -> (x - y));
				break;
			case '*':
				execute(operatingWithDoubles, (Number) value, (Number) arg, (x,
						y) -> (x * y));
				break;
			case '/':
				execute(operatingWithDoubles, (Number) value, (Number) arg, (x,
						y) -> (x / y));
				break;
		}

	}

	/**
	 * Converts a valid <code>String</code> into a <code>Number</code>
	 * representation used for arithmetic operations.
	 * 
	 * @param incValue
	 *            the operand in <code>String</code> format
	 * @return parsed Number
	 */
	private Number convertFromString(String incValue) {

		Number ret;
		DecimalFormat form = new DecimalFormat("#.#");

		try {
			ret = form.parse(incValue);
		} catch (ParseException e) {
			throw new RuntimeException("unable to parse String " + incValue);
		}
		return ret;
	}

	/**
	 * Executes our arithmetic operation and sets the stored value to the newly
	 * calculated value.
	 * 
	 * @param operatingWithDoubles
	 *            marks if the result will be a double or an integer
	 * @param x
	 *            the first operand
	 * @param y
	 *            the second operand
	 * @param helper
	 */
	private void execute(boolean operatingWithDoubles, Number x, Number y,
			FOperation helper) {
		if (operatingWithDoubles) {
			this.value = new Double(helper.operate(x.doubleValue(),
					y.doubleValue()));
		} else {
			this.value = new Integer((int) helper.operate(x.intValue(),
					y.intValue()));
		}
	}

	/**
	 * A functional interface which defines a single method used for performing
	 * arithmetic operations.
	 * 
	 * @author Filip Džidić
	 *
	 */
	@FunctionalInterface
	private interface FOperation {
		/**
		 * Performs some kind of binary arithmetic operation on the two provided
		 * parameters
		 * 
		 * @param x
		 *            the first operand
		 * @param y
		 *            the second operand
		 * @return the result of the mathematical operation
		 */
		double operate(double x, double y);
	}

}
