package hr.fer.zemris.java.tests;

import static org.junit.Assert.*;
import hr.fer.zemris.java.complex.Complex;
import hr.fer.zemris.java.complex.ComplexPolynomial;
import hr.fer.zemris.java.complex.ComplexRootedPolynomial;

import org.junit.Test;

public class ComplexTest {

	@Test
	public void ConstantsTest() {
		Complex one = Complex.ONE;
		Complex negone = Complex.ONE_NEG;
		Complex im = Complex.IM;
		Complex negim = Complex.IM_NEG;
		assertTrue(one.toString().equals("1 + 0i"));
		assertTrue(negone.toString().equals("-1 + 0i"));
		assertTrue(im.toString().equals("0 + 1i"));
		assertTrue(negim.toString().equals("0 - 1i"));
	}

	@Test
	public void GeneralConstructorTest() {
		Complex c1 = new Complex(3, 4);
		assertTrue(c1.getReal() == 3 && c1.getImaginary() == 4);
	}

	@Test
	public void FromRealTest() {
		Complex c1 = new Complex(6, 0);
		Complex c2 = Complex.fromReal(6);
		assertTrue(c1.getReal() == c2.getReal());
		assertTrue(c1.getImaginary() == c2.getImaginary());
	}

	@Test
	public void FromImaginaryTest() {
		Complex c1 = new Complex(0, 6);
		Complex c2 = Complex.fromImaginary(6);
		assertTrue(c1.getReal() == c2.getReal());
		assertTrue(c1.getImaginary() == c2.getImaginary());
	}

	@Test
	public void FromMagnitudeAndAngleTest() {
		Complex c1 = new Complex(3, 4);
		Complex c2 = Complex.fromMagnitudeAndAngle(c1.getMagnitude(),
				c1.getAngle());
		assertTrue(Math.abs(c1.getAngle() - c2.getAngle()) < 1E-10);
		assertTrue(Math.abs(c1.getMagnitude() - c2.getMagnitude()) < 1E-10);
	}

	@Test
	public void SingleImaginaryParseTest() {
		Complex c1 = Complex.fromImaginary(1);
		Complex c2 = Complex.parse("i");
		assertTrue(c1.getReal() == c2.getReal()
				&& c1.getImaginary() == c2.getImaginary());

		c1 = Complex.fromImaginary(-1);
		c2 = Complex.parse("-i");
		assertTrue(c1.getReal() == c2.getReal()
				&& c1.getImaginary() == c2.getImaginary());

		c1 = Complex.fromImaginary(-23243.123);
		c2 = Complex.parse("-i23243.123");
		assertTrue(c1.getReal() == c2.getReal()
				&& c1.getImaginary() == c2.getImaginary());
	}

	@Test
	public void SingleRealParseTest() {
		Complex c1 = Complex.fromReal(6);
		Complex c2 = Complex.parse("6");
		assertTrue(c1.getReal() == c2.getReal()
				&& c1.getImaginary() == c2.getImaginary());

		c1 = Complex.fromReal(-32.212);
		c2 = Complex.parse("-32.212");
		assertTrue(c1.getReal() == c2.getReal()
				&& c1.getImaginary() == c2.getImaginary());

	}

	@Test
	public void GeneralParseTest() {
		Complex c1 = Complex.parse("-23.34 -i12.12345");
		Complex c2 = new Complex(-23.34, -12.12345);
		assertTrue(c1.getReal() == c2.getReal()
				&& c1.getImaginary() == c2.getImaginary());

		c1 = Complex.parse("23.344      +        i12.122");
		c2 = new Complex(23.344, 12.122);
		assertTrue(c1.getReal() == c2.getReal()
				&& c1.getImaginary() == c2.getImaginary());

		c1 = Complex.parse("31309491.123243 +   i");
		c2 = new Complex(31309491.123243, 1);
		assertTrue(c1.getReal() == c2.getReal()
				&& c1.getImaginary() == c2.getImaginary());
	}

	@Test(expected = NumberFormatException.class)
	public void TooMuchDecimalsExceptionTest() {
		Complex.parse("123.34.32 + i12");
	}

	@Test(expected = IllegalArgumentException.class)
	public void NullParseExceptionTest() {
		Complex.parse(null);
	}

	@Test(expected = NumberFormatException.class)
	public void TooMuchImaginaryUnitsExceptionTest() {
		Complex.parse("i1233432 + i12");
	}

	@Test(expected = NumberFormatException.class)
	public void NoImaginaryUnitTest() {
		Complex.parse("1233432 + 12");
	}

	@Test(expected = NumberFormatException.class)
	public void ImaginaryUnitNotAtBeginning() {
		Complex.parse("1233432 + 1i2");
	}

	@Test
	public void AdditionTest() {
		Complex c1 = new Complex(1, 2);
		Complex c2 = new Complex(2, 3);
		Complex c3 = c1.add(c2);
		assertTrue(c3.getReal() == 1 + 2);
		assertTrue(c3.getImaginary() == 2 + 3);
	}

	@Test
	public void SubtractionTest() {
		Complex c1 = new Complex(1, 2);
		Complex c2 = new Complex(2, 3);
		Complex c3 = c1.sub(c2);
		assertTrue(c3.getReal() == 1 - 2);
		assertTrue(c3.getImaginary() == 2 - 3);
	}

	@Test
	public void MultiplicationTest() {
		Complex c1 = new Complex(4, 5);
		Complex c2 = new Complex(6, 7);
		Complex c3 = c1.mul(c2);

		assertTrue(Math.abs(c3.getAngle() - (c1.getAngle() + c2.getAngle())) < 1E-10);
		assertTrue(Math.abs(c3.getMagnitude()
				- (c1.getMagnitude() * c2.getMagnitude())) < 1E-10);
	}

	@Test
	public void DivisionTest() {
		Complex c1 = new Complex(4, 5);
		Complex c2 = new Complex(6, 7);
		Complex c3 = c1.div(c2);

		assertTrue(Math.abs(c3.getAngle() - (c1.getAngle() - c2.getAngle())) < 1E-10);
		assertTrue(Math.abs(c3.getMagnitude()
				- (c1.getMagnitude() / c2.getMagnitude())) < 1E-10);
	}

	@Test
	public void ConjugateTest() {
		Complex c1 = new Complex(1, 4);
		Complex c2 = c1.conjugate();

		assertTrue(c1.getReal() == c2.getReal());
		assertTrue(c1.getImaginary() == -c2.getImaginary());
	}

	@Test
	public void NegTest() {
		Complex c1 = new Complex(1, 4);
		Complex c2 = c1.neg();

		assertTrue(c1.getReal() == -c2.getReal());
		assertTrue(c1.getImaginary() == -c2.getImaginary());
	}

	@Test
	public void PowTest() {
		Complex c1 = new Complex(1, 5);
		Complex c2 = c1.pow(5);
		assertTrue(Math.abs(c2.getMagnitude() - Math.pow(c1.getMagnitude(), 5)) < 1E-9);
	}

	@Test(expected = IllegalArgumentException.class)
	public void NegativeExponentException() {
		Complex c1 = new Complex(1, 5);
		c1.pow(-32);
	}

	@Test
	public void ToStringTest() {
		Complex c1 = new Complex(4, -5);
		assertTrue(c1.toString().equals("4 - 5i"));
	}

	@Test
	public void ComplexPolynomialConstructorTest() {
		ComplexPolynomial cp = new ComplexPolynomial(Complex.ONE, Complex.IM);
		assertTrue(cp.toString().equals("(0 + 1i)z + (1 + 0i)"));
	}

	@Test
	public void ComplexPolynomialOrderTest() {
		ComplexPolynomial cp = new ComplexPolynomial(Complex.ONE, Complex.ONE,
				Complex.ONE);
		assertTrue(cp.order() == 2);
	}

	@Test
	public void ComplexPolynomialValueTest() {
		ComplexPolynomial cp = new ComplexPolynomial(Complex.ZERO, Complex.ONE,
				Complex.ONE);
		// z^2+z
		Complex z = new Complex(3, 4);
		Complex result = z.pow(2).add(z);
		Complex polyValue = cp.apply(z);

		assertTrue(result.getReal() == polyValue.getReal());
		assertTrue(result.getImaginary() == polyValue.getImaginary());

	}

	@Test
	public void ComplexPolynomialMultiplyTest() {
		ComplexPolynomial cp = new ComplexPolynomial(Complex.ZERO, Complex.ONE,
				Complex.ONE);
		// z^2 + z

		ComplexPolynomial cp2 = new ComplexPolynomial(Complex.ZERO,
				Complex.ONE, Complex.ONE, Complex.ONE);
		// z^3 + z^2 + z
		ComplexPolynomial cp3 = cp.multiply(cp2);
		// (z^2+z)(z^3+z^2+z)

		Complex z = new Complex(1, 3);

		// real values of (cp1*cp2).apply = (cp1.apply)*(cp2.apply)
		assertTrue(cp3.apply(z).getReal() == (cp.apply(z).mul(cp2.apply(z)))
				.getReal());
		// imaginary values of (cp1*cp2).apply = (cp1.apply)*(cp2.apply)
		assertTrue(cp3.apply(z).getImaginary() == (cp.apply(z)
				.mul(cp2.apply(z))).getImaginary());
	}

	@Test
	public void DerivativeTest() {
		ComplexPolynomial cp = new ComplexPolynomial(Complex.ZERO,
				Complex.fromReal(4), Complex.fromReal(1));
		// z^2+4z

		ComplexPolynomial derivative = new ComplexPolynomial(
				Complex.fromReal(4), Complex.fromReal(2));
		// 2z+4

		Complex z = new Complex(1, 3);

		assertTrue(cp.derivative().apply(z).getReal() == derivative.apply(z)
				.getReal());

		assertTrue(cp.derivative().apply(z).getImaginary() == derivative.apply(
				z).getImaginary());
	}

	@Test
	public void ComplexPolynomialToStringTest() {
		ComplexPolynomial cp = new ComplexPolynomial(Complex.IM,
				Complex.fromReal(4), Complex.fromReal(1));
		assertTrue(cp.toString().equals("(1 + 0i)z^2 + (4 + 0i)z + (0 + 1i)"));
	}

	@Test
	public void ComplexPolynomialRootConstructorTest() {
		ComplexRootedPolynomial cpr = new ComplexRootedPolynomial(Complex.IM,
				Complex.IM_NEG);
		// (z-i)(z-(-i))
		assertTrue(cpr.toString().equals("[z - (0 + 1i)][z - (0 - 1i)]"));
	}

	@Test
	public void ComplexPolynomialRootValueTest() {
		ComplexRootedPolynomial cpr = new ComplexRootedPolynomial(Complex.IM,
				Complex.IM_NEG);
		// (z-i)(z-(-i))
		Complex z = new Complex(1, 3);
		Complex result = z.sub(Complex.IM).mul(z.sub(Complex.IM_NEG));
		assertTrue(cpr.apply(z).getReal() == result.getReal());
		assertTrue(cpr.apply(z).getImaginary() == result.getImaginary());

	}

	@Test
	public void ComplexPolynomialConversionTest() {
		ComplexRootedPolynomial cpr = new ComplexRootedPolynomial(Complex.IM,
				Complex.IM_NEG);
		ComplexPolynomial cp = new ComplexPolynomial(Complex.ONE, Complex.ZERO,
				Complex.ONE);
		// (z-i)(z+i)=z^2+1
		Complex z = new Complex(4, 5);
		assertTrue(cpr.toComplexPolynom().apply(z).getReal() == cp.apply(z)
				.getReal());
		assertTrue(cpr.toComplexPolynom().apply(z).getImaginary() == cp
				.apply(z).getImaginary());
	}

	@Test
	public void ClosestRootTest() {
		ComplexRootedPolynomial cpr = new ComplexRootedPolynomial(Complex.ONE,
				Complex.ONE_NEG);
		// index of one is 0
		// index of -one is 1
		Complex z = new Complex(0.5, 0);
		// should be closer to 1
		assertTrue(cpr.indexOfClosestRootFor(z, 0.6) == 0);
		// low threshold
		assertTrue(cpr.indexOfClosestRootFor(z, 1E-10) == -1);
	}

	@Test
	public void RootToStringTest() {
		ComplexRootedPolynomial cpr = new ComplexRootedPolynomial(Complex.ONE,
				Complex.ONE_NEG);
		assertTrue(cpr.toString().equals("[z - (1 + 0i)][z - (-1 + 0i)]"));
	}

}
