package hr.fer.zemris.linearna.tests;

import static org.junit.Assert.*;
import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Matrix;

import org.junit.Test;

public class AbstractMatrixTest {

	@Test
	public void ModifyingAdditionTest() {
		IMatrix m1 = Matrix.parseSimple("1 2 3|4 5 6");
		IMatrix m2 = Matrix.parseSimple("-1 -2 -3|-4 -5 -6");
		m1.add(m2);
		double[][] val = new double[][] { { 0, 0, 0 }, { 0, 0, 0 } };
		double[][] expected = m1.toArray();
		assertArrayEquals(val[0], expected[0], 1E-10);
		assertArrayEquals(val[1], expected[1], 1E-10);
	}

	@Test
	public void UnmodifyingAdditionTest() {
		IMatrix m1 = Matrix.parseSimple("1 2 3|4 5 6");
		IMatrix m2 = Matrix.parseSimple("-1 -2 -3|-4 -5 -6");
		IMatrix m3 = m1.nAdd(m2);
		double[][] val1 = new double[][] { { 0, 0, 0 }, { 0, 0, 0 } };
		double[][] val2 = new double[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		double[][] expected = m1.toArray();
		assertArrayEquals(val2[0], expected[0], 1E-10);
		assertArrayEquals(val2[1], expected[1], 1E-10);

		assertArrayEquals(val1[0], m3.toArray()[0], 1E-10);
		assertArrayEquals(val1[1], m3.toArray()[1], 1E-10);
	}

	@Test
	public void ModifyingSubtractionTest() {
		IMatrix m1 = Matrix.parseSimple("1 2 3|4 5 6");
		IMatrix m2 = Matrix.parseSimple("1 2 3|4 5 6");
		m1.sub(m2);
		double[][] val = new double[][] { { 0, 0, 0 }, { 0, 0, 0 } };
		double[][] expected = m1.toArray();
		assertArrayEquals(val[0], expected[0], 1E-10);
		assertArrayEquals(val[1], expected[1], 1E-10);
	}

	@Test
	public void UnmodifyingSubtractionTest() {
		IMatrix m1 = Matrix.parseSimple("1 2 3|4 5 6");
		IMatrix m2 = Matrix.parseSimple("1 2 3|4 5 6");
		IMatrix m3 = m1.nSub(m2);
		double[][] val1 = new double[][] { { 0, 0, 0 }, { 0, 0, 0 } };
		double[][] val2 = new double[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		double[][] expected = m1.toArray();
		assertArrayEquals(val2[0], expected[0], 1E-10);
		assertArrayEquals(val2[1], expected[1], 1E-10);

		assertArrayEquals(val1[0], m3.toArray()[0], 1E-10);
		assertArrayEquals(val1[1], m3.toArray()[1], 1E-10);
	}

	@Test
	public void nMultiplyTest() {
		IMatrix m1 = Matrix.parseSimple("1 2 3|1 2 3|1 2 3");
		String text = "[6.000, 12.000, 18.000]\n[6.000, 12.000, 18.000]\n[6.000, 12.000, 18.000]\n";
		assertTrue(text.equals(m1.nMultiply(m1).toString()));
	}

	@Test
	public void DeterminantTest() {
		IMatrix m1 = Matrix.parseSimple("2 0 0|0 4 0|0 0 8");
		assertTrue(m1.determinant() == 2 * 4 * 8);
	}

	@Test
	public void InversionTest() {
		IMatrix m1 = Matrix.parseSimple("2 0 0|0 4 0|0 0 8");
		IMatrix m2 = m1.nInvert();
		String text = "[1.000, 0.000, 0.000]\n[0.000, 1.000, 0.000]\n[0.000, 0.000, 1.000]\n";
		assertTrue(m1.nMultiply(m2).toString().equals(text));
	}

	@Test
	public void ScalarMultiplyTest() {
		IMatrix m1 = Matrix.parseSimple("1 0 0|0 1 0|0 0 1");
		m1.scalarMultiply(3);
		String text = "[3.000, 0.000, 0.000]\n[0.000, 3.000, 0.000]\n[0.000, 0.000, 3.000]\n";
		assertTrue(m1.toString().equals(text));
	}

	@Test
	public void NScalarMultiplyTest() {
		IMatrix m1 = Matrix.parseSimple("1 0 0|0 1 0|0 0 1");
		m1.nScalarMultiply(3);
		String text = "[1.000, 0.000, 0.000]\n[0.000, 1.000, 0.000]\n[0.000, 0.000, 1.000]\n";
		assertTrue(m1.toString().equals(text));
	}

	@Test
	public void IdentityTest() {
		IMatrix m1 = Matrix.parseSimple("1 2 3|4 5 6|1 2 3");
		m1.makeIdentity();
		String text = "[1.000, 0.000, 0.000]\n[0.000, 1.000, 0.000]\n[0.000, 0.000, 1.000]\n";
		assertTrue(m1.toString().equals(text));
	}

	@Test
	public void LiveTransposeTest() {
		IMatrix m1 = Matrix.parseSimple("1 2|4 5| 6 7");
		IMatrix m2 = m1.nTranspose(true);
		assertTrue(m2.getRowsCount() == m1.getColsCount());
		assertTrue(m2.getColsCount() == m1.getRowsCount());
		m2.set(0, 2, 9);
		assertTrue(m1.get(2, 0) == 9);
	}

	@Test
	public void NotLiveTransposeTest() {
		IMatrix m1 = Matrix.parseSimple("1 2|4 5| 6 7");
		IMatrix m2 = m1.nTranspose(false);
		assertTrue(m2.getRowsCount() == m1.getColsCount());
		assertTrue(m2.getColsCount() == m1.getRowsCount());
		m2.set(0, 2, 9);
		assertFalse(m1.get(2, 0) == 9);
	}

	@Test
	public void LiveSubMatrixTest() {
		IMatrix m1 = Matrix.parseSimple("1 2 3|4 5 6|7 8 9");
		IMatrix m2 = m1.subMatrix(0, 0, true);

		assertTrue(m2.get(0, 0) == 5);
		m2.set(0, 0, 333);
		assertTrue(m1.get(1, 1) == 333);

	}

	@Test
	public void NotLiveSubMatrixTest() {
		IMatrix m1 = Matrix.parseSimple("1 2 3|4 5 6|7 8 9");
		IMatrix m2 = m1.subMatrix(0, 0, false);

		assertTrue(m2.get(0, 0) == 5);
		m2.set(0, 0, 333);
		assertFalse(m1.get(1, 1) == 333);
	}

	@Test
	public void LiveToVectorTest() {
		IMatrix m1 = Matrix.parseSimple("1 2 3 4 5 6");
		IVector v = m1.toVector(true);
		assertTrue(v.getDimension() == m1.getColsCount());
		v.set(0, 189);
		assertTrue(m1.get(0, 0) == 189);
	}

	@Test
	public void NotLiveToVectorTest() {
		IMatrix m1 = Matrix.parseSimple("1 2 3 4 5 6");
		IVector v = m1.toVector(false);
		assertTrue(v.getDimension() == m1.getColsCount());
		v.set(0, 189);
		assertFalse(m1.get(0, 0) == 189);
	}

}
