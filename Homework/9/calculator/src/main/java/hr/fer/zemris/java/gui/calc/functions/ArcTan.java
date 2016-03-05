package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.IFunction;

/**
 * Defines the arcus tangent function.
 * 
 * @author Filip Džidić
 *
 */
public class ArcTan implements IFunction {

	@Override
	public double calculate(double x) {
		return (Math.atan(x));
	}

	@Override
	public String getName() {
		return "arctan";
	}

}
