package hr.fer.zemris.java.gui.operators;

import hr.fer.zemris.java.gui.calc.IOperator;

/**
 * Defines the power operation.
 * 
 * @author Filip Džidić
 *
 */
public class Pow implements IOperator {

	@Override
	public double calculate(double x, double y) {
		return Math.pow(x, y);
	}

	@Override
	public String getName() {
		return "x^n";
	}

}
