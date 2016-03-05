package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.IFunction;

/**
 * Defines the arcus sine function.
 * 
 * @author Filip Džidić
 *
 */
public class ArcSin implements IFunction {

	@Override
	public double calculate(double x) {
		return Math.asin(x);
	}

	@Override
	public String getName() {
		return "arcsin";
	}

}
