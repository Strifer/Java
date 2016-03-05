package hr.fer.zemris.java.student0036474052.hw12;

/**
 * This class represents a multi-dimensional vector of real numbers. It comes
 * equipped with several methods for vector calculation such as scalar products.
 * 
 * @author Filip Džidić
 *
 */
public class SearchVector {
	/** array for storing vector elements */
	private double[] field;

	/**
	 * Constructs a new <code>SearchVector</code> with a dimension defined by
	 * the provided capacity.
	 * 
	 * @param capacity
	 *            the dimension of the new vector
	 * @throws IllegalArgumentException
	 *             if dimension is less than one
	 */
	public SearchVector(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException();
		}
		field = new double[capacity];
	}

	/**
	 * Returns the dimension of a vector.
	 * 
	 * @return the dimension of a vector
	 */
	public int getDimension() {
		return field.length;
	}

	/**
	 * This method sets the provided value in the provided index within the
	 * vector.
	 * 
	 * @param index
	 *            the location in which we're adding the new value
	 * @param value
	 *            the new value
	 * @return reference to the updated vector
	 */
	public SearchVector set(int index, double value) {
		field[index] = value;
		return this;
	}

	/**
	 * This method returns the appropriate value with the associated index.
	 * 
	 * @param index
	 *            the location in which we're retrieving the value
	 * @return the value found in that index
	 */
	public double get(int index) {
		return field[index];
	}

	/**
	 * Increments the value found with the associated index.
	 * 
	 * @param index
	 *            the value being incremented
	 * @return reference to the updated vector
	 */
	public SearchVector increment(int index) {
		field[index]++;
		return this;
	}

	/**
	 * Returns the norm of the vector.
	 * 
	 * @return the norm of the vector
	 */
	public double norm() {
		double squaredNorm = 0.0;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			squaredNorm += this.get(i) * this.get(i);
		}
		return Math.sqrt(squaredNorm);
	}

	/**
	 * Calculates and returns the scalar product between two vectors.
	 * 
	 * @param other
	 *            the second operand in scalar multiplication
	 * @return the value of the scalar product
	 * @throws IllegalArgumentException
	 *             if vectors are of differing dimensions
	 */
	public double scalarProduct(SearchVector other) {
		if (this.getDimension() != other.getDimension()) {
			throw new IllegalArgumentException();
		}
		double product = 0;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			product += this.get(i) * other.get(i);
		}
		return product;
	}

	/**
	 * Calculates the cosine between two vectors.
	 * 
	 * @param other
	 *            the second vector
	 * @return the cosine of the angle between the two vectors
	 */
	public double proximity(SearchVector other) {
		if (this.norm() == 0 || other.norm() == 0) {
			throw new IllegalArgumentException();
		}
		return scalarProduct(other) / (this.norm() * other.norm());
	}
}
