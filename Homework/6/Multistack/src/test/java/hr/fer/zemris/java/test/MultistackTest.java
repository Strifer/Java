package hr.fer.zemris.java.test;
 
import hr.fer.zemris.java.custom.scripting.demo.ObjectMultistackDemo;
import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

import java.text.DecimalFormat;
import java.util.NoSuchElementException;
 

import org.junit.Assert;
import org.junit.Test;

public class MultistackTest {
	
	@Test
	public void constructorTest() {
		ObjectMultistack om = new ObjectMultistack();
		Assert.assertTrue(om.isEmpty("hehe"));
	}
	
	@Test
	public void pushTest() {
		ObjectMultistack om = new ObjectMultistack();
		om.push("hehe", new ValueWrapper(12.123));
		om.push("hehe", new ValueWrapper("12"));
		Assert.assertFalse(om.isEmpty("hehe"));
	}
	
	@Test
	public void popTest() {
		ObjectMultistack om = new ObjectMultistack();
		ValueWrapper val = new ValueWrapper(12.123);
		om.push("hehe", val);
		ValueWrapper poppy = om.pop("hehe");
		Assert.assertTrue(val.equals(poppy));
		Assert.assertTrue(om.isEmpty("hehe"));
	}
	
	@Test
	public void peekTest() {
		ObjectMultistack om = new ObjectMultistack();
		ValueWrapper val = new ValueWrapper(12.123);
		om.push("hehe", val);
		ValueWrapper poppy = om.peek("hehe");
		Assert.assertTrue(val.equals(poppy));
		Assert.assertFalse(om.isEmpty("hehe"));
	}
	
	@Test(expected = NoSuchElementException.class)
	public void popExceptionTest() {
		ObjectMultistack om = new ObjectMultistack();
		om.pop("hehe");
	}
	
	@Test(expected = NoSuchElementException.class)
	public void peekExceptionTest() {
		ObjectMultistack om = new ObjectMultistack();
		om.peek("hehe");
	}
	
	@Test
	public void getSetTest() {
		ValueWrapper wrappy = new ValueWrapper(Integer.valueOf(123));
		Integer ints = Integer.valueOf(123);
		Assert.assertTrue(ints.equals(wrappy.getValue()));
		wrappy.setValue(Integer.valueOf(12333));
		Assert.assertFalse(ints.equals(wrappy.getValue()));
	}
	
	@Test(expected = RuntimeException.class)
	public void illegalTypeTest() {
		ValueWrapper wrappy = new ValueWrapper(new DecimalFormat());
		wrappy.getValue();
	}
	
	@Test
	public void incrementTest() {
		ValueWrapper wrappy = new ValueWrapper(Integer.valueOf(123));
		wrappy.increment(1);
		Assert.assertTrue(wrappy.getValue().equals(Integer.valueOf(123+1)));
	}
	
	@Test
	public void decrementTest() {
		ValueWrapper wrappy = new ValueWrapper(Integer.valueOf(123));
		wrappy.decrement(23);
		Assert.assertTrue(wrappy.getValue().equals(Integer.valueOf(123-23)));
	}
	
	@Test
	public void multiplyTest() {
		ValueWrapper wrappy = new ValueWrapper(Integer.valueOf(123));
		wrappy.multiply(2);
		Assert.assertTrue(wrappy.getValue().equals(Integer.valueOf(123*2)));
	}
	
	@Test
	public void divisionTest() {
		ValueWrapper wrappy = new ValueWrapper(Integer.valueOf(12));
		wrappy.divide(2);
		Assert.assertTrue(wrappy.getValue().equals(Integer.valueOf(6)));
	}
	
	@Test
	public void numCompareTest() {
		ValueWrapper wrappy = new ValueWrapper(Integer.valueOf(12));
		Assert.assertTrue(wrappy.numCompare(Integer.valueOf(-21))>0);
		wrappy.setValue("123.123");
		Assert.assertTrue(wrappy.numCompare(Double.valueOf(123.123))==0);
		wrappy.setValue(Double.valueOf(3.012E2));
		Assert.assertTrue(wrappy.numCompare(Integer.valueOf(123123123))<0);
	}
	
	@Test
	public void typeConversionTest() {
		ValueWrapper wrappy = new ValueWrapper(Integer.valueOf(20));
		wrappy.increment(null);
		Assert.assertTrue(wrappy.getValue().equals(Integer.valueOf(20)));
		wrappy.increment("1.2");
		Assert.assertTrue(wrappy.getValue() instanceof Double);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void MultiStackDemoTest() {
		ObjectMultistackDemo demo = new ObjectMultistackDemo();
		demo.main(null);
	}
}
