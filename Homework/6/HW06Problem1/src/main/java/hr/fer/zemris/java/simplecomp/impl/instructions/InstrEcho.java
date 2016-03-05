package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
/**
 * Razred predstavlja naredbu echo koju naše računalo može izvoditi.
 * <code>echo r0</code>
 * <br>Sadržaj r0 ispisujemo na ekran
 * @author Filip Džidić
 *
 */
public class InstrEcho implements Instruction {
	/** registar čiji sadržaj ispisujemo na ekran */
	private int registerIndex;
	
	/**
	 * Konstruktor kojim inicijaliziramo našu naredbu prateći koje argumente je dobila. 
	 * Ova naredba prima samo jedan argument, registar čiju vrijednost ispisujemo na ekran.
	 * @param arguments lista argumenata uz naredbu
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size()!=1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		
		this.registerIndex=((Integer) arguments.get(0).getValue()).intValue();
	}
	
	@Override
	public boolean execute(Computer computer) {
		Object output =computer.getRegisters().getRegisterValue(registerIndex);
		System.out.print(output);
		return false;
	}

}
