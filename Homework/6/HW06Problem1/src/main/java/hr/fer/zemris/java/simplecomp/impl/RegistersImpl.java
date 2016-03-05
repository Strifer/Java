package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;
/**
 * Implementacija sučelja Registers koje predstavlja sve registre u našem računalnom sustavu
 * @author Filip Džidić
 */
public class RegistersImpl implements Registers {
	/**
	 * Vrijednosti registara se čuvaju u polju
	 */
	private Object[] values;
	/**
	 * Predstavlja programsko brojilo našeg računala
	 */
	private int programcounter;
	/**
	 * Predstavlja zastavice našeg računala
	 */
	private boolean flag;
	/**
	 * Inicijalizira registre našeg računala na zadani broj.
	 * @param regsLen broj registara u računalu
	 */
	public RegistersImpl(int regsLen) {
		values = new Object[regsLen];
	}
	
	@Override
	public Object getRegisterValue(int index) {
		return values[index];
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		values[index]=value;
	}

	@Override
	public int getProgramCounter() {
		return programcounter;
	}

	@Override
	public void setProgramCounter(int value) {
		programcounter=value;

	}

	@Override
	public void incrementProgramCounter() {
		programcounter++;
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean value) {
		flag=value;
	}

}
