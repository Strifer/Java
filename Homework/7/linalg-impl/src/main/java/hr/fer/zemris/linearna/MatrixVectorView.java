package hr.fer.zemris.linearna;

/**
 * Razred <code>MatrixTransposeView</code> predstavlja pogled na vektor u obliku
 * matrice.
 * <p>
 * Promjene na instancama ovog razreda utječu i na originalni objekt vektora na
 * kojeg gleda.
 * 
 * @author Filip Džidić
 *
 */
public class MatrixVectorView extends AbstractMatrix {
	/** Označava je li pogled na vektor kao stupac ili kao red */
	private boolean asRowMatrix;
	/** Originalni vektor na kojeg gledamo */
	private IVector original;

	/**
	 * Stvara novi pogled na originalni vektor u obliku matrice koja se sastoji
	 * od jednog stupca ili od jednog reda ovisno o parametru asRowMatrix.
	 * 
	 * @param original
	 *            referenca na originalni vektor kojeg promatramo
	 * @param asRowMatrix
	 *            true označava da vektor promatramo kao red, false označava da
	 *            vektor promatramo kao stupac
	 */
	public MatrixVectorView(IVector original, boolean asRowMatrix) {
		this.original = original;
		this.asRowMatrix = asRowMatrix;
	}

	public int getRowsCount() {
		return asRowMatrix ? 1 : original.getDimension();
	}

	public int getColsCount() {
		return !asRowMatrix ? 1 : original.getDimension();
	}

	public double get(int row, int col) {
		if (asRowMatrix) {
			if (row != 0) {
				throw new IllegalArgumentException();
			}
		} else {
			if (col != 0) {
				throw new IllegalArgumentException();
			}
		}
		return asRowMatrix ? original.get(col) : original.get(row);
	}

	public IMatrix set(int row, int col, double value) {
		if (asRowMatrix) {
			if (row != 0) {
				throw new IllegalArgumentException();
			}
			original.set(col, value);
		} else {
			if (col != 0) {
				throw new IllegalArgumentException();
			}
			original.set(row, value);
		}

		return this;
	}

	@Override
	/**
	 * Ova metoda će napraviti kopiju našeg pogleda kao pravu matricu jednake strukture.
	 * Promjene na ovoj matrici neće više utjecati na original.
	 */
	public IMatrix copy() {
		IMatrix copy = asRowMatrix ? newInstance(1, original.getDimension())
				: newInstance(original.getDimension(), 1);
		if (asRowMatrix) {
			for (int i = this.getColsCount() - 1; i >= 0; i--) {
				copy.set(0, i, original.get(i));
			}
		} else {
			for (int i = this.getRowsCount() - 1; i >= 0; i--) {
				copy.set(i, 0, original.get(i));
			}
		}
		return copy;
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return LinAlgDefaults.defaultMatrix(rows, cols);
	}

}
