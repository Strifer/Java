package hr.fer.zemris.linearna.tests;

import static org.junit.Assert.*;
import hr.fer.zemris.linearna.Matrix;

import org.junit.Test;

public class MatrixTest {
	@Test
	public void MatrixConstructorTest() {
		Matrix m = new Matrix(3, 3);
		String text = "[0.000, 0.000, 0.000]\n[0.000, 0.000, 0.000]\n[0.000, 0.000, 0.000]\n";
		assertTrue(m.toString().equals(text));
	}

	@Test(expected = IllegalArgumentException.class)
	public void ParseSimpleExceptionTest() {
		Matrix.parseSimple("1 2 3 4 5| 1 2 3|");
	}

	@Test(expected = IllegalArgumentException.class)
	public void SetExceptionTEst() {
		Matrix m = new Matrix(3, 3);
		m.set(10, 10, 12);
	}

	@Test(expected = IllegalArgumentException.class)
	public void NegativeIndexSetExceptionTEst() {
		Matrix m = new Matrix(3, 3);
		m.set(-10, -10, 12);
	}
}
