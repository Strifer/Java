package hr.fer.zemris.java.hw12.drawingmodel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JComponent;

/**
 * This class represents a single object that can be drawn in our painting
 * canvas. All <code>GeometricObject</code> are defined by their base color and
 * their two starting and finishing mouse coordinates.
 * 
 * @author Filip Džidić
 *
 */
public abstract class GeometricalObject extends JComponent {
	/** serial ID */
	private static final long serialVersionUID = -7906521030283288637L;

	/** coordinate of the first mouse click */
	protected Point start;
	/** coordinate of the last mouse click */
	protected Point end;
	/** base color */
	protected Color base;

	/**
	 * Constructs a new <code>GeometricalObject</code> based on user provided
	 * arguments.
	 * 
	 * @param start
	 *            the coordinate of the first mouse click
	 * @param end
	 *            the coordinate of the last mouse click
	 * @param base
	 *            the base color
	 */
	public GeometricalObject(Point start, Point end, Color base) {
		this.start = start;
		this.end = end;
		this.base = base;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	/**
	 * Getter method for the starting mouse coordinate.
	 * 
	 * @return the coordinate of the first mouse click
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * Setter method for the starting mouse coordinate.
	 * 
	 * @param start
	 *            the new starting mouse coordinate
	 */
	public void setStart(Point start) {
		this.start = start;
	}

	/**
	 * Getter method for the ending mouse coordinate.
	 * 
	 * @return the coordinate of the last mouse click
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * Setter method for the ending mouse coordinate.
	 * 
	 * @param end
	 *            the new ending mouse coordinate
	 */
	public void setEnd(Point end) {
		this.end = end;
	}

	/**
	 * Getter method for the base color of the component.
	 * 
	 * @return the base color of the component
	 */
	public Color getBase() {
		return base;
	}

	/**
	 * Setter method for the base color of the component.
	 * 
	 * @param base
	 *            the new color for this object
	 */
	public void setBase(Color base) {
		this.base = base;
	}

	/**
	 * Returns a <code>String</code> representation of this object used for
	 * debugging and quick output.
	 */
	public String toString() {
		return "";
	}

}
