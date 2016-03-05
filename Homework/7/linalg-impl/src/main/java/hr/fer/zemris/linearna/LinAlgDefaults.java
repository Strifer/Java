package hr.fer.zemris.linearna;

/**
 * Razred <code>LinAlgDefaults</code> nudi niz statičkih metoda koje stvaraju
 * defaultne implementacije razreda u API-ju radi lakše upotrebe.
 * 
 * @author Filip Džidić
 *
 */
public class LinAlgDefaults {
	/**
	 * Metoda vraća praznu matricu oblika [rows]x[cols]. Svaki element matrice
	 * je inicijaliziran na nulu.
	 * 
	 * @param rows
	 *            broj redova u matrici
	 * @param cols
	 *            broj stupaca u matrici
	 * @return referencu na stvorenu matricu
	 */
	public static IMatrix defaultMatrix(int rows, int cols) {
		return new Matrix(rows, cols);
	}

	/**
	 * Metoda vraća prazan vektor duljine dimension. Svaki element vektora je
	 * inicijaliziran na nulu.
	 * 
	 * @param dimension
	 *            duljina vektora
	 * @return referenca na stvoreni vektor
	 */
	public static IVector defaultVector(int dimension) {
		return new Vector(new double[dimension]);
	}
}
