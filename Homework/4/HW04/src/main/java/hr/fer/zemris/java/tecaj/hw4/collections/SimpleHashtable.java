package hr.fer.zemris.java.tecaj.hw4.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The class <code>SimpleHashtable</code> is a simple implementation of a
 * collection meant for quick retrieval of stored data. It comes equipped with
 * several methods for adding, removing and changing inputted data as well as
 * iteration of all data through a for : each loop.
 * <p>
 * This class makes no guarantees of the order maintained within, in fact it is
 * very likely that the order will be change as the capacity is automatically
 * increased when required. <br>
 * If speed of iteration is important make sure not to set the initial capacity
 * too high because by its nature iteration will visit each bucket until the end
 * is reached. <br>
 * Upon reaching a load factor of 0.75 the hashtable is automatically resized by
 * doubling its capacity and rearranging the method. As said before this might
 * mean the order of the elements changes completely so keep that in mind.
 * <p>
 * Overflows are handled by chaining, a list of <code>TableEntry</code> is
 * created if their hash function assigns them the same index,
 * 
 * @author Filip Džidić
 *
 */
public class SimpleHashtable implements Iterable<SimpleHashtable.TableEntry> {

	/** represents an array of buckets within the hashtable */
	private TableEntry[] table;
	/** counts how many elements there are within the hashtable */
	private int size;
	/** counts how many times we've modified the hashtable */
	private int modificationCount;

	/**
	 * A <code>TableEntry</code> represents a single entry within
	 * <code>SimpleHashtable</code>. It accepts a key and value and reference to
	 * another reference which follows it within the list. This is used for
	 * handling overflows within our hashtable.
	 * 
	 * @author Filip Džidić
	 * @version 1.0
	 */
	public static class TableEntry {
		/**
		 * holds the key of the data we are storing, used for calculating the
		 * hash index value
		 */
		private Object key;
		/** holds the actual data */
		private Object value;
		/**
		 * reference to the next entry inside a specific hash bucket if
		 * overflows happen
		 */
		private TableEntry next;

		/**
		 * General constructor for <code>TableEntry</code> Initializes all of
		 * its parameter to user provided arguments
		 * 
		 * @param key
		 *            holds the key of the data we are storing
		 * @param value
		 *            holds the actual data
		 * @param next
		 *            reference to the next entry within our bucket chain
		 */
		public TableEntry(Object key, Object value, TableEntry next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Getter method for an entry's value.
		 * 
		 * @return the data stored inside the <code>TableEntry</code>
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Setter method for an entry's value.
		 * 
		 * @param value
		 *            the new value which is being assigned
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		/**
		 * Getter method for an entry's key.
		 * 
		 * @return the key of the specified <code>TableEntry</code>
		 */
		public Object getKey() {
			return key;
		}

		/**
		 * Returns a <code>String</code> representation of the
		 * <code>TableEntry</code>
		 */
		public String toString() {
			return key.toString() + "=" + value.toString();
		}
	}

	/**
	 * Default constructor initializes a new <code>SimpleHashtable</code> with
	 * 16 buckets.
	 */
	public SimpleHashtable() {
		this(16);
	}

	/**
	 * General constructor takes a user provided capacity and creates a new
	 * <code>SimpleHashtable</code> with a capacity of the nearest bigger or
	 * equal power of two. <br>
	 * For example, if the user specifies a capacity of 31 the actual
	 * <code>SimpleHashtable</code> will have a capacity of 32.
	 * 
	 * @param capacity
	 *            the number of buckets in the hashtable
	 * @throws IllegalArgumentException
	 *             if capacity is less than 1
	 */
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException(
					"capacity cannot be less than one");
		}
		int roundedCapacity = nearestPowerOfTwo(capacity);

		size = 0;
		modificationCount = 0;
		table = new TableEntry[roundedCapacity];
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
	}

	/**
	 * Inserts an <code>TableEntry</code> inside the hashtable. If a same key
	 * already exists inside the hashtable it updates its value. Key cannot be
	 * null but value can be null. <br>
	 * If by adding the element we reach a load factor of 0.75 the hashtable's
	 * capacity is automatically doubled and all the entries are reinserted
	 * which can change their order.
	 * 
	 * @param key
	 *            the key of our new entry
	 * @param value
	 *            the data held inside our new entry
	 * @throws IllegalArgumentException
	 *             if key is null
	 */
	public void put(Object key, Object value) {
		
		if (key == null) {
			throw new IllegalArgumentException("key cannot be null");
		}
		
		// first we calculate the index in the table
		int index = Math.abs(key.hashCode()) % table.length;
		modificationCount++;
		// head is the start of the list at index
		TableEntry head = table[index];
		// we create our entry
		TableEntry entry = new TableEntry(key, value, null);

		// if the list is empty create the first element
		if (head == null) {
			table[index] = entry;
			size++;
		} else { // if it is not empty put the entry at the end of the list
			while (true) {
				// if our entry already exists change its value and leave
				if (head != null && head.key.equals(key)) {
					head.setValue(value);
					break;
				}
				if (head.next == null) {
					head.next = entry;
					this.size++;
					break;
				}
				head = head.next;
			}
		}
		
		if ((size) / (double) table.length >= 0.75) {
			extend();
		}
	}

	/**
	 * Doubles the current <code>SimpleHashtable</code> capacity and reorders
	 * the elements based on new hash values. Method is used automatically when
	 * the load factor reaches a treshold of 75%.
	 */
	private void extend() {
		SimpleHashtable temp = new SimpleHashtable(this.table.length * 2);
		for (int i = 0, max = this.table.length; i < max; i++) {
			TableEntry head = table[i];
			// go through i-th list, if you find the value return true
			while (head != null) {
				temp.put(head.getKey(), head.getValue());
				head = head.next;
			}
		}
		clear();
		this.table = temp.table;
		this.size = temp.size;

	}

	/**
	 * Clears every element from the <code>SimpleHashtable</code>. Current
	 * capacity is not changed.
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			TableEntry head = table[i];
			if (head != null) {
				table[i] = null;
			}
		}
		this.size = 0;
		modificationCount++;
	}

	/**
	 * Searches through the <code>SimpleHashtable</code> based on provided key
	 * and returns the appropriate <code>TableEntry</code> value. Returns null
	 * if key is not found.
	 * 
	 * @param key
	 *            the key of the ordered pair we're searching for
	 * @return the associated <code>TableEntry</code> value
	 */
	public Object get(Object key) {
		if (key == null) {
			throw new IllegalArgumentException("key cannot be null");
		}

		// first we calculate the index in the table
		int index = Math.abs(key.hashCode()) % table.length;

		// head is the start of the list at index
		TableEntry head = table[index];

		// go through the list
		while (head != null) {
			if (head.getKey().equals(key)) {
				return head.getValue(); // if you find the key return its value
			}
			head = head.next;
		}
		// if we reach this the entry is not in the table, return null
		return null;

	}

	public int size() {
		return size;
	}

	/**
	 * Checks if a given key can be found in our <code>SimpleHashtable</code>.
	 * Only searches through the calculated hash slot.
	 * 
	 * @param key
	 *            the key we're searching for
	 * @return true if key is found
	 */
	public boolean containsKey(Object key) {

		// calculate the index in the table
		int index = Math.abs(key.hashCode()) % table.length;
		TableEntry head = table[index];
		// go through the list if you find the key return true
		while (head != null) {
			if (head.key.equals(key)) {
				return true;
			}
			head = head.next;
		}
		return false;
	}

	/**
	 * Checks if a given value can be found in our <code>SimpleHashtable</code>.
	 * Searches through the entire table.
	 * 
	 * @param value
	 *            the value we're searching for
	 * @return true if value is found
	 */
	public boolean containsValue(Object value) {
		// go through every list in the table
		for (int i = 0, max = this.table.length; i < max; i++) {
			TableEntry head = table[i];
			// go through i-th list, if you find the value return true
			while (head!=null) {
				if (head.getValue().equals(value)) {
					return true;
				}
				head = head.next;
			}
		}
		return false;
	}

	/**
	 * Removes an entry within the <code>SimpleHashtable</code> based on its
	 * key. The method does nothing if the key is not found.
	 * 
	 * @param key
	 *            the key of the entry we are removing
	 * @throws IllegalArgumentException
	 *             if user tries to remove a null key null keys are not allowed
	 */
	public void remove(Object key) {
		if (key == null) {
			throw new IllegalArgumentException("key cannot be null");
		}
		modificationCount++;
		// calculate the index in the table
		int index = Math.abs(key.hashCode()) % table.length;

		// these are our temporary variables
		TableEntry head = table[index];
		TableEntry entry = new TableEntry(null, null, null);

		// if we're removing the first entry in the list ...
		if (head.getKey().equals(key)) {
			entry = head;
			table[index] = entry.next; // skip over the first element
			// entry.next=null; //let the GC clean it up
			size--;
		} else {
			while (head != null) {
				// if we're not removing the first entry go through the list one
				// by one
				if (head.next != null && head.next.getKey().equals(key)) { // we're
																			// stopping
																			// at
																			// the
																			// entry
																			// PRECEEDING
																			// the
																			// entry
																			// we're
																			// deleting

					// skip over the entry we're removing
					entry = head.next;
					head.next = entry.next;
					// entry.next=null;
					// the PRECEEDING element now points to the SUCCEEDING
					// element
					// the GC should take care of work after this

					size--;
					break;
				}
				head = head.next;
			}
		}
	}

	/**
	 * Checks if <code>SimpleHashtable</code> is empty.
	 * 
	 * @return true if empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Calculates the nearest larger or equal power of two using bitwise
	 * operations.
	 * 
	 * @param capacity
	 *            the number we're rounding
	 * @return the nearest larger or equal power of two to provided number
	 */
	private int nearestPowerOfTwo(int originalCapacity) {
		int capacity = originalCapacity;
		capacity--;
		capacity |= capacity >> 1;
		capacity |= capacity >> 2;
		capacity |= capacity >> 4;
		capacity |= capacity >> 8;
		capacity |= capacity >> 16;
		capacity++;
		return capacity;
	}

	/**
	 * Returns the <code>String</code> representation of our
	 * <code>SimpleHashtable</code>. Lists every <code>TableEntry</code> in the
	 * order it is stored. <br>
	 * Example of output:
	 * <br>
	 * <code>"[key1=value1, key2=value2, key3=value3, ..., key_n=value_n]"</code>
	 * <br>
	 * Returns <code>"[]"</code> if table is empty.
	 */
	public String toString() {
		if (this.size == 0) {
			return "[]";
		}
		StringBuilder buildy = new StringBuilder();
		buildy.append('[');
		for (int i = 0; i < table.length; i++) {
			TableEntry head = table[i];
			if (head != null) {
				while (head.next != null) {
					if (head != null) {
						buildy.append(head.toString() + ", ");
					}
					head = head.next;
				}
				buildy.append(head.toString() + ", ");
			}
		}
		buildy.replace(buildy.length() - 2, buildy.length(), "");
		buildy.append("]");
		return buildy.toString();
	}

	@Override
	public Iterator<TableEntry> iterator() {
		return new IteratorImpl();
	}

	/**
	 * This class represents an <code>Iterator</code> in our collection.<br>
	 * It enables us to go through our collection using for : each loops. It
	 * provides methods for retrieving the next element in our collection as
	 * well as methods for checking if a next element exists and a method for
	 * removing elements.
	 * <p>
	 * When using the remove() method you have to keep in mind that you can only
	 * call it once per retrieved element. <br>
	 * When iterating through the collection any outside changes to the
	 * collection will cause the iterator to throw an exception as it has no
	 * guarantee that certain elements will not be skipped.
	 * 
	 * 
	 * @author Filip Džidić
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry> {
		/** used to keep track of the current bucket we're iterating through */
		private int index;
		/** used to keep track of how many elements we have */
		private int count;
		/** tracks any outside changes made to the collection */
		private int modCount;
		/** tracks if next() has been called before removing */
		private boolean removeAllowed;
		/** tracks the current element we are iterating through */
		private TableEntry head;
		/** tracks the previous element we've iterated through */
		private TableEntry previous;
		
		/**
		 * Default constructor initializes all the necessary class variables to enable iteration.
		 */
		public IteratorImpl() {
			index = 0;
			count = size;
			removeAllowed = false;
			modCount = modificationCount;
			head = table[index];
		}

		
		/**
		 * Returns true if there are still more elements to iterate through.
		 * @throws ConcurrentModificationException if we try iterating through a collection that has been modified from outside the iterator
		 */
		public boolean hasNext() {
			if (modCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			return count > 0;
		}
		
		/**
		 * Removes the last element retrieved by the next() method.
		 * This method can only be called once per retrieval otherwise an exception is thrown.
		 * @throws ConcurrentModificationEXception if collection is modified outside the iterator
		 * @throws IllegalStateException if remove is called more than once per call of the next() method 
		 */
		public void remove() {
			if (modCount != modificationCount) {
				throw new ConcurrentModificationException(
						"collection has been modified outside of the iterator");
			}
			if (removeAllowed == false) {
				throw new IllegalStateException(
						"cannot remove before another call to method next()");
			}

			SimpleHashtable.this.remove(previous.getKey());
			count--;
			removeAllowed = false;
			modCount = modificationCount;

		}

		/**
		 * Retrieves the next element inside the collection.
		 * @throws NoSuchElementException if no more elements can be retrieved
		 * @throws ConcurrentModificationException if collection is modified outside the iterator
		 */
		public TableEntry next() {
			if (modCount != modificationCount) {
				throw new ConcurrentModificationException(
						"collection has been modified outside of the iterator");
			}
			if (count <= 0) {
				throw new NoSuchElementException();
			}
			while (index < table.length && head == null) {
				head = table[index++];
			}
			count--;
			previous = head;
			head = head.next;
			removeAllowed = true;
			return previous;

		}

	}

}
