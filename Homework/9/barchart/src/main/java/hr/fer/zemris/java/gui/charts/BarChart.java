package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * This class represents a bar chart that can display data in the first
 * quadrant.
 * 
 * @author Filip Džidić
 *
 */
public class BarChart {
	/** holds the points inside our chart */
	private List<XYValue> list;
	/** x axis title */
	private String xDescription;
	/** y axis title */
	private String yDescription;
	/** starting y axis point */
	private int minY;
	/** ending y axis point */
	private int maxY;
	/** step y axis point */
	private int stepY;

	/**
	 * Constructs our chart from the given parameters
	 * 
	 * @param list
	 *            a list of points making up our chart
	 * @param xDescription
	 *            the x title
	 * @param yDescription
	 *            the y title
	 * @param minY
	 *            starting y axis point
	 * @param maxY
	 *            ending y axis point
	 * @param stepY
	 *            step on the y axis
	 */
	public BarChart(List<XYValue> list, String xDescription,
			String yDescription, int minY, int maxY, int stepY) {
		super();
		this.list = list;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = maxY;
		this.stepY = stepY;
	}

	/**
	 * Returns the list of our points
	 * 
	 * @return the list
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * Returns the x title
	 * 
	 * @return the xDescription
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Returns the y title
	 * 
	 * @return the yDescription
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Returns the minimal y value
	 * 
	 * @return the minY
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Returns the maximal y value
	 * 
	 * @return the maxY
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Returns the step value
	 * 
	 * @return the stepY
	 */
	public int getStepY() {
		return stepY;
	}

}
