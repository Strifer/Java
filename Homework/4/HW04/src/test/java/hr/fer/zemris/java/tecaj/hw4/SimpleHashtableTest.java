package hr.fer.zemris.java.tecaj.hw4;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;

import org.junit.Assert;
import org.junit.Test;

public class SimpleHashtableTest {

	@Test
	public void DefaultConstructorTest() {
		SimpleHashtable test = new SimpleHashtable();
		Assert.assertTrue(test.toString().equals("[]"));
	}
	
	@Test
	public void SpecifiedConstructorTest() {
		SimpleHashtable test = new SimpleHashtable(1);
		Assert.assertTrue(test.toString().equals("[]"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void NegativeCapacityTest() {
		new SimpleHashtable(-1);
	}
	
	
	@Test
	public void generalInsertionAndRetrievalTest() {
		SimpleHashtable test = new SimpleHashtable();
		test.put("Štefica", Integer.valueOf(5));
		Assert.assertTrue((Integer)test.get("Štefica")==5);
	}
	
	@Test
	public void overflowsTest() {
		SimpleHashtable test = new SimpleHashtable(4);
		test.put("Ivana", 1);
		test.put("Jasna", 3);
		test.put("Kristina", 5);
		Assert.assertTrue((Integer)test.get("Kristina")==5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullKeyExceptionTest() {
		SimpleHashtable test = new SimpleHashtable();
		test.put(null, Integer.valueOf(5));
	}
	
	@Test
	public void removalAndChangingWithOverflowTest() {
		SimpleHashtable test = new SimpleHashtable(4);
		test.put("Ivana", 1);
		test.put("Jasna", 3);
		test.put("Kristina", 5);
		test.put("Kristina", 6);
		Assert.assertTrue((Integer)test.get("Kristina")==6);
		test.remove("Kristina");
		Assert.assertTrue(test.get("Kristina")==null);
	}
	@Test
	public void toStringTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", 1);
		test.put("2", 2);
		test.put("3", 3);
		test.put("4", 4);
		String output = "[1=1, 2=2, 3=3, 4=4]";
		Assert.assertTrue(test.toString().equals(output));
	}
	
	@Test
	public void toStringWithOverflowTest() {
		SimpleHashtable test = new SimpleHashtable(4);
		test.put("Ivana", 1);
		test.put("Jasna", 3);
		test.put("Kristina", 5);
		String output = "[Ivana=1, Jasna=3, Kristina=5]";
		Assert.assertTrue(test.toString().equals(output));
	}
	
	@Test
	public void clearTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", 1);
		test.put("2", 2);
		test.put("3", 3);
		test.put("4", 4);
		test.clear();
		Assert.assertTrue((Integer)test.get("1")==null);
		Assert.assertTrue(test.toString().equals("[]"));
		Assert.assertTrue(test.size()==0);
	}
	
	@Test
	public void retrievingNonExistantElementTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", 1);
		test.put("2", 2);
		test.put("3", 3);
		test.put("4", 4);
		Assert.assertTrue(test.get("5")==null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getNullExceptionTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", 1);
		test.put("2", 2);
		test.get(null);
	}
	
	@Test
	public void getSizeTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", 1);
		test.put("2", 2);
		test.put("3", 3);
		test.put("4", 4);
		Assert.assertTrue(test.size()==4);
	}
	
	@Test
	public void containsKeyTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", 1);
		test.put("2", 2);
		test.put("3", 3);
		test.put("4", 4);
		Assert.assertTrue(test.containsKey("1"));
		Assert.assertFalse(test.containsKey("5"));
	}
	
	@Test
	public void containsValueTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", Integer.valueOf(1));
		test.put("2", 2);
		test.put("3", 3);
		test.put("4", 4);
		Assert.assertTrue(test.containsValue(Integer.valueOf(1)));
		Assert.assertFalse(test.containsValue(Integer.valueOf(5)));
	}
	
	@Test
	public void removeTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", 1);
		test.put("2", 2);
		test.put("3", 3);
		test.put("4", 4);
		test.remove("1");
		test.remove("2");
		Assert.assertTrue(test.size()==2);
	}
	
	@Test
	public void isEmptyTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		Assert.assertTrue(test.isEmpty());
		test.put("1", 1);
		test.put("2", 2);
		test.put("3", 3);
		test.put("4", 4);
		Assert.assertFalse(test.isEmpty());
		test.clear();
		Assert.assertTrue(test.isEmpty());
	}
	
	@Test
	public void normalIteratorTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", 1);
		test.put("2", 2);
		test.put("3", 3);
		test.put("4", 4);
		int sum = 0;
		for (SimpleHashtable.TableEntry e : test) {
			sum+=(Integer)e.getValue();
		}
		
		Assert.assertTrue(sum==10);
	}
	
	@Test
	public void iterationWithOverflowTest() {
		SimpleHashtable test = new SimpleHashtable(4);
		test.put("Ivana", 1);
		test.put("Jasna", 1);
		test.put("Kristina", 1);
		for (SimpleHashtable.TableEntry e : test) {
			Assert.assertTrue((Integer)e.getValue()==1);
		}
	}
	
	@Test
	public void doubleIteratorTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", 1);
		test.put("2", 2);
		test.put("3", 3);
		String s = "";
		for (SimpleHashtable.TableEntry e1 : test) {
			for (SimpleHashtable.TableEntry e2 : test) {
				s+=(String)e1.getKey()+(String)e2.getKey();
			}
		}
		Assert.assertTrue(s.equals("111213212223313233"));
	}
	
	@Test
	public void iteratorRemoveTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", 1);
		test.put("2", 2);
		test.put("3", 3);
		Iterator<SimpleHashtable.TableEntry> iter = test.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if (pair.getKey().equals("1")) {
				iter.remove();
			}
		}
		Assert.assertTrue(test.size()==2 && test.get("1")==null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void doubleIteratorRemoveExceptionTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", 1);
		test.put("2", 2);
		test.put("3", 3);
		Iterator<SimpleHashtable.TableEntry> iter = test.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if (pair.getKey().equals("1")) {
				iter.remove();
				iter.remove();
			}
		}
	}
	@Test(expected=ConcurrentModificationException.class)
	public void removingWhileIteratingExceptionTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", 1);
		test.put("2", 2);
		test.put("3", 3);
		Iterator<SimpleHashtable.TableEntry> iter = test.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if (pair.getKey().equals("1")) {
				test.remove("1");
			}
		}
	}
	
	
	@Test(expected=NoSuchElementException.class)
	public void iteratingWithoutHasNextExceptionTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("1", 1);
		test.put("2", 2);
		test.put("3", 3);
		Iterator<SimpleHashtable.TableEntry> iter = test.iterator();
		while (true) {
			iter.next();
		}
	}
	
	
}
