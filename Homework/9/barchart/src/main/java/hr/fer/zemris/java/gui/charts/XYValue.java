package hr.fer.zemris.java.gui.charts;

/**
 * This class represents a single point on our data chart
 * 
 * @author Filip Džidić
 *
 */
public class XYValue {
	/** x coordinate */
	private int x;
	/** y coordinate */
	private int y;

	/**
	 * Constructs our value
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x coordinate
	 * 
	 * @return x the x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y coordinate
	 * 
	 * @return y the y coordinate
	 */
	public int getY() {
		return y;
	}

}
