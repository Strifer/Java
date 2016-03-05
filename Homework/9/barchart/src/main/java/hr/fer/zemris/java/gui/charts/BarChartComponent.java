package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * This class represents a swing component used for displaying charts.
 * 
 * @author Filip Džidić
 *
 */
public class BarChartComponent extends JComponent {
	/**
	 * serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * holds our chart data
	 */
	private BarChart chart;
	/** the x offset */
	private static final int xChart = 70;
	/** the y offset */
	private static final int yChart = 70;

	/**
	 * Constructs our component from a provided chart.
	 * 
	 * @param chart
	 *            the chart holding all our necessary chart data
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}

	/**
	 * Paints all the parts of our graph
	 */
	protected void paintComponent(Graphics g) {
		System.out.println("Pozvan paint komponent");
		Insets ins = getInsets();
		Dimension size = getSize();
		FontMetrics fm = g.getFontMetrics();

		Rectangle rect = new Rectangle(ins.left, ins.top, size.width - ins.left
				- ins.right, size.height - ins.top - ins.bottom);

		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}

		g.setColor(getForeground());

		String bottom = chart.getxDescription();
		String left = chart.getyDescription();
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		g2d.setTransform(at);
		int sirina = fm.stringWidth(left);
		g2d.drawString(left, -(rect.y + (rect.height - fm.getAscent()) / 2),
				rect.x + fm.getAscent());
		g2d.setTransform(defaultAt);
		sirina = fm.stringWidth(bottom);
		g.drawString(bottom, rect.x + (rect.width - sirina) / 2, rect.y
				+ (rect.height - fm.getAscent()));

		g.setFont(new Font("default", Font.BOLD, 12));
		int x = xChart;
		int y = rect.height - yChart;
		drawHorizontals(g, x, y);
		//
		// g.drawLine(xChart, rect.height - yChart, rect.width, rect.height
		// - yChart);
		// g.drawLine(x, y, x, rect.y + 70);

		drawRectangles(g, x, y, rect.x, rect.y);
		g.setColor(Color.BLACK);
		drawVerticals(g, x, y, rect.y);

	}

	/**
	 * Draws the vertical lines in our graph
	 * 
	 * @param g
	 *            used for invoking drawing methods
	 * @param x
	 *            the x coordinate of our starting point
	 * @param y
	 *            the y coordinate of our starting point
	 * @param rectY
	 *            the y coordinate of our drawing rectangle
	 */
	private void drawVerticals(Graphics g, int x, int y, int rectY) {
		int count = chart.getList().size() + 1;
		y += 5;
		int offset = (this.getWidth() - x) / (count - 1);
		for (int i = 0; i < count; i++) {
			if (i != count - 1) {
				g.drawString("" + (i + 1), x + offset / 2, y + 40);
			}
			g.drawLine(x, y, x, rectY + 30);
			x += offset - 30;
		}

	}

	/**
	 * Draws the horizontal lines in our graph
	 * 
	 * @param g
	 *            used for invoking drawing methods
	 * @param x
	 *            the x coordinate of our starting point
	 * @param y
	 *            the y coordinate of our starting point
	 */
	private void drawHorizontals(Graphics g, int x, int y) {
		int count = (chart.getMaxY() - chart.getMinY()) / 2 + 1;
		int offset = (this.getHeight() - 80) / count;
		x -= 5;
		for (int i = 0, digit = 0; i < count; i++) {
			g.drawString("" + digit, x - 30, y);
			g.drawLine(x, y, this.getWidth() - x, y);
			y -= offset;
			digit += chart.getStepY();
		}

	}

	/**
	 * Draws the vertical lines in our graph
	 * 
	 * @param g
	 *            used for invoking drawing methods
	 * @param x
	 *            the x coordinate of our starting point
	 * @param y
	 *            the y coordinate of our starting point
	 * @param rectY
	 *            the y coordinate of our drawing rectangle
	 */
	private void drawRectangles(Graphics g, int x, int y, int rectX, int rectY) {
		List<XYValue> list = chart.getList();
		int count1 = chart.getList().size() + 1;
		int count2 = (chart.getMaxY() - chart.getMinY()) / 2 + 1;
		int horizontalOffset = (this.getHeight() - 80) / count2;
		int verticalOffset = (this.getWidth() - x) / (count1 - 1);
		for (XYValue v : list) {

			Rectangle r = new Rectangle(x, y - horizontalOffset * v.getY()
					/ chart.getStepY(), verticalOffset - 30, horizontalOffset
					* v.getY() / chart.getStepY());

			g.setColor(Color.RED);
			g.fillRect(r.x, r.y, r.width, r.height);

			x += verticalOffset - 30;
		}
	}

}
