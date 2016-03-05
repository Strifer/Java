package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.IFunction;

/**
 * Defines the 10^x function.
 * 
 * @author Filip Džidić
 *
 */
public class Power10 implements IFunction {

	@Override
	public double calculate(double x) {
		return Math.pow(10.0, x);
	}

	@Override
	public String getName() {
		return "10^x";
	}

}
