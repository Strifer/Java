package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
/**
 * Razred predstavlja naredbu increment koju naše računalo može izvoditi.
 * <code>increment r0</code>
 * <br>Rezultat r0+1 spremamo u r0
 * @author Filip Džidić
 *
 */
public class InstrIncrement implements Instruction {
	/** registar čiju vrijednost uvećavamo */
	private int registerIndex;
	/**
	  * Konstruktor za naredbu koji uzima cijelu listu argumenata nad njom i provjerava je li dobro zadana
	  * @param arguments argumenti uz naredbu
	  */
	public InstrIncrement(List<InstructionArgument> arguments) {
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
		Integer var=(Integer) computer.getRegisters().getRegisterValue(registerIndex)+1;
		computer.getRegisters().setRegisterValue(registerIndex, var);
		return false;
	}

}
