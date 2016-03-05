package hr.fer.zemris.linearna.tests;

import static org.junit.Assert.*;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;
import hr.fer.zemris.linearna.AbstractVector;

import org.junit.Test;

public class VectorTest {

	@Test
	public void VectorConstructorTest() {
		Vector v = new Vector(1, 2, 3, 4);
		String text = "[1, 2, 3, 4]";
		assertTrue(((AbstractVector) v).toString(0).equals(text));
	}

	@Test
	public void UseGivenReferenceConstructorTest() {
		double[] val = new double[] { 1, 2, 3, 4 };
		Vector v = new Vector(false, true, val);
		assertTrue(v.get(0) == 1.0);
		val[0] = 73;
		assertTrue(v.get(0) == 73.0);
	}

	@Test
	public void CopyGivenReferenceConstructorTest() {
		double[] val = new double[] { 1, 2, 3, 4 };
		Vector v = new Vector(false, false, val);
		assertTrue(v.get(0) == 1.0);
		val[0] = 73;
		assertFalse(v.get(0) == 73.0);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void ReadOnlyTest() {
		double[] val = new double[] { 1, 2, 3, 4 };
		Vector v = new Vector(true, false, val);
		v.set(0, 32.12);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void GetOutOfBoundsTest() {
		double[] val = new double[] { 1, 2, 3, 4 };
		Vector v = new Vector(false, false, val);
		assertTrue(v.get(-1) == 1.0);
		assertTrue(v.get(32) == 1.0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void SetOutOfBoundsTest() {
		double[] val = new double[] { 1, 2, 3, 4 };
		Vector v = new Vector(false, false, val);
		v.set(-3, 32);
		v.set(32, -3);
	}

	@Test
	public void getDimensionTest() {
		Vector v = new Vector(1, 2, 3);
		assertTrue(v.getDimension() == 3);
	}

	@Test
	public void copyPartTest() {
		Vector v = new Vector(1, 2);
		IVector v2 = v.copyPart(5);
		String text = "[1.000, 2.000, 0.000, 0.000, 0.000]";
		assertTrue(v2.toString().equals(text));
	}

	@Test
	public void ParsingTest() {
		IVector v = Vector.parseSimple("1 2 3 	4 	5");
		String text = "[1.000, 2.000, 3.000, 4.000, 5.000]";
		assertTrue(v.toString().equals(text));
	}

	@Test
	public void NewInstanceTest() {
		IVector v = new Vector(1, 2, 3);
		IVector vv = v.newInstance(3);
		String text = "[0.000, 0.000, 0.000]";
		assertTrue(vv.toString().equals(text));
	}

	@Test(expected = IllegalArgumentException.class)
	public void NewInstanceExceptionTest() {
		IVector v = new Vector(1, 2, 3);
		v.newInstance(-33);
	}

}
