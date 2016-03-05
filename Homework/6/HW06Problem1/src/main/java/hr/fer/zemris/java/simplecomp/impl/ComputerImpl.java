package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;
/**
 * Razred implementira sučelje Computer i predstavlja računalni sustav sasatvljen od registara i memorije.
 * @author Filip Džidić
 *
 */
public class ComputerImpl implements Computer {
	/** registri našeg računala */
	private Registers registers;
	/** memorija našeg računala */
	private Memory memory;
	
	/**
	 * Konstruktor inicijalizira memoriju i registre na zadane vrijednosti
	 * @param locationNum veličina memorije
	 * @param registers broj registara
	 */
	public ComputerImpl(int locationNum, int registers) {
		this.registers = new RegistersImpl(registers);
		memory = new MemoryImpl(locationNum);
	}
	@Override
	public Registers getRegisters() {
		return registers;
	}

	@Override
	public Memory getMemory() {
		return memory;
	}

}
