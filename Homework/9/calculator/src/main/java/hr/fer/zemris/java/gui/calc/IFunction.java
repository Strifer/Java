package hr.fer.zemris.java.gui.calc;

/**
 * This interface defines a single mathematical function within our calculator.
 * 
 * @author Filip Džidić
 *
 */
public interface IFunction {
	/** performs the necessary calculation */
	public double calculate(double x);

	/** returns the function's name */
	public String getName();
}
