package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.IFunction;

/**
 * Defines the arcus cotangent function.
 * 
 * @author Filip Džidić
 *
 */
public class ArcCotan implements IFunction {

	@Override
	public double calculate(double x) {
		return 1.0 / (Math.atan(x));
	}

	@Override
	public String getName() {
		return "arctg";
	}

}
