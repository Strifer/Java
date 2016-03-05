package hr.fer.zemris.linearna;

import java.util.Arrays;

/**
 * Razred <code>Vector</code> predstavlja konkretnu implementaciju razreda
 * <code>AbstraktVector</code>.
 * <p>
 * Vektor je reprezentiran kao polje od n elemenata tipa
 * <code><b>double</b></code>. Ovaj razred opremljen je s raznim metodama za
 * stvaranje vektora te za obavljanje raznih matematičkih operacija s vektorima
 * poput zbrajanja, množenja i sl.
 * 
 * @author Filip Džidić
 *
 */
public class Vector extends AbstractVector {
	/** Polje u kojem su spremljeni elementi vektora. */
	private double[] elements;
	/** Broj elemenata u vektoru, njegova duljina. */
	private int dimension;
	/** Označava da li možemo mijenjati elemente u vektoru */
	private boolean readOnly;

	/**
	 * Defaultni konstruktor stvara promjenjiv vektor koji elemente
	 * inicijalizira direktno s referencom koje mu pošalje korisnik
	 * 
	 * @param elements
	 *            referenca na polje elemenata koje će vektor preuzeti
	 */
	public Vector(double... elements) {
		this.elements = elements;
		this.dimension = elements.length;
	}

	/**
	 * Generalni konstruktor koji omogućava korisniku kontrolu hoće li elemente
	 * direktno referencirati ili kopirati te hoće li vektor biti promjenjiv ili
	 * ne.
	 * 
	 * @param readOnly
	 *            označava hoće li vektor biti promjenjiv
	 * @param useGiven
	 *            označava hoće li se predana referenca na elemente kopirati ili
	 *            direktno uzeti
	 * @param elements
	 *            referenca na polje elemenata koje će vektor preuzeti
	 */
	public Vector(boolean readOnly, boolean useGiven, double... elements) {
		this.readOnly = readOnly;
		this.dimension = elements.length;
		this.elements = useGiven ? elements : Arrays.copyOf(elements,
				elements.length);
	}

	@Override
	public double get(int index) {
		if (index < 0 || index > this.getDimension()) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}

	@Override
	public IVector set(int index, double value) {
		if (readOnly) {
			throw new UnsupportedOperationException("Vector is read-only");
		}
		if (index < 0 || index > this.getDimension()) {
			throw new IndexOutOfBoundsException();
		}
		elements[index] = value;
		return this;
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public IVector copy() {
		return copyPart(this.dimension);
	}

	@Override
	public IVector copyPart(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		double[] elements = new double[n];
		if (n > this.getDimension()) {
			for (int i = this.getDimension() - 1; i >= 0; i--) {
				elements[i] = this.get(i);
			}
		} else {
			for (int i = 0; i < n; i++) {
				elements[i] = this.get(i);
			}
		}

		return new Vector(elements);
	}

	@Override
	public IVector newInstance(int dimension) {
		if (dimension <= 0) {
			throw new IllegalArgumentException();
		}
		return new Vector(new double[dimension]);
	}

	/**
	 * Metoda gradi vektor na osnovu njegove <code>String</code> reprezentacije.
	 * Primjer korištenja:
	 * <p>
	 * <code>IVector v = Vector.parseSimple("1 2 3 4 5 6");</code>
	 * </p>
	 * Elementi su razdvojeni razmakom. Elementi moraju biti realni brojevi.
	 * 
	 * @param text
	 *            <code>String</code> reprezentacija vektora
	 * @throws IllegalArgumentException
	 *             ako korisnik zada pogrešan format
	 * @return referenca na stvoreni vektor
	 */
	public static IVector parseSimple(String text) {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		String[] values = text.split("\\s+");
		double[] elements = new double[values.length];
		for (int i = 0; i < elements.length; i++) {
			elements[i] = Double.parseDouble(values[i]);
		}
		return new Vector(elements);
	}

}
