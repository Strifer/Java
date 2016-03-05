package hr.fer.zemris.java.tecaj.hw5;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.StudentDB;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonEqual;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonEqualWithWildCard;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonGreater;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonGreaterEqual;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonLesser;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonLesserEqual;
import hr.fer.zemris.java.tecaj.hw5.db.comparators.ComparisonNotEqual;
import hr.fer.zemris.java.tecaj.hw5.db.data.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.data.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.fields.FirstName;
import hr.fer.zemris.java.tecaj.hw5.db.fields.Jmbag;
import hr.fer.zemris.java.tecaj.hw5.db.fields.LastName;
import hr.fer.zemris.java.tecaj.hw5.db.filters.QueryFilter;

import org.junit.Assert;
import org.junit.Test;

public class StudentDatabaseTest {

	@Test
	public void ComparisonEqualTest() {
		ComparisonEqual test = new ComparisonEqual();
		String s = "oifjoweifj2o3ifj";
		Assert.assertTrue(test.satisfied(s, s));
	}
	
	@Test
	public void ComparisonNotEqualTest() {
		ComparisonNotEqual test = new ComparisonNotEqual();
		String value1 = "aehfuoeif";
		String value2 = "ioqwjeroiwejr";
		Assert.assertTrue(test.satisfied(value1, value2));
		Assert.assertFalse(test.satisfied(value2, value2));
	}
	
	@Test
	public void ComparisonEqualWithWildCardTest() {
		ComparisonEqualWithWildCard test = new ComparisonEqualWithWildCard();
		String fieldValue1 = "Anamarija";
		String fieldValue2 = "Anja";
		String fieldValue3 = "Ana";
		String value2 = "An*ja";
		
		Assert.assertTrue(test.satisfied(fieldValue1, value2));
		Assert.assertTrue(test.satisfied(fieldValue2, value2));
		Assert.assertFalse(test.satisfied(fieldValue3, value2));
	}
	
	@Test
	public void ComparisonGreaterTest() {
		ComparisonGreater test = new ComparisonGreater();
		String value1 = "Zebra";
		String value2 = "Anaconda";
		Assert.assertTrue(test.satisfied(value1, value2));
		Assert.assertFalse(test.satisfied(value2, value1));
	}
	
	@Test
	public void ComparisonGreaterEqualTest() {
		ComparisonGreaterEqual test = new ComparisonGreaterEqual();
		String value1 = "Zebra";
		String value2 = "Anaconda";
		String value3 = "Zebra";
		Assert.assertTrue(test.satisfied(value1, value2));
		Assert.assertTrue(test.satisfied(value1, value3));
		Assert.assertFalse(test.satisfied(value2, value1));
	}
	
	@Test
	public void ComparisonLesserTest() {
		ComparisonLesser test = new ComparisonLesser();
		String value1 = "Anaconda";
		String value2 = "Zebra";
		Assert.assertTrue(test.satisfied(value1, value2));
		Assert.assertFalse(test.satisfied(value2, value1));
	}
	
	@Test
	public void ComparisonLesserEqualTest() {
		ComparisonLesserEqual test = new ComparisonLesserEqual();
		String value1 = "Anaconda";
		String value2 = "Zebra";
		String value3 = "Anaconda";
		Assert.assertTrue(test.satisfied(value1, value2));
		Assert.assertTrue(test.satisfied(value1, value3));
		Assert.assertFalse(test.satisfied(value2, value1));
	}
	
	@Test
	public void StudentDataBaseConstructorTest() {
		List<String> list = new ArrayList<>();
		list.add("01\tIvić\tIva\t5");
		StudentDatabase db = new StudentDatabase(list);
		StudentRecord record = new StudentRecord("01", "Iva", "Ivić", 5);
		Assert.assertTrue(db.forJMBAG("01").equals(record));
	}
	
	@Test
	public void StudentRecordGetterTest() {
		StudentRecord record = new StudentRecord("01", "Iva", "Ivić", 5);
		Assert.assertTrue(record.getFirstName().equals("Iva"));
		Assert.assertTrue(record.getLastName().equals("Ivić"));
		Assert.assertTrue(record.getJmbag().equals("01"));
		Assert.assertTrue(record.getFinalGrade()==5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void StudentRecordExceptionTest() {
		new StudentRecord("01", "Iva", null, 5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void StudentRecordInvalidGradeExceptionTest() {
		new StudentRecord("01", "Iva", "Ivić", 10);
	}
	
	@Test
	public void StudentRecordEqualsTest() {
		StudentRecord record = new StudentRecord("01", "Iva", "Ivić", 5);
		StudentRecord record2 = new StudentRecord("03", "Iva", "Ivić", 5);
		Assert.assertTrue(record.equals(record));
		Assert.assertFalse(record.equals("haha"));
		Assert.assertFalse(record.equals(null));
		Assert.assertFalse(record.equals(record2));
	}
	
	@Test
	public void FirstNameFieldTest() {
		StudentRecord record = new StudentRecord("01", "Iva", "Ivić", 5);
		FirstName test = new FirstName();
		Assert.assertTrue(test.get(record).equals(record.getFirstName()));
	}
	
	@Test
	public void LastNameFieldTest() {
		StudentRecord record = new StudentRecord("01", "Iva", "Ivić", 5);
		LastName test = new LastName();
		Assert.assertTrue(test.get(record).equals(record.getLastName()));
	}
	
	@Test
	public void JmbagFieldTest() {
		StudentRecord record = new StudentRecord("01", "Iva", "Ivić", 5);
		Jmbag test = new Jmbag();
		Assert.assertTrue(test.get(record).equals(record.getJmbag()));
	}
	
	@Test
	public void FilterConstructorTest() {
		QueryFilter filter = new QueryFilter("firstName=\"Iva\" and lastName>=\"Ivić\"");
		StudentRecord record = new StudentRecord("01", "Iva", "Ivić", 5);
		Assert.assertTrue(filter.accepts(record));
	}
	
	@Test
	public void FilterOperatorsTest() {
		QueryFilter filter = new QueryFilter("firstName=\"*a\" and lastName<=\"Marko and Čupić\" and jmbag!=\"******\" and jmbag>\"000\" and jmbag<\"123123123\"");
		StudentRecord record = new StudentRecord("01", "Iva", "Ivić", 5);
		Assert.assertTrue(filter.accepts(record));
		Assert.assertTrue(!filter.getJmbag().isPresent());
	}
	
	@Test
	public void FilterEqualsIndexTest() {
		QueryFilter filter = new QueryFilter("jmbag=\"01\"");
		Assert.assertTrue(filter.getJmbag().isPresent());
	}
	
	@Test
	public void DatabaseFilterTest() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("01\tIva\tIvić\t5");
		arr.add("02\tIvo\tIvić\t5");
		arr.add("03\tSandra\tSandrić\t5");
		StudentDatabase db = new StudentDatabase(arr);
		QueryFilter filter = new QueryFilter("jmbag=\"*\"");
		List<StudentRecord> l = db.filter(filter);
		StudentRecord r1 = new StudentRecord("01", "Iva", "Ivić", 5);
		StudentRecord r2 = new StudentRecord("02", "Ivo", "Ivić", 5);
		StudentRecord r3 = new StudentRecord("03", "Sandra", "Sandrić", 5);
		ArrayList<StudentRecord> recs = new ArrayList<>();
		recs.add(r1);
		recs.add(r2);
		recs.add(r3);
		Assert.assertTrue(l.containsAll(recs));	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void StringLiteralTestWithoutQuotesTest() {
		new QueryFilter("firstname=123");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void StringLiteralTestWithOperatorInsideTest() {
		new QueryFilter("firstname=\"m<b\"");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void StringLiteralWithEscapedQuotesTest() {
		new QueryFilter("firstname=\"Imena\\\"l\\\"\"");
	}
	
	
	@Test
	public void HashCodeTest() {
		StudentRecord record1 = new StudentRecord("01", "Iva", "Ivić", 5);
		StudentRecord record2 = new StudentRecord("01", "Ivo", "Ivić", 4);
		Assert.assertTrue(record1.hashCode()==record2.hashCode());
	}
	
	@Test
	public void userInputWithIndexTest() throws IOException {
		System.setIn(new FileInputStream("./TestQueries/query.txt"));
		StudentDB.main(null);
		Assert.assertTrue(StudentDB.getOutput()
				.equals("Using index for record retrieval.\n"
		+"+============+========+========+===+\n"
		+ "| 0000000003 | Bosnić | Andrea | 4 |\n"
		+ "+============+========+========+===+\n"
		+ "Records selected:1\n"));
	}
	
	@Test
	public void userInputWithFilterTest() throws IOException {
		System.setIn(new FileInputStream("./TestQueries/queryFilter.txt"));
		StudentDB.main(null);
		Assert.assertTrue(StudentDB.getOutput()
				.equals("+============+========+========+===+\n"
		+ "| 0000000003 | Bosnić | Andrea | 4 |\n"
		+ "| 0000000004 | Božić  | Marin  | 5 |\n"
		+ "+============+========+========+===+\n"
		+ "Records selected:2\n"));
	}
	

}
