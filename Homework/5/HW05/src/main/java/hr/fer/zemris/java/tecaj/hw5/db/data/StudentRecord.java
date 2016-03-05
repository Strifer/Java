package hr.fer.zemris.java.tecaj.hw5.db.data;
/**
 * This class represents a single n-tuple within our database.
 * @author Filip Džidić
 * @see hr.fer.zemris.java.tecaj.hw5.db.data.StudentDatabase
 */
public class StudentRecord {
	/** this variable represents a number which is unique to each distinct entry */
	private String jmbag;
	/** represents a student's first name */
	private String firstName;
	/** represents a student's last name */
	private String lastName;
	/** represents a student's final grade */
	private int finalGrade;
	
	/**
	 * Constructs a single n-tuple with user provided parameters.
	 * Null values are not allowed.
	 * Final grade must be between 1 and 5.
	 * @param jmbag represents a number which is unique to each distinct entry
	 * @param firstName a student's first name
	 * @param lastName a student's last name
	 * @param finalGrade a student's final grade
	 * @throws IllegalArgumentException if grade is not between 1 or 5 or if some parameter is null
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, int finalGrade) {
		if (jmbag==null || firstName==null || lastName==null) {
			throw new IllegalArgumentException("null parameters are not allowed");
		}
		if (finalGrade<1 || finalGrade>5) {
			throw new IllegalArgumentException("grade must be between 1 and 5");
		}
		this.jmbag=jmbag;
		this.firstName=firstName;
		this.lastName=lastName;
		this.finalGrade=finalGrade;
	}


	/** 
	 * Getter method for jmbag
	 * @return the jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}


	/**
	 * Getter method for firstName
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * Getter method for lastName
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @return the finalGrade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}


	/**
	 * Calculates a hashcode for storage within hash based collections
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}


	/**
	 * Checks if a provided object is equal to this one.
	 * It will be equal only if it is an instance of this class and if its jmbag class variable is the same.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	
}
