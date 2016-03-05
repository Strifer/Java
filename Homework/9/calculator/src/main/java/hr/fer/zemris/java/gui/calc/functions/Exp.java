package hr.fer.zemris.java.gui.calc.functions;

import hr.fer.zemris.java.gui.calc.IFunction;

/**
 * Defines the e^x function where e is euler's number.
 * 
 * @author Filip Džidić
 *
 */
public class Exp implements IFunction {

	@Override
	public double calculate(double x) {
		return Math.exp(x);
	}

	@Override
	public String getName() {
		return "e^x";
	}

}
