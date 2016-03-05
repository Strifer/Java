package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
/**
 * Razred predstavlja naredbu halt koju naše računalo može izvoditi.
 * Naredba halt zaustavlja rad računala.
 * @author Filip Džidić
 *
 */
public class InstrHalt implements Instruction {
	/**
	 * Naredba halt ne smije imati nikakvih argumenata
	 * @param arguments potencijalni argumenti uz naredbu
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if (arguments.size()!=0) {
			throw new IllegalArgumentException("Expected 0 arguments!");
		}
	}
	
	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
