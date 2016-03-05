package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.IFunction;

/**
 * Defines the cotangent function.
 * 
 * @author Filip Džidić
 *
 */
public class Ctg implements IFunction {

	@Override
	public double calculate(double x) {
		return 1.0 / Math.tan(x);
	}

	@Override
	public String getName() {
		return "ctg";
	}

}
