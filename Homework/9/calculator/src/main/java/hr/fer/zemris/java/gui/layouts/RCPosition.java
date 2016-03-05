package hr.fer.zemris.java.gui.layouts;

/**
 * This class represents the coordinates inside our calcualtor grid
 * 
 * @author Filip Džidić
 *
 */
public class RCPosition {
	/** defines the row of the calculator */
	private int row;
	/** defines the column of the calculator */
	private int column;

	/**
	 * Constructor constructs a position based from the provided arguments.
	 * 
	 * @param row
	 *            the row inside our calculator
	 * @param column
	 *            the column inside our calculator
	 */
	public RCPosition(int row, int column) {
		if (row < 1 || column < 1) {
			throw new IllegalArgumentException();
		}
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns the row.
	 * 
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column.
	 * 
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

}
