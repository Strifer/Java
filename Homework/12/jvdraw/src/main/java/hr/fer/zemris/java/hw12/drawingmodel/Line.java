package hr.fer.zemris.java.hw12.drawingmodel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * This class defines a straight bounded line which can be drawn on our canvas.
 * 
 * @author Filip Džidić
 *
 */
public class Line extends GeometricalObject {
	/** serial ID */
	private static final long serialVersionUID = 1541668537764742631L;

	/**
	 * Constructs a new Line defined by its starting point, ending point and
	 * base color
	 * 
	 * @param start
	 *            the starting point of the line
	 * @param end
	 *            the ending point of the line
	 * @param base
	 *            the color of the line
	 */
	public Line(Point start, Point end, Color base) {
		super(start, end, base);
	}

	@Override
	public void paintComponent(Graphics g) {
		if (this.getParent() != null) {
			g = this.getParent().getGraphics();
		}
		g.setColor(base);
		g.drawLine(start.x, start.y, end.x, end.y);
	}

	public String toString() {
		return "Line";
	}

}
