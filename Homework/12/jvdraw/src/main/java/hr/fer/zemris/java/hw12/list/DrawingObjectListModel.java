package hr.fer.zemris.java.hw12.list;

import hr.fer.zemris.java.hw12.drawingmodel.GeometricalObject;

import javax.swing.AbstractListModel;

/**
 * <code>DrawingObjectListModel</code> is an adapter class of
 * <code>DrawingModelList</code> which delegates its work to its adaptee.
 * 
 * It's main purpose is to simplify the API for handling our model changes. It
 * also functions as a listener to the very model it adapts so it can be
 * utilized as a JList model.
 * 
 * @author Filip Džidić
 *
 */
public class DrawingObjectListModel extends
		AbstractListModel<GeometricalObject> implements DrawingModelListener {
	/** serial ID */
	private static final long serialVersionUID = -4816586781094255926L;
	/** adaptee through which we delegate jobs of adding and removing */
	private DrawingModelList model;

	/**
	 * Constructs a new instance given the provided draw model.
	 * 
	 * @param drawModel
	 *            the draw model being observed and being adapted
	 */
	public DrawingObjectListModel(DrawingModelList drawModel) {
		this.model = drawModel;
	}

	/**
	 * Adds a new <code>GeometricalObject</code> to our model.
	 * 
	 * @param o
	 *            the <code>GeometricalObject</code> being added
	 */
	public void addElement(GeometricalObject o) {
		model.add(o);
	}

	/**
	 * Clears all <code>GeometricalObject</code> from our model.
	 */
	public void clear() {
		model.clear();
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		this.fireIntervalAdded(source.getObject(source.getSize() - 1), index0,
				index1);

	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		this.fireIntervalRemoved(source, index0, index1);

	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		this.fireContentsChanged(source, index0, index1);

	}

}
