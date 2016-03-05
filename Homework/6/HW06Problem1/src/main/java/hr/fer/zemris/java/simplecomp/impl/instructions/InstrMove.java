package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
/**
 * Razred predstavlja naredbu move koju naše računalo može izvoditi.
 * <br><code>move r0, r1</code>
 * <br>U r0 kopiramo sadržaj s registra r1
 * @author Filip Džidić
 *
 */
public class InstrMove implements Instruction {
	/** registar u kojeg kopiramo */
	private int registerIndex1;
	/** registar s kojeg kopiramo */
	private int registerIndex2;
	
	/**
	 * Konstruktor koji inicijalizira vrijednosti registara za daljnje računanje.
	 * Oba parametra moraju biti registri.
	 * @param arguments lista parametera koji je parser pročitao za našu naredbu
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if (arguments.size()!=2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1");
		}
		this.registerIndex1=((Integer) arguments.get(0).getValue()).intValue();
		this.registerIndex2=((Integer) arguments.get(1).getValue()).intValue();
	}
	
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(registerIndex2);
		computer.getRegisters().setRegisterValue(registerIndex1, value);
		return false;
	}

}
