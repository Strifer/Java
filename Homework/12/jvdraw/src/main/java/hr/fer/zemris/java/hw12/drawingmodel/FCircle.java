package hr.fer.zemris.java.hw12.drawingmodel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * This class represents a single filled circle which can be drawn on our
 * canvas.
 * 
 * @author Filip Džidić
 *
 */
public class FCircle extends GeometricalObject {
	/** serial ID */
	private static final long serialVersionUID = 6105824488826202904L;
	/** the color which is used for filling the circle's inner area */
	private Color fill;
	/** the circle's radius */
	private int radius;
	/** the topLeft coordinate of the fcircle's bounding square */
	private Point topLeft;

	/**
	 * Constructs a new FCircle defined by its center, ending point, outer line
	 * and fill colors.
	 * 
	 * @param start
	 *            the center of the new fcircle
	 * @param end
	 *            the ending point of the new fcircle, defines the radius
	 * @param base
	 *            the color of the outer line of the fcircle
	 * @param fill
	 *            the color used to fill the circle's empty area
	 */
	public FCircle(Point start, Point end, Color base, Color fill) {
		super(start, end, base);
		this.fill = fill;
		radius = (int) end.distance(start);
	}

	/**
	 * Getter method for the filling color.
	 * 
	 * @return the fill color used for filling the FCircle.
	 */
	public Color getFill() {
		return fill;
	}

	/**
	 * Setter method for the filling color.
	 * 
	 * @param fill
	 *            the new filling color
	 */
	public void setFill(Color fill) {
		this.fill = fill;
	}

	@Override
	public void paintComponent(Graphics g) {
		this.setDoubleBuffered(true);
		if (this.getParent() != null) {
			g = this.getParent().getGraphics();
		}
		g.setColor(base);
		radius = (int) end.distance(start);
		topLeft = new Point(start.x - radius, start.y - radius);
		g.setColor(fill);
		g.fillOval(topLeft.x, topLeft.y, 2 * radius, 2 * radius);
		g.setColor(base);
		g.drawOval(topLeft.x, topLeft.y, 2 * radius, 2 * radius);
	}

	public String toString() {
		return "FCircle";
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

	/**
	 * Getter method for the radius of this circle.
	 * 
	 * @return the radius of this circle
	 */
	public int getRadius() {
		return radius;
	}

}
