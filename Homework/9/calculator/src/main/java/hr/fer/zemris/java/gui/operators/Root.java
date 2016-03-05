package hr.fer.zemris.java.gui.operators;

import hr.fer.zemris.java.gui.calc.IOperator;

/**
 * Defines the root operation.
 * 
 * @author Filip Džidić
 *
 */
public class Root implements IOperator {

	@Override
	public double calculate(double x, double y) {
		return Math.pow(x, 1.0 / y);
	}

	@Override
	public String getName() {
		return "n\u221Ax";
	}

}
