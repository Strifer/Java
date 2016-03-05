package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.IFunction;

/**
 * Defines the arcus cosine function.
 * 
 * @author Filip Džidić
 *
 */
public class ArcCos implements IFunction {

	@Override
	public double calculate(double x) {
		return Math.acos(x);
	}

	@Override
	public String getName() {
		return "arccos";
	}

}
