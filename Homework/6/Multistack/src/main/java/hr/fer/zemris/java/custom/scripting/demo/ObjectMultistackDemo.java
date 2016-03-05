package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
/**
 * This class demonstrates the functionality of our ObjectMultistack
 * @author Filip Džidić
 *
 */
public class ObjectMultistackDemo {
	/**
	 * Main method used for demonstration of the functionality of our ObjectMultistack
	 * @param args no parameters are necessary 
	 */
	public static void main(String[] args) {
		ObjectMultistack multistack = new ObjectMultistack();
		
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		
		System.out.println("Current value for year: "
								+ multistack.peek("year").getValue());
		System.out.println("Current value for price: "
								+ multistack.peek("price").getValue());
		
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		System.out.println("Current value for year: "
								+ multistack.peek("year").getValue());
		
		multistack.peek("year").setValue(
				((Integer) multistack.peek("year").getValue()).intValue() + 50);
		System.out.println("Current value for year: "
								+ multistack.peek("year").getValue());
		
		multistack.pop("year");
		System.out.println("Current value for year: "
								+ multistack.peek("year").getValue());
		
		multistack.peek("year").increment("5");
		System.out.println("Current value for year: "
								+ multistack.peek("year").getValue());
		
		multistack.peek("year").increment(5);
		System.out.println("Current value for year: "
								+ multistack.peek("year").getValue());
		
		multistack.peek("year").increment(5.0);
		System.out.println("Current value for year: "
								+ multistack.peek("year").getValue());
		
		System.out.println("Stored value equal to given: "
				+ multistack.peek("year").numCompare("2015"));
		
		System.out.println("Stored value less than given: "
				+ multistack.peek("year").numCompare("2015.1"));
		
		System.out.println("Stored value greater than given: "
				+ multistack.peek("year").numCompare("2014.9"));
		 
	}
}
