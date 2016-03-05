package hr.fer.zemris.linearna;

/**
 * Razred <code>MatrixTransposeView</code> predstavlja pogled na transponiranu
 * matricu neke matrice. Transponirana matrice je matrica kojoj su indexi
 * stupaca i redaka zamijenjeni.
 * <p>
 * Promjene na instancama ovog razreda utječu i na originalni objekt matrice na
 * koju gleda.
 * 
 * @author Filip Džidić
 *
 */
public class MatrixTransposeView extends AbstractMatrix {

	/** Originalna matrica na koju gledamo */
	private IMatrix original;

	/**
	 * Stvara novi pogled na transponiranu matricu originala
	 * 
	 * @param original
	 *            originalna matrica na koju gledamo
	 */
	public MatrixTransposeView(IMatrix original) {
		this.original = original;
	}

	public int getRowsCount() {
		return original.getColsCount();
	}

	public int getColsCount() {
		return original.getRowsCount();
	}

	public double get(int row, int col) {
		return original.get(col, row);
	}

	public IMatrix set(int row, int col, double value) {
		original.set(col, row, value);
		return this;
	}

	/**
	 * Stvara novu matricu koja predstavlja transponiranu matricu originala.
	 * Mijenjanje ove nove matrice ne utječe na original.
	 */
	public IMatrix copy() {
		IMatrix copy = newInstance(getRowsCount(), getColsCount());
		for (int i = copy.getRowsCount() - 1; i >= 0; i--) {
			for (int j = copy.getRowsCount() - 1; j >= 0; j--) {
				copy.set(i, j, get(i, j));
			}
		}
		return copy;
	}

	public IMatrix newInstance(int rows, int cols) {
		return original.newInstance(cols, rows);
	}

	public double[][] toArray() {
		double[][] arr = new double[this.getRowsCount()][this.getColsCount()];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				arr[i][j] = this.get(i, j);
			}
		}
		return arr;
	}
}
