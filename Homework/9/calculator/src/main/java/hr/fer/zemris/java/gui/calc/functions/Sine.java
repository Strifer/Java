package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.IFunction;

/**
 * Defines the sine function.
 * 
 * @author Filip Džidić
 *
 */
public class Sine implements IFunction {

	@Override
	public double calculate(double x) {
		return Math.sin(x);
	}

	@Override
	public String getName() {
		return "sin";
	}

}
