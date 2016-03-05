package hr.fer.zemris.java.tecaj.hw3;

import org.junit.Assert;
import org.junit.Test;

public class ComplexNumberTest {

	@Test
	public void getterTest() {
		ComplexNumber ctest = new ComplexNumber(3,4);
		
		Assert.assertEquals("Real should be 3.0", 3.0, ctest.getReal(), 1E-5);
		Assert.assertEquals("Imaginary should be 4.0", 4.0, ctest.getImaginary(), 1E-5);
		Assert.assertEquals("Magnitude should be 5.0", 5.0, ctest.getMagnitude(), 1E-5);
		Assert.assertEquals("Angle should be 0.92729", 0.92729, ctest.getAngle(), 1E-5);
	}
	
	@Test
	public void realConstructorTest() {
		ComplexNumber ctest = ComplexNumber.fromReal(3.12456);
		Assert.assertEquals("Real should be 3.12456", 3.12456, ctest.getReal(), 1E-5);
		Assert.assertEquals("Imaginary should be 0", 0, ctest.getImaginary(), 0);
	}
	
	@Test
	public void imaginaryConstructorTest() {
		ComplexNumber ctest = ComplexNumber.fromImaginary(3.12456);
		Assert.assertEquals("Real should be 0", 0, ctest.getReal(), 0);
		Assert.assertEquals("Imaginary should be 3.12456", 3.12456, ctest.getImaginary(), 1E-9);
	}
	
	@Test
	public void reverseStringOrderTest() {
		boolean exception = false;
		try {
			ComplexNumber.parse("3i+2");
		} catch (NumberFormatException ex) {
			exception = true;
		}
		
		Assert.assertTrue("Exception occurs if format isn't followed", exception);

	}
	@Test
	public void garbageCharactersAfterComplexNumberTest() {
		boolean exception = false;
		try {
			ComplexNumber.parse("2+3iabcdef");
		} catch (NumberFormatException ex) {
			exception = true;
		}
		
		Assert.assertTrue("Only numbers operators and the letter i are allowed", exception);

	}
	@Test
	public void imaginaryUnitInTheMiddle () {
		boolean exception = false;
		try {
			ComplexNumber.parse("32+32i234");
		} catch (NumberFormatException ex) {
			exception = true;
		}
		
		Assert.assertTrue("Exception should occur if i isn't in the last index", exception);

	}
	@Test
	public void tooManyImaginaryUnits_ParseTest() {
		boolean exception = false;
		try {
			ComplexNumber.parse("2+3ii");
		} catch (NumberFormatException ex) {
			exception = true;
		}
		
		Assert.assertTrue("Exception occurs if too much is in imaginary part", exception);
	}
	
	@Test
	public void badOperatorsParsingTest() {
		boolean exception = false;
		try {
			ComplexNumber.parse("2+-3i");
		} catch (NumberFormatException ex) {
			exception = true;
		}
		
		Assert.assertTrue(exception);
	}
	
	@Test
	public void singleRealNumberParseTest() {
		boolean exception = false;
		try {
			ComplexNumber.parse("32");
		} catch (NumberFormatException ex) {
			exception = true;
		}
		
		Assert.assertFalse(exception);
	}
	
	@Test
	public void creatingFromAngleAndMagnitudeTest() {
		ComplexNumber ctest = ComplexNumber.fromMagnitudeAndAngle(5, 0.92729);
		//1E-4 precision is close enough
		Assert.assertEquals("Real should be 3.0", 3.0, ctest.getReal(), 1E-4);
		Assert.assertEquals("Imaginary should be 4.0", 4.0, ctest.getImaginary(), 1E-4);
		Assert.assertEquals("Magnitude should be 5.0", 5.0, ctest.getMagnitude(), 1E-4);
		Assert.assertEquals("Angle should be 0.92729", 0.92729, ctest.getAngle(), 1E-4);
	}
	
	@Test
	public void basicFromStringTest() {
		ComplexNumber ctest1 = ComplexNumber.parse("i");
		ComplexNumber ctest2 = ComplexNumber.parse("-11+2i");
		
		Assert.assertEquals("Real should be 0", 0, ctest1.getReal(), 1E-9);
		Assert.assertEquals("Imaginary should be 1", 1, ctest1.getImaginary(), 1E-9);
		
		Assert.assertEquals("Real should be -11", -11, ctest2.getReal(), 1E-9);
		Assert.assertEquals("Imaginary should be 2.0", 2, ctest2.getImaginary(), 1E-9);
	}
	
	@Test
	public void toStringOutputTest() {
		ComplexNumber ctest = new ComplexNumber(1,2);
		String s1 = "1 + 2i";
		String s2 = ctest.toString();
		Assert.assertTrue("s1 and s2 should have same output", s1.equals(s2));
	}
	
	@Test
	public void multiplicationTest() {
		ComplexNumber ctest = new ComplexNumber(1,2);
		ctest = ctest.mul(new ComplexNumber(1,2));
		Assert.assertEquals("Real should be -3", -3, ctest.getReal(), 1E-6);
		Assert.assertEquals("Imaginary should be 4i", 4, ctest.getImaginary(), 1E-6);
	}
	
	@Test
	public void baseRootTest() {
		ComplexNumber ctest = new ComplexNumber(4,5);
		ComplexNumber[] roots = ctest.root(10);
		Assert.assertEquals("Real should be 1.19920", 1.19920, roots[0].getReal(), 1E-4);
		Assert.assertEquals("Imaginary should be 0.10774", 0.10774, roots[0].getImaginary(), 1E-4);

	}
	
	@Test
	public void calculationWithAddDivPowRootTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).pow(3).root(2)[1];
		ComplexNumber c4 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).pow(3).root(2)[0];
		
		Assert.assertEquals("Real should be -1.6182", -1.6182, c3.getReal(), 1E-4);
		Assert.assertEquals("Imaginary should be 0.0688", 0.0688, c3.getImaginary(), 1E-4);
		

		Assert.assertEquals("Real should be 1.6182", 1.6182, c4.getReal(), 1E-4);
		Assert.assertEquals("Imaginary should be -0.0688", -0.0688, c4.getImaginary(), 1E-4);
		
	}
	
	@Test
	public void subtractionTest() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		c1 = c1.sub(ComplexNumber.fromImaginary(1));
		Assert.assertEquals("Real should be 1", 1, c1.getReal(), 1E-9);
		Assert.assertEquals("Imaginary should be 0", 0, c1.getImaginary(), 1E-9);
	}
	
	@Test
	public void negativeRootTest() {
		boolean exception = false;
		try {
			ComplexNumber.fromImaginary(32).root(-10);
		} catch(IllegalArgumentException ex) {
			exception=true;
		}
		
		Assert.assertTrue(exception);
	}
	
	@Test
	public void negativePowerTest() {
		boolean exception = false;
		try {
			ComplexNumber.fromImaginary(32).pow(-10);
		} catch(IllegalArgumentException ex) {
			exception=true;
		}
		
		Assert.assertTrue(exception);
	}
	
	@Test
	public void zeroDivisionTest() {
		boolean exception = false;
		ComplexNumber c1 = new ComplexNumber(1,1);
		try {
			c1.div(ComplexNumber.fromImaginary(0));
		} catch(IllegalArgumentException ex) {
			exception=true;
		}
		
		Assert.assertTrue(exception);
	}

}
