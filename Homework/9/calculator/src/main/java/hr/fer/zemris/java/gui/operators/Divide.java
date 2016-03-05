package hr.fer.zemris.java.gui.operators;

import hr.fer.zemris.java.gui.calc.IOperator;

/**
 * Defines the division operation.
 * 
 * @author Filip Džidić
 *
 */
public class Divide implements IOperator {

	@Override
	public double calculate(double x, double y) {
		return x / y;
	}

	@Override
	public String getName() {
		return "/";
	}

}
