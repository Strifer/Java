package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.NoSuchElementException;
/**
 * This class represents a collection where multiples values can be assigned to a single key.
 * These multiple values are stored on a LIFO basis and are retrieved as such.
 * It comes equipped with methods for adding and retrieving values according to their keys.
 * @author Filip Džidić
 *
 */
public class ObjectMultistack {
	/** we arrange our values by key inside this main collection */
	private HashMap<String, MultistackEntry> multistack;
	
	/**
	 *  Default constructor initializes our collection used for storing all of our elements.
	 */
	public ObjectMultistack() {
		multistack = new HashMap<String, MultistackEntry>();
	} 
	/**
	 * This inner class represents a single LIFO stack of values that are assigned to a given key inside 
	 * our map collection.
	 * @author Filip Džidić
	 *
	 */
	private static class MultistackEntry {
		private ValueWrapper valueWrapper;
		private MultistackEntry next;
		
		/**
		 * Constructs a new element in our LIFO structure
		 * @param value the value stored within the element
		 * @param next reference to the next element in the LIFO structure
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			valueWrapper = value;
			this.next=next;
		}
		
	}
	/**
	 * Adds a new element to the collection, based on its provided key.
	 * @param name the key we're using for storage and retrieval
	 * @param valueWrapper the value being stored
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		MultistackEntry reference = multistack.get(name);
		MultistackEntry element = new MultistackEntry(valueWrapper, reference);
		multistack.put(name, element);
	}
	
	/**
	 * Retrieves and removes the top element in the LIFO structure assigned by it's key (or name).
	 * @param name the key the LIFO structure is stored under
	 * @return the head of the LIFO structure
	 * @throws NoSuchElementException if no elements can be found under given key
	 */
	public ValueWrapper pop(String name) {
		MultistackEntry reference = multistack.get(name);
		if(reference==null) {
			throw new NoSuchElementException("there is no stack for entered key "+name);
		}
		ValueWrapper ret = reference.valueWrapper;
		multistack.put(name, reference.next);
		return ret;
	}
	/**
	 * Only retrieves the top element in the LIFO structure assigned by it's key (or name).
	 * Does not delete any elements.
	 * @param name the key the LIFO structure is stored under
	 * @return the head of the LIFO structure
	 * @throws NoSuchElementException if no elements can be found under given key
	 */
	public ValueWrapper peek(String name) {
		MultistackEntry reference = multistack.get(name);
		if(reference==null) {
			throw new NoSuchElementException("there is no stack for entered key "+name);
		}
		return reference.valueWrapper;
	}
	/**
	 * Checks if there are any elements under a user defined key
	 * @param name the index of our search
	 * @return true if no elements are found
	 */
	public boolean isEmpty(String name) {
		return multistack.get(name)==null;
	}
}
