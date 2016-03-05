package hr.fer.zemris.linearna;

/**
 * Razred <code>AbstractVector</code> predstavlja implementaciju matematičkog
 * objekta vektora. <br>
 * Razred nudi brojan niz standardnih metoda za izvedbu čestih matematičkih
 * operacija s vektorima: zbrajanje, oduzimanje, skalarno množenje, vektorski
 * produkti...
 * 
 * <p>
 * Razred također nudi mogućnost prikaza vektora u oblike jednoredčane ili
 * jednostupčane matrice.
 * </p>
 * 
 * @author Filip Džidić
 *
 */
public abstract class AbstractVector implements IVector {

	@Override
	public abstract double get(int index);

	@Override
	public abstract IVector set(int index, double value);

	@Override
	public abstract int getDimension();

	@Override
	public abstract IVector copy();

	@Override
	public abstract IVector copyPart(int n);

	@Override
	public abstract IVector newInstance(int dimension);

	@Override
	public IVector add(IVector other) throws IncompatibleOperandException {
		if (other.getDimension() != this.getDimension()) {
			throw new IncompatibleOperandException();
		}
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) + other.get(i));
		}
		return this;
	}

	@Override
	public IVector nAdd(IVector other) throws IncompatibleOperandException {
		return this.copy().add(other);
	}

	@Override
	public IVector sub(IVector other) throws IncompatibleOperandException {
		if (other.getDimension() != this.getDimension()) {
			throw new IncompatibleOperandException();
		}
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) - other.get(i));
		}
		return this;
	}

	@Override
	public IVector nSub(IVector other) throws IncompatibleOperandException {
		return this.copy().sub(other);
	}

	@Override
	public IVector scalarMultiply(double byValue) {
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) * byValue);
		}
		return this;
	}

	@Override
	public IVector nScalarMultiply(double byValue) {
		return this.copy().scalarMultiply(byValue);
	}

	@Override
	public double norm() {
		double squaredNorm = 0.0;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			squaredNorm += this.get(i) * this.get(i);
		}
		return Math.sqrt(squaredNorm);
	}

	@Override
	public IVector normalize() {
		double norm = this.norm();
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) / norm);
		}
		return this;
	}

	@Override
	public IVector nNormalize() {
		return this.copy().normalize();
	}

	@Override
	public double cosine(IVector other) throws IncompatibleOperandException {
		return this.scalarProduct(other) / (this.norm() * other.norm());
	}

	@Override
	public double scalarProduct(IVector other)
			throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		double product = 0;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			product += this.get(i) * other.get(i);
		}
		return product;
	}

	@Override
	public IVector nVectorProduct(IVector other)
			throws IncompatibleOperandException {
		if (this.getDimension() != 3 || other.getDimension() != 3) {
			throw new IncompatibleOperandException();
		}

		IVector product = this.copy();
		product.set(0, this.get(1) * other.get(2) - this.get(2) * other.get(1));
		product.set(1, this.get(2) * other.get(0) - this.get(0) * other.get(2));
		product.set(2, this.get(0) * other.get(1) - this.get(1) * other.get(0));
		return product;
	}

	@Override
	public IVector nFromHomogeneus() {
		if (this.getDimension() == 1) {
			throw new UnsupportedOperationException("Vector is 1-dimensional");
		}
		double last = this.get(this.getDimension() - 1);
		if (last == 0) {
			throw new UnsupportedOperationException("Division by zero");
		}
		return this.copyPart(this.getDimension() - 1).scalarMultiply(1 / last);
	}

	@Override
	public IMatrix toRowMatrix(boolean liveView) {
		return liveView ? new MatrixVectorView(this, true)
				: new MatrixVectorView(this.copy(), true);
	}

	@Override
	public IMatrix toColumnMatrix(boolean liveView) {
		return liveView ? new MatrixVectorView(this, false)
				: new MatrixVectorView(this.copy(), false);
	}

	@Override
	public double[] toArray() {
		double[] arr = new double[this.getDimension()];
		for (int i = getDimension() - 1; i >= 0; i--) {
			arr[i] = this.get(i);
		}
		return arr;
	}

	/**
	 * Defaultna metoda koja vektor reprezentira jednim <code>String</code>
	 * objektom. Po defaultu preciznost svakog elementa je na tri decimale u
	 * prikazu.
	 */
	public String toString() {
		return toString(3);
	}

	/**
	 * Generalna metoda koja vektor reprezentira jednim <code>String</code>
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
		sb.append('[');
		String format = "%." + precision + "f";
		for (int i = 0, max = this.getDimension() - 2; i <= max; i++) {
			sb.append(String.format(format, this.get(i))).append(", ");
		}
		sb.append(String.format(format, this.get(this.getDimension() - 1)))
				.append(']');
		return sb.toString();
	}

}
