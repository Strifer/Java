package hr.fer.zemris.linearna.tests;

import static org.junit.Assert.*;
import hr.fer.zemris.linearna.AbstractVector;
import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

import org.junit.Test;

public class AbstractVectorTest {

	@Test
	public void ModifyingAdditionTest() {
		AbstractVector v1 = new Vector(1, 2, 3, 4, 5);
		AbstractVector v2 = new Vector(-1, -2, -3, -4, -5);
		v1.add(v2);
		for (int i = v1.getDimension() - 1; i >= 0; i--) {
			assertTrue(v1.get(i) == 0.0);
		}
	}

	@Test
	public void AdditionWithoutModificationTest() {
		AbstractVector v1 = new Vector(1, 2, 3, 4, 5);
		AbstractVector v2 = new Vector(-1, -2, -3, -4, -5);
		AbstractVector v3 = (AbstractVector) v1.nAdd(v2);
		for (int i = v1.getDimension() - 1; i >= 0; i--) {
			assertFalse(v1.get(i) == 0.0);
		}
		for (int i = v3.getDimension() - 1; i >= 0; i--) {
			assertTrue(v3.get(i) == 0.0);
		}
	}

	@Test
	public void ModifyingSubtractionTest() {
		AbstractVector v1 = new Vector(1, 2, 3, 4, 5);
		v1.sub(v1);
		for (int i = v1.getDimension() - 1; i >= 0; i--) {
			assertTrue(v1.get(i) == 0.0);
		}
	}

	@Test
	public void SubtractionWithoutModificationTest() {
		AbstractVector v1 = new Vector(1, 2, 3, 4, 5);
		AbstractVector v2 = (AbstractVector) v1.nSub(v1);
		for (int i = v1.getDimension() - 1; i >= 0; i--) {
			assertFalse(v1.get(i) == 0.0);
		}
		for (int i = v2.getDimension() - 1; i >= 0; i--) {
			assertTrue(v2.get(i) == 0.0);
		}
	}

	@Test
	public void ModifyingScalarMultiplication() {
		AbstractVector v1 = new Vector(1, 1, 1, 1);
		v1.scalarMultiply(2);
		for (int i = v1.getDimension() - 1; i >= 0; i--) {
			assertTrue(v1.get(i) == 2.0);
		}
	}

	@Test
	public void MultiplicationiWithoutModificationTest() {
		AbstractVector v1 = new Vector(1, 1, 1, 1);
		AbstractVector v2 = (AbstractVector) v1.nScalarMultiply(2);
		for (int i = v1.getDimension() - 1; i >= 0; i--) {
			assertFalse(v1.get(i) == 2.0);
		}
		for (int i = v2.getDimension() - 1; i >= 0; i--) {
			assertTrue(v2.get(i) == 2.0);
		}
	}

	@Test
	public void NormTest() {
		AbstractVector v = new Vector(3, 4);
		assertTrue(v.norm() == 5.0);
	}

	@Test
	public void ModifyingNormalizationTest() {
		AbstractVector v = new Vector(3, 4, 5);
		v.normalize();
		assertTrue(Math.abs(v.norm() - 1) < 1E-15);
	}

	@Test
	public void NormalizationWithoutModificationTest() {
		AbstractVector v = new Vector(3, 4, 5);
		double norm = v.norm();
		AbstractVector v1 = (AbstractVector) v.nNormalize();
		assertTrue(v.norm() == norm);
		assertTrue(Math.abs(v1.norm() - 1) < 1E-15);
	}

	@Test
	public void ScalarProductTest() {
		AbstractVector v1 = new Vector(1, 2, 3);
		AbstractVector v2 = new Vector(6, 3, 2);
		double scalar = v1.scalarProduct(v2);
		assertTrue(scalar == 3 * 6);
	}

	@Test
	public void toStringTest() {
		AbstractVector v = new Vector(1, 2, 3);
		String text = "[1.000, 2.000, 3.000]";
		assertTrue(v.toString().equals(text));
	}

	@Test
	public void toArrayTest() {
		AbstractVector v = new Vector(1, 2, 3);
		double[] values = new double[] { 1, 2, 3 };
		assertArrayEquals(v.toArray(), values, 1E-20);
	}

	@Test
	public void VectorProductTest() {
		AbstractVector v1 = new Vector(1, 2, 3);
		AbstractVector v2 = new Vector(3, 4, -2);
		String text = "[-16.000, 11.000, -2.000]";
		assertTrue(v1.nVectorProduct(v2).toString().equals(text));
	}

	@Test
	public void CosineTest() {
		AbstractVector v1 = new Vector(1, 0);
		AbstractVector v2 = new Vector(0, 1);
		assertTrue(v1.cosine(v2) == 0.0);
		assertTrue(v1.cosine(v1) == 1.0);
	}

	@Test
	public void HomogenizeTest() {
		AbstractVector v = new Vector(5, 4, 3, 2, 1);
		String text = "[5.000, 4.000, 3.000, 2.000]";
		assertTrue(v.nFromHomogeneus().toString().equals(text));
	}

	@Test
	public void LiveToRowMatrixTest() {
		IVector v = new Vector(1, 2, 3, 4, 5);
		IMatrix m = v.toRowMatrix(true);
		assertTrue(m.getColsCount() == v.getDimension());
		m.set(0, 2, 78);
		assertTrue(v.get(2) == 78);
	}

	@Test
	public void NotLiveToRowMatrixTest() {
		IVector v = new Vector(1, 2, 3, 4, 5);
		IMatrix m = v.toRowMatrix(false);
		assertTrue(m.getColsCount() == v.getDimension());
		m.set(0, 2, 78);
		assertFalse(v.get(2) == 78);
	}

	@Test
	public void LiveToColMatrixTest() {
		IVector v = new Vector(1, 2, 3, 4, 5);
		IMatrix m = v.toColumnMatrix(true);
		assertTrue(m.getRowsCount() == v.getDimension());
		m.set(2, 0, 78);
		assertTrue(v.get(2) == 78);
	}

	@Test
	public void NotLiveToColMatrixTest() {
		IVector v = new Vector(1, 2, 3, 4, 5);
		IMatrix m = v.toColumnMatrix(false);
		assertTrue(m.getRowsCount() == v.getDimension());
		m.set(2, 0, 78);
		assertFalse(v.get(2) == 78);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void IncompatibleAdditionTest() {
		AbstractVector v1 = new Vector(1, 2, 3);
		AbstractVector v2 = new Vector(0, 2, 3, 4, 5);
		v1.add(v2);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void IncompatibleSubtractionTest() {
		AbstractVector v1 = new Vector(1, 2, 3);
		AbstractVector v2 = new Vector(0, 2, 3, 4, 5);
		v1.sub(v2);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void IncompatibleScalarProductTest() {
		AbstractVector v1 = new Vector(1, 2, 3);
		AbstractVector v2 = new Vector(0, 2, 3, 4, 5);
		v1.scalarProduct(v2);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void IncompatibleVectorProductTest() {
		AbstractVector v1 = new Vector(1, 2);
		AbstractVector v2 = new Vector(0, 2);
		v1.nVectorProduct(v2);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void OneDimensionalHomogenizationTest() {
		AbstractVector v1 = new Vector(1);
		v1.nFromHomogeneus();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void ZeroHomogenizationTest() {
		AbstractVector v1 = new Vector(1, 2, 3, 4, 5, 0);
		v1.nFromHomogeneus();
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativePrecisionTest() {
		AbstractVector v1 = new Vector(1, 2, 3, 4, 5, 6);
		System.out.println(v1.toString(-4));
	}
}
