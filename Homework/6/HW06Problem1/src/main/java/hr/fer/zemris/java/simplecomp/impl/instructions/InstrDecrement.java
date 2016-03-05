package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
/**
 * Razred predstavlja naredbu decrement koju naše računalo može izvoditi.
 * <code>decrement r0</code>
 * <br>Rezultat r0-1 spremamo u r0
 * @author Filip Džidić
 *
 */
public class InstrDecrement implements Instruction {
	/** registar čiju vrijednost umanjujemo */
	private int registerIndex;
	
	/**
	 * Konstruktor kojim inicijaliziramo našu naredbu prateći koje argumente je dobila. 
	 * Ova naredba prima samo jedan argument, registar čiju vrijednost umanjujemo.
	 * @param arguments lista argumenata uz naredbu
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {
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
		Integer var=(Integer) computer.getRegisters().getRegisterValue(registerIndex)-1;
		computer.getRegisters().setRegisterValue(registerIndex, var);
		return false;
	}

}
