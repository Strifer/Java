package hr.fer.zemris.linearna;

/**
 * Razred <code>Matrix</code> predstavlja konkretnu implementaciju razreda
 * <code>AbstractMatrix</code>. Definiran je kao matrica u kojoj je svaki
 * element tipa <code><b>double</b></code>.
 * 
 * <p>
 * Razred je opremljen raznim metodama za stvaranje matrica te metodama za
 * obavljanje računskih operacija s matricama.
 * </p>
 * 
 * @author Filip Džidić
 *
 */
public class Matrix extends AbstractMatrix {
	/** Polje u kojem su spremljeni elementi matrice. */
	private double[][] elements;
	/** Broj redova u matrici. */
	private int rows;
	/** Broj stupaca u matrici. */
	private int cols;

	/**
	 * Stvara novu matricu sa zadanim brojem redaka i stupaca i inicijalizira
	 * svaki element na početnu vrijednost, nulu.
	 * 
	 * @param rows
	 *            broj redaka u matrici
	 * @param cols
	 *            broj stupaca u matrici
	 */
	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.elements = new double[rows][cols];
	}

	/**
	 * Stvara novu matrica sa zadanim brojem redaka i stupaca te inicijalizira
	 * sve elemente referencirajući se na predano polje array. <br>
	 * Parametar useGiven označava da li korisnik želi direktno referencirati
	 * predano polje ili ga želi iskopirati.
	 * 
	 * @param rows
	 *            broj redaka u matrici
	 * @param cols
	 *            broj stupaca u matrici
	 * @param array
	 *            polje vrijednosti koje će preuzeti elementi
	 * @param useGiven
	 *            označava hoće li se predano polje kopirati ili referencirati
	 */
	public Matrix(int rows, int cols, double[][] array, boolean useGiven) {
		if (rows != array.length || cols != array[0].length) {
			throw new IllegalArgumentException();
		}
		if (useGiven) {
			this.elements = array;
			this.rows = rows;
			this.cols = cols;
		} else {
			this.elements = new double[rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					elements[i][j] = array[i][j];
				}
			}
			this.rows = rows;
			this.cols = cols;
		}

	}

	/**
	 * Metoda gradi matricu na osnovu njezine <code>String</code>
	 * reprezentacije. Primjer korištenja:
	 * <p>
	 * <code>IMatrix m = Matrix.parseSimple("1 2 3|4 5 6|7 8 9");</code>
	 * </p>
	 * Redovi su razdvojeni znakom '|' a stupci su razdvojeni razmacima. Po
	 * definicije matrice svaki redak mora imati isti broj stupaca.
	 * 
	 * @param text
	 *            <code>String</code> reprezentacija matrice
	 * @throws IllegalArgumentException
	 *             ako korisnik zada pogrešan format
	 * @return referenca na stvorenu matricu
	 */
	public static IMatrix parseSimple(String text) {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		String[] rows = text.split("\\|+");
		int cols = rows[0].split("\\s+").length;
		double[][] elements = new double[rows.length][cols];
		for (int i = 0; i < elements.length; i++) {
			String[] values = rows[i].trim().split("\\s+");
			if (values.length != cols) {
				throw new IllegalArgumentException("Invalid parse format "
						+ text);
			}
			for (int j = 0; j < elements[0].length; j++) {
				elements[i][j] = Double.parseDouble(values[j]);
			}
		}

		return new Matrix(rows.length, cols, elements, true);
	}

	@Override
	public int getRowsCount() {
		return rows;
	}

	@Override
	public int getColsCount() {
		return cols;
	}

	@Override
	public double get(int row, int col) {
		return elements[row][col];
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		if (row < 0 || row >= rows) {
			throw new IllegalArgumentException("Index out of bounds " + row);
		}
		if (col < 0 || col >= cols) {
			throw new IllegalArgumentException("Index out of bounds " + col);
		}
		this.elements[row][col] = value;
		return this;
	}

	@Override
	public IMatrix copy() {
		return new Matrix(rows, cols, elements, false);
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return new Matrix(rows, cols);
	}

}
