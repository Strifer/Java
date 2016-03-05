package hr.fer.zemris.java.hw12.list;

import hr.fer.zemris.java.hw12.drawingmodel.GeometricalObject;

/**
 * This interface models a list of <code>GeometricalObject</code> which can
 * inform its registered listeners of any changes happening in the list.
 * 
 * @author Filip Džidić
 *
 */
public interface DrawingModel {
	/**
	 * Returns the number of <code>GeometricalObject</code> currently in the
	 * model.
	 * 
	 * @return the number of <code>GeometricalObject</code>
	 */
	public int getSize();

	/**
	 * Retrieves the <code>GeometricalObject</code> found in the given index.
	 * 
	 * @param index
	 *            the index of the <code>GeometricalObject</code>
	 * @return the appropriate <code>GeometricalObject</code> if it exists
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds a new <code>GeometricalObject</code> to the model.
	 * 
	 * @param object
	 *            the new <code>GeometricalObject</code> being added
	 */
	public void add(GeometricalObject object);

	/**
	 * Registers a new <code>DrawingModelListener</code> to the model.
	 * 
	 * @param l
	 *            some new <code>DrawingModelListener</code>
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes a registered <code>DrawingModelListener</code> from the model.
	 * 
	 * @param l
	 *            reference to the <code>DrawingModelListener</code> being
	 *            removed
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}