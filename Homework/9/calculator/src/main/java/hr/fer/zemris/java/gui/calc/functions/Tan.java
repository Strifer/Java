package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.IFunction;

/**
 * Defines the cotangent function.
 * 
 * @author Filip Džidić
 *
 */
public class Tan implements IFunction {

	@Override
	public double calculate(double x) {
		return Math.tan(x);
	}

	@Override
	public String getName() {
		return "tan";
	}

}
