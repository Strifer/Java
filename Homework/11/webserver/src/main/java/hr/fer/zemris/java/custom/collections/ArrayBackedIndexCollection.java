package hr.fer.zemris.java.custom.collections;

/**
 * <code>ArrayBackedIndexCollection</code> represents a dynamic array of
 * objects. An object of type <code>ArrayBackedIndexCollection</code> contains
 * three fields representing its size, capacity and all of the elements it
 * contains.
 * <p>
 * In addition, this class provides several methods for adding an element,
 * inserting an element, removing an element as well as other useful methods
 * found in other implementations of resizeable arrays. One thing to keep in
 * mind is that it is not allowed to add null references.
 * <p>
 * The default constructor creates an <code>ArrayBackedIndexCollection</code> of
 * size 16.
 * 
 * @author Filip Džidić
 * @version 1.0
 *
 */
public class ArrayBackedIndexCollection {

	/**
	 * The size of the array.
	 */
	private int size;

	/**
	 * The capacity of the array.
	 */
	private int capacity;

	/**
	 * Contains all the elements in the array.
	 */
	Object[] elements;

	/**
	 * The default constructor sets the array's capacity to 16.
	 */
	public ArrayBackedIndexCollection() {
		this(16);
	}

	/**
	 * Sets the initial capacity to what the user provided. The capacity must be
	 * a natural number.
	 * 
	 * @param initialCapacity
	 *            the array's initial capacity
	 * @throws IllegalArgumentException
	 *             if capacity is less than 1
	 */
	public ArrayBackedIndexCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity less than 1");
		}
		size = 0;
		capacity = initialCapacity;
		elements = new Object[capacity];
	}

	/**
	 * Checks the size of the array and determines if it is empty or not.
	 * 
	 * @return true if array is empty
	 */
	public boolean isEmpty() {
		if (size == 0)
			return true;
		else
			return false;
	}

	/**
	 * Returns the size of the array.
	 * 
	 * @return size of the array
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds a new element in the first available location in the array. Adding
	 * null references is not allowed. If the array is full, dynamically
	 * increases its capacity by a factor of two.
	 * <p>
	 * Complexity of adding is O(n) in the worst case scenario.
	 * 
	 * @param value
	 *            the element which is being added
	 * @throws IllegalArgumentException
	 *             if the user tries to add null
	 */
	public void add(Object value) {

		if (value == null) {
			throw new IllegalArgumentException(
					"Null references are not allowed");
		}

		if (size == capacity) {
			this.extend();
		}

		for (int i = 0; i < capacity; i++) {
			if (elements[i] == null) {
				elements[i] = value;
				size++;
				break;
			}
		}
	}

	/**
	 * Retrieves the element at the user provided index in the array.
	 * <p>
	 * Note that the index must be within the array's current bounds.
	 * <p>
	 * Complexity is O(1)
	 * 
	 * @param index
	 *            the index of the element being retrieved
	 * @return the element being searched for
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the user tries getting something out of the arrays bounds
	 *             as determined by the current size
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new ArrayIndexOutOfBoundsException("Out of bounds!");
		}
		return elements[index];
	}

	/**
	 * Removes the element found at the user provided index.
	 * <p>
	 * Note that the index must be within the array's current bounds.
	 * <p>
	 * Average complexity is O(n)
	 * 
	 * @param index
	 *            the index of the element being removed
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the user tries getting something out of the arrays bounds
	 *             as determined by the current size
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new ArrayIndexOutOfBoundsException("Out of bounds!");
		}
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		elements[size - 1] = null;
		size--;
	}

	/**
	 * Inserts the specified element at the specified position in this list.
	 * Shifts the element currently at that position (if any) and any subsequent
	 * elements to the right (adds one to their indices). Note that the index
	 * cannot exceed the array's current bounds and that adding null is not
	 * allowed.
	 * 
	 * @param index
	 *            index at which the specified value will be inserted
	 * @param value
	 *            value to be inserted
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the user tries indexing out of bounds
	 * @throws IllegalArgumentException
	 *             if the users tries adding null
	 */
	public void insert(int index, Object value) {
		if (index < 0 || index > size) {
			throw new ArrayIndexOutOfBoundsException("Out of bounds!");
		}
		if (value == null) {
			throw new IllegalArgumentException(
					"Null references are not allowed");
		}
		if (size == capacity) {
			this.extend();
		}
		Object transfer = elements[index];
		for (int i = index; i < size; i++) {
			Object temp = elements[i + 1];
			elements[i + 1] = transfer;
			transfer = temp;
		}
		elements[index] = value;
		size++;
	}

	/**
	 * Retrieves the index of the first occurrence of the searched value within
	 * the array. Complexity is O(n)
	 * 
	 * @param value
	 *            the value being searched
	 * @return the index of the first occurrence of the value if found, -1 if
	 *         the element is not found
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value))
				return i;
		}

		return -1;
	}

	/**
	 * Checks if the array contains at least one object value. Complexity is
	 * O(n)
	 * 
	 * @param value
	 *            the value being searched
	 * @return true if value is found
	 */
	public boolean contains(Object value) {
		if (indexOf(value) == -1) {
			return false;
		}
		return true;
	}

	/**
	 * Removes all elements from the array
	 */
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * Increases the array's capacity by a factor of two.
	 */
	private void extend() {
		Object[] extension = new Object[capacity * 2];
		for (int i = 0; i < size; i++) {
			extension[i] = elements[i];
		}
		elements = extension;
		capacity *= 2;
	}

}
