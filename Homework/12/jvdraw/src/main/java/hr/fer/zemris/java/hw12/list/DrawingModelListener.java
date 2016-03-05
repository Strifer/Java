package hr.fer.zemris.java.hw12.list;

/**
 * This interface models a single listener of <code>DrawingModel</code>.
 * Listeners are informed any time something is added, removed or changed in the
 * model.
 * 
 * @author Filip Džidić
 *
 */
public interface DrawingModelListener {
	/**
	 * This method defines some work done whenever something is added to the
	 * model being observed.
	 * 
	 * @param source
	 *            the model being observed by this listener
	 * @param index0
	 *            the inclusive lower limit of the range
	 * @param index1
	 *            the inclusive upper limit of the range
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * This method defines some work done whenever something is removed from the
	 * model being observed.
	 * 
	 * @param source
	 *            the model being observed by this listener
	 * @param index0
	 *            the inclusive lower limit of the range
	 * @param index1
	 *            the inclusive upper limit of the range
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * This method defines some work done whenever something is changed in the
	 * model being observed.
	 * 
	 * @param source
	 *            the model being observed by this listener
	 * @param index0
	 *            the inclusive lower limit of the range
	 * @param index1
	 *            the inclusive upper limit of the range
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}