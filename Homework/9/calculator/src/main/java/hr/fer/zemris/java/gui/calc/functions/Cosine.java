package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.IFunction;

/**
 * Defines the cosine function.
 * 
 * @author Filip Džidić
 *
 */
public class Cosine implements IFunction {

	@Override
	public double calculate(double x) {
		return Math.cos(x);
	}

	@Override
	public String getName() {
		return "cos";
	}

}
