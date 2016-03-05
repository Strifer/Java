package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.webserver.RequestContext;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Utility class for <code>SmartScriptEngine</code>. It's main use is to offer
 * two maps for easy retrieval and execution of defined operations and functions
 * inside our <code>SmartScript</code> scripts.
 * 
 * @author Filip Džidić
 *
 */
public class EngineUtility {
	/**
	 * This interface models a single operation inside our
	 * <code>SmartScript</code> scripts.
	 * 
	 * @author Filip Džidić
	 *
	 */
	@FunctionalInterface
	public interface IOperation {
		/**
		 * Performs the defined binary operation using the two provided
		 * operands.
		 * 
		 * @param first
		 *            the first operand
		 * @param second
		 *            the second operand
		 */
		public void performOperation(ValueWrapper first, Object second);
	}

	/**
	 * This interface models a single <code>@function</code> inside our
	 * <code>SmartScript</code> scripts.
	 * 
	 * @author Filip Džidić
	 *
	 */
	@FunctionalInterface
	public interface IContextFunction {
		/**
		 * Performs the defined function using the values stored on the stack
		 * and updating the <code>RequestContext</code> if necessary.
		 * 
		 * @param stack
		 *            the stack holding all of our potential operands
		 * @param reqContext
		 *            the <code>RequestContext</code> holding predefined
		 *            parameters
		 */
		public void operate(Stack<Object> stack, RequestContext reqContext);
	}

	/** collection which holds all the defined functions inside our scripts */
	public static Map<String, IContextFunction> functions = buildFunctionalMap();
	/** collection which holds all the defined operations inside our scripts */
	public static Map<String, IOperation> operations = Collections
			.unmodifiableMap(buildOperationalMap());

	/**
	 * This method builds the predefined <code>IOperation</code> operations and
	 * stores them in a map for easy retrieval and execution.
	 * <p>
	 * Currently supported operations are: <br>
	 * 1)Addition adds two operands <br>
	 * 2)Subtraction subtracts two operands <br>
	 * 3)Multiplication multiplies two operands <br>
	 * 4)Division divides two operands
	 * 
	 * @return collection containing all of the defined operations inside our
	 *         scripts
	 */
	private static Map<String, IOperation> buildOperationalMap() {
		HashMap<String, IOperation> map = new HashMap<>();
		map.put("+", (x, y) -> x.increment(y));
		map.put("-", (x, y) -> x.decrement(y));
		map.put("/", (x, y) -> x.divide(y));
		map.put("*", (x, y) -> x.multiply(y));
		return map;
	}

	/**
	 * This method builds the predefined <code>IContextFunction</code> functions
	 * and stores them in a map for easy retrieval and execution.
	 * <p>
	 * Currently supported operations are: <br>
	 * 1)@sin(x) calculates the sine of x degrees <br>
	 * 2)@decfmt(x,f) rounds the x value using the provided decimal format <br>
	 * 3)@dup(x) duplicates the variable on the stack <br>
	 * 4)@swap() swaps the two top variables x and y on the stack <br>
	 * 5)@setMimeType(x) sets the <code>RequestContext</code> mimeType to x
	 * 6)@paramGet(name, default) attempts to retrieve the associated general
	 * parameter value from the <code>RequestContext</code> uses default value
	 * is it is not present<br>
	 * 7)@pparamGet(name, default) attempts to retrieve the associated
	 * persistent parameter value from the <code>RequestContext</code> uses
	 * default value if it is not present<br>
	 * 8)@pparamSet(value,name) stores value in persistent parameters map in
	 * <code>RequestContext</code><br>
	 * 9)@pparamDel(name) removes association from persistent parameters map in
	 * <code>RequestContext</code><br>
	 * 10)@tparamGet(name, default) attempts to retrieve the associated
	 * temporary parameter value from the <code>RequestContext</code> uses
	 * default value if it is not present<br>
	 * 11)tpparamSet(value,name) stores value in temporary parameters map in
	 * <code>RequestContext</code><br>
	 * 12)@tparamDel(name) removes association from temporary parameters map in
	 * <code>RequestContext</code><br>
	 * 
	 * @return collection containing all of the defined operations inside our
	 *         scripts
	 */
	private static Map<String, IContextFunction> buildFunctionalMap() {
		HashMap<String, IContextFunction> map = new HashMap<>();

		map.put("sin", (x, y) -> {
			ValueWrapper arg = new ValueWrapper(x.pop());
			arg.increment(0.0);
			arg.setValue(Math.sin(Math.toRadians((double) arg.getValue())));
			x.push(arg.getValue());
		});

		map.put("decfmt", (x, y) -> {
			DecimalFormat df = new DecimalFormat((String) x.pop());
			ValueWrapper arg = new ValueWrapper(x.pop());
			x.push(df.format((double) arg.getValue()));
		});

		map.put("dup", (x, y) -> {
			Object arg = x.pop();
			x.push(arg);
			x.push(arg);
		});

		map.put("swap", (x, y) -> {
			Object a = x.pop();
			Object b = x.pop();
			x.push(a);
			x.push(b);
		});

		map.put("setMimeType", (x, y) -> {
			y.setMimeType((String) x.pop());
		});

		map.put("paramGet", (x, y) -> {
			String defValue = x.pop().toString();
			String name = x.pop().toString();
			String value = y.getParameter(name);
			x.push(value == null ? defValue : value);
		});

		map.put("pparamGet", (x, y) -> {
			String defValue = x.pop().toString();
			String name = x.pop().toString();
			String value = y.getPersistentParameter(name);
			x.push(value == null ? defValue : value);
		});

		map.put("pparamSet", (x, y) -> {
			String name = x.pop().toString();
			String value = x.pop().toString();
			y.setPersistentParameter(name, value);
		});

		map.put("pparamDel", (x, y) -> {
			String name = x.pop().toString();
			y.removePersistentParameter(name);
		});

		map.put("tparamGet", (x, y) -> {
			String defValue = x.pop().toString();
			String name = x.pop().toString();
			String value = y.getTemporaryParameter(name);
			x.push(value == null ? defValue : value);
		});

		map.put("tparamSet", (x, y) -> {
			String name = x.pop().toString();
			String value = x.pop().toString();
			y.setTemporaryParameter(name, value);
		});

		map.put("tparamDel", (x, y) -> {
			String name = x.pop().toString();
			y.removeTemporaryParameter(name);
		});

		return map;
	}

}
