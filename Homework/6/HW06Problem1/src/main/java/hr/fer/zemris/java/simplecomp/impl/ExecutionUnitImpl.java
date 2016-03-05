package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;
/**
 * Ovaj razred predstavlja upravljačku jedinicu našeg računalnog sustava.
 * Vrši dohvat, dekodiranje i izvođenje naredbi sve dok se ne dogodi greška ili dok se ne izvede naredba halt
 * @author Filip Džidić
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	/**
	 * Metoda koja pokreće izvedbu našeg računala vraća false ako izađemo iz nje zbog neke greške.
	 */
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);
		boolean haltExecuted = false;
		while (!haltExecuted) {
			int programCount = computer.getRegisters().getProgramCounter();
			Instruction instruction = (Instruction) computer.getMemory().getLocation(programCount);
			computer.getRegisters().incrementProgramCounter();
			try {
				haltExecuted = instruction.execute(computer);
			} catch (IllegalArgumentException ex) {
				return false;
			}
			
		}
		return true;
	}

}
