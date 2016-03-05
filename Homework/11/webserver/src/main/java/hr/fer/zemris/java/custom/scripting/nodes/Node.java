package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexCollection;

/**
 * This is a base class which represents a single Node in our document tree. It
 * (and every class which inherits from it) contains methods for adding
 * children, and seeing how many children it has.
 * 
 * @author Filip Džidić
 *
 */
public abstract class Node {
	/** Node's children are stored inside here */
	private ArrayBackedIndexCollection children;
	/** This boolean tells us if we are calling the class for the first time */
	private boolean firstcall = true;

	/**
	 * This method adds a single child inside the Node's
	 * <code>ArrayBackedIndexCollection</code>. One thing to keep in mind is
	 * that a Node's array is only created when it is actually needed, on
	 * firstcall of this method.
	 * 
	 * @param child
	 *            the node we are adding as a child
	 */
	public void addChildren(Node child) {
		// if calling for the first time initilaie the array
		if (firstcall) {
			children = new ArrayBackedIndexCollection();
		}
		firstcall = false;
		children.add(child);
	}

	/**
	 * This method returns the number of children a node has. If
	 * <code>ArrayBackedIndexCollection children</code> hasn't been initialied
	 * yet it returns 0.
	 * 
	 * @return number of children inside a node
	 */
	public int numberOfChildren() {
		if (firstcall) {
			return 0;
		}
		return children.size();
	}

	/**
	 * This method returns a reference of a node's child in a given index.
	 * 
	 * @param index
	 *            signifies which place in the array we're searching for
	 * @return reference to the specified child
	 */
	public Node getChild(int index) {
		if (index < 0 || index > (numberOfChildren() - 1))
			throw new ArrayIndexOutOfBoundsException("Out of bounds!");

		return (Node) children.get(index);
	}

	/**
	 * Accepts the visitor and performs its visit operation.
	 * 
	 * @param visitor
	 *            the visitor visiting the node
	 */
	public abstract void accept(INodeVisitor visitor);
}
