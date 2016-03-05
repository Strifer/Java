package hr.fer.zemris.java.custom.collections;

/**
 * The <code>ObjectStack</code> class represents a dynamic LIFO stack of
 * Objects. It provides the usual <code>push</code> and <code>pop</code> methods
 * as well as a <code>peek</code> method which checks the first element on the
 * stack without removing it, a method for checking if the stack is
 * <code>empty</code> and a method which returns the stack's size.
 * <p>
 * One thing to keep in mind is that this class delegates most of its methods to
 * <code>ArrayBackedIndexCollection</code> which means added complexity when
 * copying the structure while resizing.
 * 
 * @author Filip Džidić
 * @version 1.0
 * @see hr.fer.zemris.java.custom.collections.ArrayBackedIndexCollection
 * @see java.lang.Object
 *
 */
public class ObjectStack {
	/**
	 * Where the elements of the stack are stored. When full the capacity is
	 * increased by a factor of two.
	 */
	private ArrayBackedIndexCollection collection;
	/**
	 * The index of the top of the stack
	 */
	private int top;

	/**
	 * Sets the initial capacity to what the user provided. The capacity must be
	 * a natural number.
	 * 
	 * @param initialCapacity
	 *            the stack's initial capacity
	 * @throws IllegalArgumentException
	 *             if capacity is less than 1
	 */
	public ObjectStack(int collectionsize) {
		collection = new ArrayBackedIndexCollection(collectionsize);
		top = 0;
	}

	/**
	 * The default constructor sets the stack's initial capacity to 16
	 */
	public ObjectStack() {
		this(16);
	}

	/**
	 * Checks if the stack is empty or not.
	 * 
	 * @return true if stack is empty
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	/**
	 * Checks the size of the stack
	 * 
	 * @return the number of elements in the stack
	 */
	public int size() {
		return collection.size();
	}

	/**
	 * Adds an element to the top of the stack. Does not accept null values.
	 * Average complexity is O(1), keep in mind that this might not be true when
	 * the stack is being resized.
	 * 
	 * @param value
	 *            the element being pushed
	 * @see hr.fer.zemris.java.custom.collections.ArrayBackedIndexCollection
	 * @see hr.fer.zemris.java.custom.collections.ArrayBackedIndexCollection#insert(int,
	 *      Object)
	 */
	public void push(Object value) {
		collection.insert(top, value);
		top++;
	}

	/**
	 * Removes and returns an element from the top of the stack. Average
	 * complexity is O(1).
	 * 
	 * @return the top element on the stack
	 * @throws EmptyStackException
	 *             if popping from an empty stack
	 * @see hr.fer.zemris.java.custom.collections.ArrayBackedIndexCollection
	 * @see hr.fer.zemris.java.custom.collections.ArrayBackedIndexCollection#get(int)
	 */
	public Object pop() {
		if (this.isEmpty()) {
			throw new EmptyStackException("Popping from empty stack");
		}
		Object ret = collection.get(top - 1);
		collection.remove(top - 1);
		top--;
		return ret;
	}

	/**
	 * Returns an element from the top of the stack but does not delete it.
	 * Average complexity is O(1).
	 * 
	 * @return the top element on the stack
	 * @throws EmptyStackException
	 *             if peeking in an empty stack
	 * @see hr.fer.zemris.java.custom.collections.ArrayBackedIndexCollection
	 * @see hr.fer.zemris.java.custom.collections.ArrayBackedIndexCollection#get(int)
	 */
	public Object peek() {
		if (this.isEmpty()) {
			throw new EmptyStackException("Peeking in empty stack");
		}
		return collection.get(top - 1);
	}

	/**
	 * Deletes all elements in the stack.
	 */
	public void clear() {
		collection.clear();
		top = 0;
	}

}
