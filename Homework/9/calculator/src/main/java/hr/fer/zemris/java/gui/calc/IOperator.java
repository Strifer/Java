package hr.fer.zemris.java.gui.calc;

/**
 * This interface defines a single binary operation within our calculator.
 * 
 * @author Filip Džidić
 *
 */
public interface IOperator {
	/**
	 * Performs the necessary calculation of the binary operation. Keep in mind
	 * that not all binary operations are commutative so keep track of the order
	 * of operands
	 * 
	 * @param x
	 *            the first operand
	 * @param y
	 *            the second operand
	 * @return
	 */
	public double calculate(double x, double y);

	/**
	 * Returns the name of our operation
	 * 
	 * @return the name of th operation
	 */
	public String getName();
}
