package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.IFunction;

/**
 * Defines the natural logarithm function.
 * 
 * @author Filip Džidić
 *
 */
public class Ln implements IFunction {

	@Override
	public double calculate(double x) {
		return Math.log(x);
	}

	@Override
	public String getName() {
		return "ln";
	}

}
