package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
/**
 * Razred predstavlja naredbu mul koju naše računalo može izvoditi.
 * <br><code>mul r0, r1, r2</code>
 * <br>Rezultat r1*r2 spremamo u r0
 * @author Filip Džidić
 *
 */
public class InstrMul implements Instruction {
	/** prvi operand naredbe */
	private int indexRegister1;
	/** drugi operand naredbe */
	private int indexRegister2;
	/** rezultantni operator naredbe */
	private int indexRegister3;
	
	/**
	 * Konstruktor koji inicijalizira vrijednosti registara za daljnje računanje.
	 * Svi parametri moraju biti registri.
	 * @param arguments lista parametera koji je parser pročitao za našu naredbu
	 */
	public InstrMul(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		if (!arguments.get(2).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 2!");
		}
		this.indexRegister1 = ((Integer) arguments.get(0).getValue())
				.intValue();
		this.indexRegister2 = ((Integer) arguments.get(1).getValue())
				.intValue();
		this.indexRegister3 = ((Integer) arguments.get(2).getValue())
				.intValue();
	}

	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(indexRegister2);
		Object value2 = computer.getRegisters()
				.getRegisterValue(indexRegister3);
		computer.getRegisters().setRegisterValue(
				indexRegister1,
				Integer.valueOf(((Integer) value1).intValue()
						* ((Integer) value2).intValue()));
		return false;
	}
}