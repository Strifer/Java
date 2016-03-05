package hr.fer.zemris.java.tecaj.hw1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Calculates and writes the area and perimeter of a given rectangle.
 * 
 * @author Filip Džidić
 * @version 1.0
 */
public class Rectangle {
	/**
	 * The main method which is called upon running the program. The user can
	 * send the rectangle's parameters as CLI arguments. The program will ask
	 * the user to provide them by default.
	 * 
	 * @param args commandline arguments
	 * @throws IOException the user has to provide two real positive integers
	 */
	public static void main(String[] args) throws IOException {
		
		double width;
		double height;
		
		
		if (args.length == 2) {
			
			width = Double.parseDouble(args[0]);
			height = Double.parseDouble(args[1]);
			printAreaPerimeter(width, height);
			
		} else if (args.length == 0) {

			width = getParameter("width");
			height = getParameter("height");
			printAreaPerimeter(width, height);
			
		} else {
			System.err.println("Invalid number of arguments");
		}
		
	}
	
	/** Gets a rectangle's parameter from user input.
	 * 
	 * @param parameter specifies which parameter is being provided
	 * @return the parameter's value
	 * @throws IOException the height and width must be positive real numbers
	 */
	static double getParameter(String parameter) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(System.in)));

		double param;

		while (true) {
			System.out.print("Please provide the " + parameter + ":");
			
			String line = reader.readLine();
			line = line.trim();
			
			if (line.isEmpty()) {
				System.out.println("Nothing was given");
				continue;
			}
			
			param = Double.parseDouble(line);
			
			if (param < 0) {
				System.out.println("The " + parameter + " cannot be negative");
				continue;
			} else {
				break;
			}
		}

		return param;
	}
	/** Prints the rectangle's area and perimeter
	 * 
	 * @param width rectangle's width
	 * @param height rectangle's height
	 */
	static void printAreaPerimeter(double width, double height) {
		System.out.println("You've specified a rectangle with width "
				+ width + " and height " + height + ". Its area is "
				+ (height * width) + " and its perimeter is "
				+ (2 * height + 2 * width) + ".");
		
	}
}
