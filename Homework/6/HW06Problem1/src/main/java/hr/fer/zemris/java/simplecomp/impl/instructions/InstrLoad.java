package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
/**
 * Razred predstavlja naredbu load koju naše računalo može izvoditi.
 * <br><code>liad r0, LABELA</code>
 * <br>U r0 spremamo sadržaj spremljen na memorijskoj lokaciji LABELA
 * @author Filip Džidić
 *
 */
public class InstrLoad implements Instruction {
	/** registar u kojeg spremamo vrijednost iz memorije */
	private int registerIndex;
	/** memorijska lokacija na kojoj je sačuvan podatak koji čitamo */
	private int location;
	
	/**
	 * Konstruktor koji inicijalizira vrijednosti registara za daljnje računanje.
	 * Prvi parametar mora biti registar opće manije a drugi argument je memorijska lokacija s koje čitamo.
	 * @param arguments lista parametera koji je parser pročitao za našu naredbu
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size()!=2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1");
		}
		this.registerIndex=((Integer) arguments.get(0).getValue()).intValue();
		this.location=((Integer) arguments.get(1).getValue()).intValue();
	}
	
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getMemory().getLocation(location);
		computer.getRegisters().setRegisterValue(registerIndex, value);
		return false;
	}

}
