package hr.fer.zemris.linearna;

/**
 * Razred <code>VectorMatrixView</code> predstavlja pogled na matricu u obliku
 * vektora.
 * <p>
 * Promjene na instancama ovog razreda utječu i na originalni objekt matrice na
 * kojeg gleda.
 * 
 * @author Filip Džidić
 *
 */
public class VectorMatrixView extends AbstractVector {
	/** Originalna matrica koju promatramo */
	private IMatrix original;
	/** Dimenzija vektora */
	private int dimension;
	/** Označava je li vektor red ili stupac u matrici */
	private boolean rowMatrix;

	/**
	 * Defaultni kontruktor stvara novi pogled na matricu u obliku vektora.
	 * Matrica mora imati jedan stupac ili jedan red.
	 * 
	 * @param original
	 *            referenca na matricu koju gledamo
	 */
	public VectorMatrixView(IMatrix original) {
		this.original = original;
		if (original.getRowsCount() == 1) {
			rowMatrix = true;
			this.dimension = original.getColsCount();
		} else {
			rowMatrix = false;
			this.dimension = original.getRowsCount();
		}
	}

	@Override
	public double get(int index) {
		return rowMatrix ? original.get(0, index) : original.get(index, 0);
	}

	@Override
	public IVector set(int index, double value) {
		if (rowMatrix) {
			original.set(0, index, value);
		} else {
			original.set(index, 0, value);
		}
		return this;
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	/**
	 * Ova metoda će napraviti kopiju našeg pogleda kao pravi vektor jednake strukture.
	 * Promjene na ovom vektoru neće više utjecati na original.
	 */
	public IVector copy() {
		IVector copy = newInstance(dimension);
		for (int i = 0; i < dimension; i++) {
			copy.set(i, this.get(i));
		}
		return copy;
	}

	@Override
	/**
	 * Vraća novi vektor koji u sebi sadrži dio prvobitnog vektora. Promjene na
	 * ovom vektoru neće više utjecati na original.
	 */
	public IVector copyPart(int n) {
		return this.copy().copyPart(n);
	}

	@Override
	public IVector newInstance(int dimension) {
		return LinAlgDefaults.defaultVector(dimension);
	}

}
