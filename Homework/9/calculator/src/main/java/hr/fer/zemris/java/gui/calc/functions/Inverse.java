package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.IFunction;

/**
 * Defines the multiplicative inverse function
 * 
 * @author Filip Džidić
 *
 */
public class Inverse implements IFunction {

	@Override
	public double calculate(double x) {
		return 1.0 / x;
	}

	@Override
	public String getName() {
		return "1/x";
	}
}
