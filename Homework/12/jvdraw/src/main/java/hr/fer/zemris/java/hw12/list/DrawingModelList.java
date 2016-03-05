package hr.fer.zemris.java.hw12.list;

import hr.fer.zemris.java.hw12.drawingmodel.GeometricalObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a concrete implementation of <code>DrawingModel</code>. It
 * keeps track of all <code>GeometricalObject</code> found in our program.
 * 
 * @author Filip Džidić
 *
 */
public class DrawingModelList implements DrawingModel {
	/** list containing listeners registered to this object */
	private List<DrawingModelListener> list;
	/**
	 * list containing all the <code>GeometricalObject</code> found in our
	 * program
	 */
	protected List<GeometricalObject> drawingList;

	/**
	 * Constructs a new instance of this class.
	 */
	public DrawingModelList() {
		super();
		this.list = new ArrayList<>();
		this.drawingList = new ArrayList<GeometricalObject>();
	}

	/**
	 * Removes all <code>GeometricalObject</code> found in the model and informs
	 * all listeners of this change.
	 */
	public void clear() {
		this.drawingList.clear();
		for (DrawingModelListener l : list) {
			l.objectsRemoved(this, 0, drawingList.size());
		}
	}

	@Override
	public int getSize() {
		return drawingList.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return drawingList.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		drawingList.add(object);
		for (DrawingModelListener l : list) {
			l.objectsAdded(this, drawingList.size() - 1, drawingList.size() - 1);
		}

	}

	/**
	 * This method is used to inform all listeners that a change has occurred on
	 * a <code>GeometricalObject</code> defined by the index.
	 * 
	 * @param index
	 *            the index of the changed <code>GeometricalObject</code>
	 */
	public void informListeners(int index) {
		for (DrawingModelListener l : list) {
			l.objectsChanged(this, index, index);
		}
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if (list.contains(l)) {
			return;
		}
		list.add(l);

	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		list.remove(l);

	}

}
