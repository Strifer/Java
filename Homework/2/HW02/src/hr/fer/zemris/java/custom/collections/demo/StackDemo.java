package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class servers as a functionality demonstration of the class
 * <code>ObjectStack</code>. It is a simple CLI postfix notation calculator with
 * 5 legal operations.
 * <p>
 * 
 * @author Filip Džidić
 *
 */
public class StackDemo {
	/**
	 * The main method accepts a single expression as a CLI argument and
	 * performs the necessary calculation.
	 * <p>
	 * Expression must follow a postfix notation also known as
	 * "reverse polish notation". There are five legal operators: <br>
	 * <code>*</code> - multiplication <br>
	 * <code>/</code> - division (division by zero not possible) <br>
	 * <code>%</code> - remainder (division by zero not possible) <br>
	 * <code>+</code> - addition <br>
	 * <code>-</code> - subtraction
	 * <p>
	 * They're all binary operators. Two operands must precede an operation to
	 * be a valid expression.
	 * 
	 * @param args
	 *            a String representation of a single expression in postfix
	 *            notation
	 */
	public static void main(String[] args) {
		if (args.length!=1) {
			System.err.println("Please provide a single expression");
			System.exit(1);
		}
		ObjectStack stacky = new ObjectStack();
		String[] split = args[0].split("\\s+");
		for (String s : split) {
			if (isDigit(s)) {
				int digit = Integer.parseInt(s);
				stacky.push(Integer.valueOf(digit));
			} else {
				operation(s, stacky);
			}
		}
		if (stacky.size() != 1) {
			System.err
					.println("Invalid expression, make sure that every operator has two preeceding numbers");
		} else
			System.out.println("Expression evualuates to " + stacky.pop());
	}

	/**
	 * Checks if a given <code>String</code> is a valid integer.
	 * 
	 * @param s
	 *            the String being checked
	 * @return true if the <code>String</code> represents a valid integer
	 */
	private static boolean isDigit(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	/**
	 * Performs the necessary operation and pushes the calculated answer to the
	 * stack.
	 * 
	 * @param s
	 *            <code>String</code> representation of the operator
	 * @param stacky
	 *            <code>String</code> the stack holding all the operands and
	 *            results
	 */
	private static void operation(String s, ObjectStack stacky) {
		int secondDigit = 0;
		int firstDigit = 0;
		try {
			secondDigit = (int) stacky.pop();
			firstDigit = (int) stacky.pop();
		} catch (EmptyStackException ex) {
			System.err
					.println("Invalid expression, make sure that every operator has two preeceding numbers");
			System.exit(1); // exit program if accessing an empty stack which
							// implies that an illegal expression was given
		}

		// checks which operation needs to be performed
		// exits program if the operation is undefined
		switch (s) {
		case "%":
			if (secondDigit == 0) {
				System.err.println("Cannot divide by zero");
				System.exit(1);
			}
			stacky.push(firstDigit % secondDigit);
			break;
		case "/":
			if (secondDigit == 0) {
				System.err.println("Cannot divide by zero");
				System.exit(1);
			}
			stacky.push(firstDigit / secondDigit);
			break;
		case "*":
			stacky.push(firstDigit * secondDigit);
			break;
		case "-":
			stacky.push(firstDigit - secondDigit);
			break;
		case "+":
			stacky.push(firstDigit + secondDigit);
			break;
		default:
			System.err.println("Invalid operator " + s);
			System.exit(1);
		}
	}
}
