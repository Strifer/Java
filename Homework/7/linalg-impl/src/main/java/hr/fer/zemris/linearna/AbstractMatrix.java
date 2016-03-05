package hr.fer.zemris.linearna;

/**
 * Razred <code>AbstractMatrix</code> predstavlja implementaciju matematičkog
 * objekta matrice. <br>
 * Razred nudi brojan niz standardnih metoda za izvedbu čestih matematičkih
 * operacija u linearnoj algebri.
 * 
 * @author Filip Džidić
 *
 */
public abstract class AbstractMatrix implements IMatrix {

	/**
	 * Granična vrijednost za nule, sve brojeve manje od ove vrijednosti
	 * tretiramo kao nula u metodi nInvert().
	 */
	private final static double ZERO_TRESHOLD = 1E-15;
	/** Granična vrijednost za nule prilikom ispisa. */
	private final static double ZERO_PRINT_PRECISION = 1E-10;

	@Override
	public abstract int getRowsCount();

	@Override
	public abstract int getColsCount();

	@Override
	public abstract double get(int row, int col);

	@Override
	public abstract IMatrix set(int row, int col, double value);

	@Override
	public abstract IMatrix copy();

	@Override
	public abstract IMatrix newInstance(int rows, int cols);

	@Override
	public IMatrix nTranspose(boolean liveView) {
		if (liveView) {
			return new MatrixTransposeView(this);
		}
		IMatrix mat = this
				.newInstance(this.getColsCount(), this.getRowsCount());
		for (int i = mat.getRowsCount() - 1; i >= 0; i--) {
			for (int j = mat.getColsCount() - 1; j >= 0; j--) {
				mat.set(i, j, this.get(j, i));
			}
		}
		return mat;

	}

	@Override
	public IMatrix add(IMatrix other) {
		if (this.getRowsCount() != other.getRowsCount()
				|| this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				this.set(i, j, this.get(i, j) + other.get(i, j));
			}
		}
		return this;
	}

	@Override
	public IMatrix nAdd(IMatrix other) {
		return this.copy().add(other);
	}

	@Override
	public IMatrix sub(IMatrix other) {
		if (this.getRowsCount() != other.getRowsCount()
				|| this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				this.set(i, j, this.get(i, j) - other.get(i, j));
			}
		}
		return this;
	}

	@Override
	public IMatrix nSub(IMatrix other) {
		return this.copy().sub(other);
	}

	@Override
	public IMatrix nMultiply(IMatrix other) {
		if (this.getColsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException();
		}
		int rows = this.getRowsCount();
		int cols = other.getColsCount();
		IMatrix multi = this.newInstance(rows, cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				multi.set(i, j, multiplyRows(i, j, this, other));
			}
		}
		return multi;

	}

	/**
	 * Pomoćna metoda koju koristimo pri množenju matrica. Množi red lijevog
	 * koeficijenta sa stupcem desnog koeficijenta. Suma svih umnožaka čini
	 * jedan element produkta matrica.
	 * 
	 * @param row
	 *            red lijevog koeficijenta u produktu
	 * @param col
	 *            stupac desnog koeficijenta u produktu
	 * @param left
	 *            lijevi koeficijent u produktu (matrica nad kojoj pozivamo
	 *            metodu nMultiply)
	 * @param right
	 *            desni koeficijent u produktu (argument koji predajemo metodi
	 *            nMultiply)
	 * @return vrijednost jednog elementa u produktu
	 */
	private double multiplyRows(int row, int col, IMatrix left, IMatrix right) {
		double sum = 0;
		for (int i = left.getRowsCount() - 1; i >= 0; i--) {
			sum += left.get(row, i) * right.get(i, col);
		}
		return sum;
	}

	@Override
	public double determinant() throws IncompatibleOperandException {
		if (this.getColsCount() != this.getRowsCount()) {
			throw new IncompatibleOperandException("Not a square Matrix");
		}
		if (this.getColsCount() == 1) {
			return this.get(0, 0);
		}
		double determinant = 0.0;
		for (int i = 0, max = this.getColsCount(); i < max; i++) {
			if (i % 2 == 0) {
				determinant += this.get(0, i)
						* this.subMatrix(0, i, true).determinant();
			} else {
				determinant -= this.get(0, i)
						* this.subMatrix(0, i, true).determinant();
			}
		}
		return determinant;
	}

	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if (row > this.getRowsCount() - 1 || col > this.getColsCount() - 1) {
			throw new IllegalArgumentException();
		}
		if (liveView) {
			return new MatrixSubmatrixView(this, row, col);
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
	 * Metoda računa inverz kvadratne matrice koristeći <a href
	 * ="http://en.wikipedia.org/wiki/Invertible_matrix#Analytic_solution">
	 * algoritam kofaktora.</a> Da bi metoda imala inverz ona mora biti
	 * kvadratna i determinanta joj mora biti različita od nule.
	 * 
	 * @throws IncompatibleOperandException
	 *             ako je matrica singularna
	 * @return referenca na izračunati inverz matrice
	 */
	public IMatrix nInvert() {
		double det = this.determinant();
		if (Math.abs(det) < ZERO_TRESHOLD) {
			throw new IncompatibleOperandException("Matrix is singular");
		}
		IMatrix adjugate = this.newInstance(this.getRowsCount(),
				this.getColsCount());
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				adjugate.set(i, j,
						Math.pow(-1, i + j)
								* this.subMatrix(i, j, true).determinant());
			}
		}
		return adjugate.nTranspose(false).scalarMultiply(1 / det);
	}

	@Override
	public double[][] toArray() {
		double[][] arr = new double[this.getRowsCount()][this.getColsCount()];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				arr[i][j] = this.get(i, j);
			}
		}
		return arr;
	}

	@Override
	public IVector toVector(boolean liveView) {
		if (this.getColsCount() > 1 && this.getRowsCount() > 1) {
			throw new IncompatibleOperandException(
					"This matrix is not a vector");
		}
		return liveView ? new VectorMatrixView(this) : new VectorMatrixView(
				this.copy());
	}

	@Override
	public IMatrix nScalarMultiply(double value) {
		return this.copy().scalarMultiply(value);
	}

	@Override
	public IMatrix scalarMultiply(double value) {
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				this.set(i, j, value * this.get(i, j));
			}
		}
		return this;
	}

	@Override
	public IMatrix makeIdentity() {
		if (this.getRowsCount() != this.getColsCount()) {
			throw new IncompatibleOperandException();
		}

		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				if (i == j) {
					this.set(i, j, 1);
				} else {
					this.set(i, j, 0);
				}
			}
		}
		return this;

	}

	/**
	 * Defaultna metoda koja matricu reprezentira jednim <code>String</code>
	 * objektom. Po defaultu preciznost svakog elementa je na tri decimale u
	 * prikazu.
	 */
	public String toString() {
		return toString(3);
	}

	/**
	 * Generalna metoda koja matricu reprezentira jednim <code>String</code>
	 * objektom. Korisnik određuje željenu preciznost argumentom precision.
	 * 
	 * @param precision
	 *            broj decimala u prikazu
	 * @return matrica prikazana kao <code>String</code>
	 * @throws IllegalArgumentException
	 *             ako je preciznost negativan broj
	 */
	public String toString(int precision) {
		if (precision < 0) {
			throw new IllegalArgumentException();
		}
		StringBuilder sb = new StringBuilder();

		String format = "%." + precision + "f";
		for (int i = 0, rowCount = this.getRowsCount(), colsCount = this
				.getColsCount(); i < rowCount; i++) {
			sb.append('[');
			for (int j = 0; j <= colsCount - 2; j++) {
				if (Math.abs(this.get(i, j)) < ZERO_PRINT_PRECISION) {
					sb.append(String.format(format, 0.0)).append(", ");
				} else {
					sb.append(String.format(format, this.get(i, j))).append(
							", ");
				}
			}
			if (Math.abs(this.get(i, colsCount - 1)) < ZERO_PRINT_PRECISION) {
				sb.append(String.format(format, 0.0)).append("]\n");
			} else {
				sb.append(String.format(format, this.get(i, colsCount - 1)))
						.append("]\n");
			}

		}
		return sb.toString();
	}

}
