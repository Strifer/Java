package hr.fer.zemris.java.hw12.drawingmodel;

import hr.fer.zemris.java.hw12.list.DrawingModel;
import hr.fer.zemris.java.hw12.list.DrawingModelListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JComponent;

/**
 * <code>JVDrawingCanvas</code> represents a canvas upon which we can draw basic
 * geometric shapes.
 * <p>
 * <code>JVDrawingCanvas</code> is also a <code>DrawingModeListener</code>
 * meaning it can be informed to redraw itself any time a change in our model
 * occurs.
 * 
 * @author Filip Džidić
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/** serial ID */
	private static final long serialVersionUID = -6922138405631241350L;
	/** we record our first mouse click on the canvas here */
	private Point start;
	/** we record our current position until the second mouse click here */
	private Point end;

	/**
	 * Getter method for starting mouse position.
	 * 
	 * @return the starting mouse position
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * Getter method for the ending mouse position.
	 * 
	 * @return the ending mouse position
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * Setter method for the starting mouse position.
	 * 
	 * @param start
	 *            the new starting mouse position
	 */
	public void setStart(Point start) {
		this.start = start;
	}

	/**
	 * Setter method for the ending mouse position.
	 * 
	 * @param end
	 *            the new ending mouse position
	 */
	public void setEnd(Point end) {
		this.end = end;
	}

	@Override
	public void paintComponent(Graphics g) {
		this.setBackground(Color.white);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		if (source.getSize() == 0) {
			return;
		}
		for (int i = 0; i < source.getSize(); i++) {
			source.getObject(i).paintComponent(getGraphics());
		}
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		if (source.getSize() == 0) {
			return;
		}
		for (int i = 0; i < source.getSize(); i++) {
			source.getObject(i).paintComponent(getGraphics());
		}

	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		if (source.getSize() == 0) {
			return;
		}
		for (int i = 0; i < source.getSize(); i++) {
			source.getObject(i).paintComponent(getGraphics());
		}
	}

}
