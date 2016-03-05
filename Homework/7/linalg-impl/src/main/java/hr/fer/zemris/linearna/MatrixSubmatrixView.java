package hr.fer.zemris.linearna;

/**
 * Razred <code>MatrixSubmatrixView</code> predstavlja pogled na podmatricu neke
 * glavne matrice. Podmatrica matrice je matrica koja sadrži samo određene
 * redove i stupce originalne matrice.
 * <p>
 * Promjene na instancama ovog razreda utječu i na originalni objekt matrice na
 * koju gleda.
 * 
 * @author Filip Džidić
 *
 */
public class MatrixSubmatrixView extends AbstractMatrix {
	/** Originalna matrica na koju gledamo. */
	private IMatrix original;
	/** Skup svih dohvatljivih redova */
	private int[] rowIndexes;
	/** Skup svih dohvatljivih stupaca */
	private int[] colIndexes;

	/**
	 * Stvara novi pogled na podmatricu originala koja je definirana predanim
	 * parametrima
	 * 
	 * @param original
	 *            originalna matrica na koju gledamo
	 * @param row
	 *            index reda kojeg izbacujemo
	 * @param col
	 *            index stupca kojeg izbacujemo
	 */
	public MatrixSubmatrixView(IMatrix original, int row, int col) {
		this(original, indexRange(row, original.getRowsCount()), indexRange(
				col, original.getColsCount()));
	}

	/**
	 * Privatni konstruktor koji inicijalizira članske varijable na predane
	 * vrijednosti.
	 * 
	 * @param original
	 *            referenca na matricu koju promatramo
	 * @param rowIndexes
	 *            skup svih dohvatljivih redova
	 * @param colIndexes
	 *            skup svih dohvatlivih stupaca
	 */
	private MatrixSubmatrixView(IMatrix original, int[] rowIndexes,
			int[] colIndexes) {
		this.original = original;
		this.rowIndexes = rowIndexes;
		this.colIndexes = colIndexes;
	}

	public int getRowsCount() {
		return rowIndexes.length;
	}

	public int getColsCount() {
		return colIndexes.length;
	}

	public double get(int row, int col) {
		if (row < 0 || row > rowIndexes.length - 1) {
			throw new IllegalArgumentException();
		}
		if (col < 0 || col > colIndexes.length - 1) {
			throw new IllegalArgumentException();
		}

		return original.get(rowIndexes[row], colIndexes[col]);
	}

	public IMatrix set(int row, int col, double value) {
		if (row < 0 || row > rowIndexes.length - 1) {
			throw new IllegalArgumentException();
		}
		if (col < 0 || col > colIndexes.length - 1) {
			throw new IllegalArgumentException();
		}
		original.set(rowIndexes[row], colIndexes[col], value);
		return this;
	}

	/**
	 * Stvara novu matricu koja ima identičnu strukturu kao i podmatrica na koju
	 * instanca ovog razreda gleda. Promjene u ovoj novoj matrici neće biti
	 * reflektirane na originalnu matricu koju promatramo.
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
		return original.newInstance(rows, cols);
	}

	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if (row > this.getRowsCount() - 1 || col > this.getColsCount() - 1) {
			throw new IllegalArgumentException();
		}
		if (liveView) {
			return new MatrixSubmatrixView(this.original, removeIndex(row,
					this.rowIndexes), removeIndex(col, this.colIndexes));
		}

		IMatrix mat = this.newInstance(this.getRowsCount() - 1,
				this.getColsCount() - 1);
		for (int i = mat.getRowsCount() - 1; i >= 0; i--) {
			for (int j = mat.getColsCount() - 1; j >= 0; j--) {
				if (i < row) {
					if (j < col) {
						mat.set(i, j, this.get(i, j));
					} else {
						mat.set(i, j, this.get(i, j + 1));
					}
				} else {
					if (j < col) {
						mat.set(i, j, this.get(i + 1, j));
					} else {
						mat.set(i, j, this.get(i + 1, j + 1));
					}
				}
			}
		}
		return mat;
	}

	/**
	 * Privatna metoda koja iz zadanog skupa indexa briše onaj koji predamo kao
	 * skippedIndex.
	 * 
	 * @param skippedIndex
	 *            broj koji brišemo iz našeg skupa indexa
	 * @param indices
	 *            skup indexa
	 * @return novi skup indexa koji ne sadržava skippedIndex
	 */
	private int[] removeIndex(int skippedIndex, int[] indices) {
		int[] newIndices = new int[indices.length - 1];
		for (int i = 0; i < newIndices.length; i++) {
			if (i < skippedIndex) {
				newIndices[i] = indices[i];
			} else {
				newIndices[i] = indices[i + 1];
			}
		}
		return newIndices;
	}

	/**
	 * Privatna metoda formira niz indexa preskačući predani skippedIndex.
	 * 
	 * @param skippedIndex
	 *            broj koji preskačemo u nizu
	 * @param range
	 *            broj brojeva u nizu
	 * @return niz indexa koji idu od 0 do range-1 preskačući skipped index
	 * @throws IllegalArgumentException
	 *             ako je range manji od 1 ili ako broj koji preskačemo nije u
	 *             nizu
	 */
	private static int[] indexRange(int skippedIndex, int range) {
		if (range <= 1) {
			throw new IllegalArgumentException();
		}
		if (skippedIndex < 0 || skippedIndex > range - 1) {
			throw new IllegalArgumentException();
		}
		int[] indices = new int[range - 1];
		for (int i = 0; i < indices.length; i++) {
			if (i < skippedIndex) {
				indices[i] = i;
			} else if (i >= skippedIndex) {
				indices[i] = i + 1;
			}

		}

		return indices;
	}
}
