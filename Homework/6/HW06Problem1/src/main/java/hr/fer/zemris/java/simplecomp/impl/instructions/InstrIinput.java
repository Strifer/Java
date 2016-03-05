package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
/**
 * Razred predstavlja naredbu iinput koju naše računalo može izvoditi.
 * <code>iinput LABELA</code>
 * <br>Naredba traži od korisnika za upis jednog cijelog broja kojeg spremamo u memoriju na lokaciju LABELA
 * @author Filip Džidić
 *
 */
public class InstrIinput implements Instruction {
	/** lokacija na koju spremamo učitani cijeli broj */
	private int location;
	 /**
	  * Konstruktor za naredbu koji uzima cijelu listu argumenata nad njom i provjerava je li dobro zadana
	  * @param arguments argumenti uz naredbu
	  */
	public InstrIinput(List<InstructionArgument> arguments) {
		if (arguments.size()!=1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		this.location=((Integer)arguments.get(0).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String number=null;
		try {
			number = reader.readLine().trim();
			computer.getMemory().setLocation(location, Integer.parseInt(number));
			computer.getRegisters().setFlag(true);
		} catch (IOException e) {
			computer.getRegisters().setFlag(false);
		} catch (NumberFormatException ex) {
			computer.getRegisters().setFlag(false);
		} 
		return false;
	}
}
