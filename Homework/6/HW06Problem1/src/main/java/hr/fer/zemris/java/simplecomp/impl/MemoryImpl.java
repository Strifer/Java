package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;
/**
 * Ovaj razred predstavlja implementaciju memorije u našem računalnom sustavu.
 * @author Filip Džidić
 *
 */
public class MemoryImpl implements Memory {
	/** memorija našeg računala je polje referenci */
	private Object[] locations;
	
	/** 
	 * Inicijalizira memoriju na zadanu veličinu
	 * @param size veličina memorije
	 */
	public MemoryImpl(int size) {
		locations = new Object[size];
	}
	
	@Override
	public void setLocation(int location, Object value) {
		locations[location]=value;

	}

	@Override
	public Object getLocation(int location) {
		return locations[location];
	}

}
