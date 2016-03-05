package hr.fer.zemris.java.hw12.drawingmodel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * This class represents a single empty circle which can be drawn on our canvas.
 * 
 * @author Filip Džidić
 *
 */
public class Circle extends GeometricalObject {
	/** serial ID */
	private static final long serialVersionUID = -9072349376172390570L;
	/** the circle's radius */
	private int radius;
	/** the topLeft coordinate of the circle's bounding square */
	private Point topLeft;

	/**
	 * Constructs a new Circle defined by its center, ending point and base
	 * color
	 * 
	 * @param start
	 *            the center of the circle
	 * @param end
	 *            the ending point of the circle, defines the radius
	 * @param base
	 *            the color of the circle
	 */
	public Circle(Point start, Point end, Color base) {
		super(start, end, base);
		radius = (int) end.distance(start);
	}

	@Override
	public void paintComponent(Graphics g) {
		if (this.getParent() != null) {
			g = this.getParent().getGraphics();

		}
		g.setColor(base);
		int newradius = (int) end.distance(start);
		topLeft = new Point(start.x - newradius, start.y - newradius);
		g.drawOval(topLeft.x, topLeft.y, 2 * newradius, 2 * newradius);
		radius = newradius;

	}

	/**
	 * Getter method for the radius of this circle.
	 * 
	 * @return the radius of this circle
	 */
	public int getRadius() {
		return radius;
	}

	public String toString() {
		return "Circle";
	}

	/**
	 * Setter method for the radius of this circle.
	 * 
	 * @param radius
	 *            the new radius of this circle
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

}
