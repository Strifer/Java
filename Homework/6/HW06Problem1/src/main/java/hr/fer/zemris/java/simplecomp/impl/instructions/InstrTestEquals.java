package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
/**
 * Razred predstavlja naredbu testEquals koju naše računalo može izvoditi.
 * <code>testEquals r0, r1</code>
 * <br>Uspoređujemo registre r0 i r1 i postavljamo zastavicu na true ako su jednaki.
 * @author Filip Džidić
 *
 */
public class InstrTestEquals implements Instruction {
	/** prvi operand */
	private int registerIndex1;
	/** drugi operand */
	private int registerIndex2;
	/**
	 * Konstruktor koji izgrađuje našu naredbu čitajući i prateći format argumenata koji su uz nju.
	 * Oba argumenta trebaju biti registri.
	 * @param arguments lista argumenata koje gledamo
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
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
		Object value1 =computer.getRegisters().getRegisterValue(registerIndex1);
		Object value2 =computer.getRegisters().getRegisterValue(registerIndex2);
		computer.getRegisters().setFlag(value1.equals(value2));
		return false;
	}

}
