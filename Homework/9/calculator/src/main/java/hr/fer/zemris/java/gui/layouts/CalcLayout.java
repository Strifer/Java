package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;

/**
 * This class defines our custom swing layout used for representing our
 * calculator GUI.
 * 
 * @author Filip Džidić
 *
 */
public class CalcLayout implements LayoutManager2 {
	/** the pixel width between each component */
	private int gap;
	/** the max amount of rows */
	private static final int ROWS = 5;
	/** the max amount of columns */
	private static final int COLS = 7;
	/** we store our coordinates in here */
	private HashMap<Component, RCPosition> table;

	/** default constructor sets the gap to 0 pixels */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructs our layout with the provided gap.
	 * 
	 * @param gap
	 *            the pixel width between each button
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
		table = new HashMap<Component, RCPosition>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		table.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
		int count = parent.getComponentCount();
		int maxHeight = 0;
		int maxWidth = 0;

		for (int i = 0; i < count; i++) {
			Component comp = parent.getComponent(i);
			Dimension d = comp.getPreferredSize();
			maxHeight = Math.max(maxHeight, d.height);
			maxWidth = Math.max(maxWidth, d.width);
		}
		Insets insets = parent.getInsets();
		dim.height = maxHeight * 5 + 4 * gap + insets.bottom + insets.top;
		dim.width = maxWidth * 7 + 6 * gap + insets.left + insets.right;
		System.out.println("Height is " + dim.height + " and width is "
				+ dim.width);
		return dim;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
		int count = parent.getComponentCount();
		int maxHeight = 0;
		int maxWidth = 0;

		for (int i = 0; i < count; i++) {
			Component comp = parent.getComponent(i);
			Dimension d = comp.getPreferredSize();
			maxHeight = Math.max(maxHeight, d.height);
			maxWidth = Math.max(maxWidth, d.width);
		}
		Insets insets = parent.getInsets();
		dim.height = maxHeight * 5 + 4 * gap + insets.bottom + insets.top;
		dim.width = maxWidth * 7 + 6 * gap + insets.left + insets.right;
		return dim;
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int maxWidth = parent.getWidth() - (insets.left + insets.right);
		int maxHeight = parent.getHeight() - (insets.top + insets.bottom);
		int count = parent.getComponentCount();

		for (int i = 0; i < count; i++) {
			Component c = parent.getComponent(i);
			if (c.isVisible()) {
				RCPosition rcp = table.get(c);
				int x, y, width, height;
				width = (maxWidth - 6 * gap) / 7;
				height = (maxHeight - 4 * gap) / 5;
				x = (rcp.getColumn() - 1) * (width + gap) + insets.left;
				y = (rcp.getRow() - 1) * (height + gap) + insets.top;
				if (rcp.getRow() == 1 && rcp.getColumn() == 1) {
					x = insets.left;
					y = insets.top;
					width = 5 * width + 4 * gap;
				}
				c.setBounds(x, y, width, height);
			}
		}

	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition rcp = null;
		if (constraints instanceof RCPosition) {
			rcp = (RCPosition) constraints;

		} else if (constraints instanceof String) {
			String[] parameters = ((String) constraints).split(",");
			if (parameters.length != 2) {
				throw new IllegalArgumentException("Invalid String format"
						+ (String) constraints);
			}

			int row = Integer.parseInt(parameters[0].trim());
			int col = Integer.parseInt(parameters[1].trim());

			rcp = new RCPosition(row, col);

		} else {
			throw new IllegalArgumentException("Invalid type");
		}

		if (rcp.getRow() > ROWS || rcp.getColumn() > COLS) {
			throw new IllegalArgumentException("Out of bounds");
		}

		table.put(comp, rcp);

	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		Dimension dim = new Dimension(0, 0);
		int count = target.getComponentCount();
		int maxHeight = 0;
		int maxWidth = 0;

		for (int i = 0; i < count; i++) {
			Component comp = target.getComponent(i);
			Dimension d = comp.getPreferredSize();
			maxHeight = Math.min(maxHeight, d.height);
			maxWidth = Math.min(maxWidth, d.width);
		}
		Insets insets = target.getInsets();
		dim.height = maxHeight * 5 + 4 * gap + insets.bottom + insets.top;
		dim.width = maxWidth * 7 + 6 * gap + insets.left + insets.right;
		return dim;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub

	}

}
