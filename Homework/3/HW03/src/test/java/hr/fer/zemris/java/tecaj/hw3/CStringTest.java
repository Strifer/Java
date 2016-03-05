package hr.fer.zemris.java.tecaj.hw3;

import org.junit.Assert;
import org.junit.Test;

public class CStringTest {
	
	@Test
	public void getLengthTest() {
		CString test = new CString("12345");
		Assert.assertTrue(test.length()==5);
	}
	@Test
	public void fromStringTest() {
		String string = "string stringy stringier";
		CString test = new CString(string);
		Assert.assertTrue("Should have same String represenation", test.toString().equals(string));
	}
	@Test
	public void charArrayTest() {
		char[] data = new char[]{'1','2','3','4','5'};
		CString test = new CString(data);
		char[] arr = test.toCharArray();
		Assert.assertArrayEquals(data, arr);
	}
	
	@Test
	public void fromDataOffsetLengthConstructorTest() {
		char[] data = new char[]{'1','2','3','4','5'};
		CString test = new CString(data, 0, 5);
		Assert.assertTrue(test.length()==5);
	}
	
	@Test
	public void fromDataOffsetLengthConstructorTest_withDefinedOffset() {
		char[] data = new char[]{'1','2','3','4','5'};
		CString test = new CString(data, 2, 3);
		Assert.assertTrue(test.length()==3);
		Assert.assertArrayEquals(new char[]{'3','4','5'}, test.toCharArray());
	}
	
	@Test
	public void fromDataOffsetLengthConstructorNullExceptionTest() {
		boolean exception_happened=false;
		try {
			new CString(null, 0, 5);
		} catch(IllegalArgumentException ex) {
			exception_happened=true;
		}
		Assert.assertTrue(exception_happened);
	}
	
	
	//this one tests all the possible exceptions that can trigger in this constructor
	@Test
	public void fromDataOffsetLengthConstructorIndexExceptionTest() {
		boolean NegativeOffsetException_happened=false;
		boolean NegativeLengthException_happened=false;
		boolean BadIndicesException_happened=false;
		char[] data = new char[]{'t','e','s','t'};
		try {
			new CString(data, -1, 3);
		} catch(StringIndexOutOfBoundsException ex) {
			 NegativeOffsetException_happened=true;
		}
		
		try {
			new CString(data, 2, -3);
		} catch(StringIndexOutOfBoundsException ex) {
			NegativeLengthException_happened=true;
		}
		
		try {
			new CString(data, 3, 6);
		} catch(StringIndexOutOfBoundsException ex) {
			BadIndicesException_happened=true;
		}
		
		Assert.assertTrue( NegativeOffsetException_happened);
		Assert.assertTrue(NegativeLengthException_happened);
		Assert.assertTrue(BadIndicesException_happened);
	}
	
	@Test
	public void fromCStringConstructorTest() {
		CString original = new CString("this is a test");
		CString test = new CString(original);
		Assert.assertTrue(test.length()==original.length());
	}
	
	@Test
	public void charAtTest() {
		CString test = new CString("123456789");
		Assert.assertTrue(test.charAt(3)=='4');
	}
	
	@Test
	public void indexOfTest() {
		CString test = new CString("0123456789");
		Assert.assertTrue(test.indexOf('0')==0);
		Assert.assertTrue(test.indexOf('4')==4);
		Assert.assertTrue(test.indexOf('9')==9);
		Assert.assertTrue(test.indexOf('ž')==-1);
		
	}
	
	@Test
	public void startsWithTest() {
		CString test = new CString("0123456789");
		Assert.assertTrue(test.startsWith(new CString("01234")));
		Assert.assertTrue(test.startsWith(new CString("")));
		Assert.assertFalse(test.startsWith(new CString("19231920491")));
	}
	
	@Test
	public void endsWithTest() {
		CString test = new CString("0123456789");
		Assert.assertTrue(test.endsWith(new CString("789")));
		Assert.assertTrue(test.endsWith(new CString("")));
		Assert.assertFalse(test.endsWith(new CString("0")));
	}
	
	@Test
	public void containsTest() {
		CString test = new CString("abas baba ba");
		Assert.assertTrue(test.contains(new CString("")));
		Assert.assertTrue(test.contains(new CString(" ")));
		Assert.assertTrue(test.contains(new CString("baba")));
		Assert.assertFalse(test.contains(new CString("abab")));
	}
	
	@Test
	public void basicSubstringTest() {
		CString test = new CString("abcdef");
		CString subs = test.substring(3, 5);
		Assert.assertTrue(subs.toString().equals("de"));
		Assert.assertTrue(subs.length()==2);
	}
	
	@Test
	public void multipleSubstringTest() {
		CString test = new CString("abcdef");
		test = test.substring(1, 6);
		Assert.assertTrue(test.toString().equals("bcdef"));
		Assert.assertTrue(test.length()==5);
		test = test.substring(1, 5);
		Assert.assertTrue(test.toString().equals("cdef"));
		Assert.assertTrue(test.length()==4);
		test = test.substring(0, 2);
		Assert.assertTrue(test.toString().equals("cd"));
		Assert.assertTrue(test.length()==2);
	}
	
	@Test
	public void leftTest() {
		CString test = new CString("abcdef");
		test=test.left(3);
		Assert.assertTrue(test.toString().equals("abc"));
		Assert.assertTrue(test.length()==3);
	}
	
	@Test
	public void rightTest() {
		CString test = new CString("abcdef");
		test=test.right(3);
		Assert.assertTrue(test.toString().equals("def"));
		Assert.assertTrue(test.length()==3);
	}
	
	@Test
	public void replaceAllCharTest() {
		CString test = new CString("šuma šumi šume");
		test=test.replaceAll('š', 's');
		Assert.assertTrue(test.toString().equals("suma sumi sume"));
	}
	
	@Test
	public void replaceAllStringTest() {
		CString test = new CString("123 + 0 = 123");
		test=test.replaceAll(new CString("123"), new CString("10000000"));
		Assert.assertTrue(test.toString().equals("10000000 + 0 = 10000000"));
	}
	@Test
	public void addTest() {
		CString t1 = new CString("170");
		CString t2 = new CString("+10");
		CString t3 = new CString("=180");
		CString t4 = t1.add(t2).add(t3);
		Assert.assertTrue(t4.toString().equals("170+10=180"));
	}
	
	@Test
	public void sequentialReplaceAllStringTest() {
		CString test = new CString("abababababab"); //6 ab
		test=test.replaceAll(new CString("ab"), new CString("baba"));
		Assert.assertTrue(test.toString().equals("babababababababababababa")); //6 baba
	}
	
	@Test
	public void positive_n_inRight() {
		boolean exception_happened=false;
		try {
			new CString("1234").right(-5);
		} catch(IllegalArgumentException ex) {
			exception_happened=true;
		}
		Assert.assertTrue(exception_happened);
		
	}
	
	@Test
	public void positive_n_inLeft() {
		boolean exception_happened=false;
		try {
			new CString("1234").left(-5);
		} catch(IllegalArgumentException ex) {
			exception_happened=true;
		}
		Assert.assertTrue(exception_happened);
		
	}
}
