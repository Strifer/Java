package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.IFunction;

/**
 * Defines the base 10 logarithm function.
 * 
 * @author Filip Džidić
 *
 */
public class Log implements IFunction {

	@Override
	public double calculate(double x) {
		return Math.log10(x);
	}

	@Override
	public String getName() {
		return "log";
	}

}
