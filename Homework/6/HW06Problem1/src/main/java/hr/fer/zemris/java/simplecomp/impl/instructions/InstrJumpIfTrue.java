package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Razred predstavlja naredbu jumpIfTrue koju naše računalo može izvoditi. <br>
 * <code>jumpIfTrue LABELA</code> <br>
 * Programsko brojilo preusmjeravamo na lokaciju labele i izvodimo naredbe na
 * sljedećim memorijskim lokacijama ali samo ako je uvjet ispunjen (postavljena
 * zastavica)
 * 
 * @author Filip Džidić
 *
 */
public class InstrJumpIfTrue implements Instruction {
	/** lokacija na koju preusmjeravamo programsko brojilo */
	private int location;
	
	/**
	 * Konstruktor koji inicijalizira naredbu i provjerava ispravnost argumenata.
	 * Naredba mora imati element memorijske lokaciju da bi znao gdje skočiti pri izvedbi.
	 * @param arguments argumenti uz naredbu
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}

		this.location = ((Integer) arguments.get(0).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		if (computer.getRegisters().getFlag()) {
			computer.getRegisters().setProgramCounter(location);
		}
		return false;
	}

}
