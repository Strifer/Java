package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.functions.ArcCos;
import hr.fer.zemris.java.gui.calc.functions.ArcCotan;
import hr.fer.zemris.java.gui.calc.functions.ArcSin;
import hr.fer.zemris.java.gui.calc.functions.ArcTan;
import hr.fer.zemris.java.gui.calc.functions.Cosine;
import hr.fer.zemris.java.gui.calc.functions.Ctg;
import hr.fer.zemris.java.gui.calc.functions.Exp;
import hr.fer.zemris.java.gui.calc.functions.Inverse;
import hr.fer.zemris.java.gui.calc.functions.Ln;
import hr.fer.zemris.java.gui.calc.functions.Log;
import hr.fer.zemris.java.gui.calc.functions.Power10;
import hr.fer.zemris.java.gui.calc.functions.Sine;
import hr.fer.zemris.java.gui.calc.functions.Tan;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.operators.Add;
import hr.fer.zemris.java.gui.operators.Divide;
import hr.fer.zemris.java.gui.operators.Multiply;
import hr.fer.zemris.java.gui.operators.Pow;
import hr.fer.zemris.java.gui.operators.Root;
import hr.fer.zemris.java.gui.operators.Sub;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class represents a functional calculator with a GUI. It comes equipped
 * with all the standard mathematical operations as well as an option to save
 * parameters on a stack using push and pop buttons.
 * 
 * @author Filip Džidić
 *
 */
public class Calculator {
	/** our main output label */
	private static JLabel label = new JLabel("");
	/** holds all the defined functions in our calculator */
	private static HashMap<String, IFunction> functions = new HashMap<>();
	/** holds all the defined binary operations in our calculator */
	private static HashMap<String, IOperator> operators = new HashMap<>();
	/** holds the first operand */
	private static Stack<String> firstOperand = new Stack<>();
	/** holds the storage stack */
	private static Stack<String> stack = new Stack<>();
	/** records the operation that has to be performed */
	private static String operation = null;
	/** records if an operation has been performed */
	private static boolean operationHappened;

	/**
	 * The main method creates and starts our GUI application.
	 * 
	 * @param args
	 *            no args should be provided
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	/**
	 * Creates all the necessary GUI components
	 */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("calc");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p = new JPanel(new CalcLayout(3));
		label.setBackground(Color.gray);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		p.add(label, "1,1");

		addDigits(p);
		addOperators(p);
		addSpecializedButtons(p);
		addFunctions(p);
		addStackFunctions(p);

		// Create and set up the content pane.
		JComponent newContentPane = p;
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * Adds the stack functions, push and pop.
	 * 
	 * @param p
	 *            the panel we're adding the buttons on
	 */
	private static void addStackFunctions(JPanel p) {
		ActionListener pushListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = label.getText();
				if (text.contains("empty")) {
					label.setText("");
				}
				if (!text.equals("")) {
					stack.push(text);
				}
			}

		};

		ActionListener popListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stack.isEmpty()) {
					label.setText("The stack is empty. Please reset the calculator");
					operationHappened = true;
				} else {
					label.setText(stack.pop());
				}

			}

		};

		JButton pop = new JButton("pop");
		JButton push = new JButton("push");
		pop.addActionListener(popListener);
		push.addActionListener(pushListener);
		p.add(push, "3,7");
		p.add(pop, "4,7");

	}

	/**
	 * Adds the unary mathematical functions.
	 * 
	 * @param p
	 *            the panel we're adding the buttons on
	 */
	private static void addFunctions(JPanel p) {
		fillFunctions(functions);
		ActionListener operatorListener = createOperatorListener();
		ActionListener functionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = label.getText();
				if (text.contains("empty")) {
					label.setText("");
				}
				String name = ((JButton) e.getSource()).getText();
				if (!text.equals("")) {
					double x = Double.parseDouble(text);
					IFunction func = functions.get(name);
					double ans = func.calculate(x);
					operationHappened = true;
					label.setText(Double.valueOf(ans).toString());
				}
			}

		};
		String functionNames = ("1/x sin log cos ln tan ctg x^n");
		String[] names = functionNames.split(" ");
		String[] inverseNames = ("1/x arcsin 10^x arccos e^x arctan arcctg n\u221Ax")
				.split(" ");
		JButton[] functions = new JButton[names.length];
		String[] location = "2,1 2,2 3,1 3,2 4,1 4,2 5,2".split(" ");
		for (int i = 0; i < names.length - 1; i++) {
			functions[i] = new JButton(names[i]);
			functions[i].addActionListener(functionListener);
			p.add(functions[i], location[i]);
		}
		functions[names.length - 1] = new JButton(names[names.length - 1]);
		functions[names.length - 1].addActionListener(operatorListener);
		p.add(functions[names.length - 1], "5,1");
		JCheckBox inv = new JCheckBox("inv");
		ItemListener invertListener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getSource() == inv) {
					if (inv.isSelected()) {
						for (int i = 1; i < names.length; i++) {
							functions[i].setText(inverseNames[i]);
						}
					} else {
						for (int i = 1; i < names.length; i++) {
							functions[i].setText(names[i]);
						}
					}
				}

			}
		};
		inv.addItemListener(invertListener);

		p.add(inv, "5,7");

	}

	/**
	 * Adds the special buttons such as resetting.
	 * 
	 * @param p
	 *            the panel we're adding the buttons on
	 */
	private static void addSpecializedButtons(JPanel p) {
		ActionListener equalsListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = label.getText();
				if (!firstOperand.isEmpty() && !text.equals("")) {
					String first = firstOperand.pop();
					if (operation == null) {
						label.setText(first);
						firstOperand.push(first);
						return;
					}
					IOperator op = operators.get(operation);
					Double result = Double
							.valueOf(op.calculate(Double.parseDouble(first),
									Double.parseDouble(text)));
					label.setText(result.toString());
					firstOperand = new Stack<String>();
					operationHappened = true;
				}

			}

		};

		ActionListener clearListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText("");
			}
		};

		ActionListener resetListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText("");
				firstOperand = new Stack<String>();
				operation = null;
				operationHappened = false;
				stack = new Stack<String>();

			}

		};

		JButton equals = new JButton("=");
		equals.addActionListener(equalsListener);
		JButton clear = new JButton("clr");
		clear.addActionListener(clearListener);
		JButton reset = new JButton("res");
		reset.addActionListener(resetListener);
		p.add(reset, "2,7");
		p.add(equals, "1,6");
		p.add(clear, "1,7");

	}

	/**
	 * Creates a listener for our binary operations buttons
	 * 
	 * @return a newly created <code>ActionListener</code>
	 */
	private static ActionListener createOperatorListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = label.getText();
				if (text.contains("empty")) {
					label.setText("");
				}
				String name = ((JButton) e.getSource()).getText();
				if (firstOperand.isEmpty()) {
					if (label.getText().equals("")) {
						return;
					}
					firstOperand.push(text);
					operation = name;
					label.setText("");

				} else {
					if (text.equals("")) {
						operation = name;
						return;
					}
					if (operation == null) {
						return;
					}
					IOperator op = operators.get(operation);
					double x = Double.parseDouble(firstOperand.pop());
					double y = Double.parseDouble(text);
					double ans = op.calculate(x, y);
					firstOperand.push(Double.valueOf(ans).toString());
					label.setText(firstOperand.peek());
					operation = name;
					operationHappened = true;
				}

			}

		};
	}

	/**
	 * Creates our operator buttons
	 * 
	 * @param p
	 *            the panel we're adding the buttons on
	 */
	private static void addOperators(JPanel p) {
		fillOperators(operators);
		ActionListener operatorListener = createOperatorListener();

		JButton add = new JButton("+");
		add.addActionListener(operatorListener);
		JButton sub = new JButton("-");
		sub.addActionListener(operatorListener);
		JButton mul = new JButton("*");
		mul.addActionListener(operatorListener);
		JButton div = new JButton("/");
		div.addActionListener(operatorListener);
		p.add(add, "5,6");
		p.add(div, "2,6");
		p.add(mul, "3,6");
		p.add(sub, "4,6");

	}

	/**
	 * Adds the digits and decimal point buttons
	 * 
	 * @param p
	 *            the panel we're adding the buttons on
	 */
	private static void addDigits(JPanel p) {
		ActionListener digitListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String digit = ((JButton) e.getSource()).getText();
				if (operationHappened) {
					label.setText("");
					operationHappened = false;
				}
				label.setText(label.getText() + digit);
			}
		};

		ActionListener signListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = label.getText();
				if (text.equals("") || text.charAt(0) != '-') {
					label.setText("-".concat(text));
				} else if (text.charAt(0) == '-') {
					label.setText(text.substring(1));
				}

			}
		};

		JButton[] digits = new JButton[10];
		String[] positions = ("5,3 4,3 4,4 4,5 3,3 3,4 3,5 2,3 2,4 2,5")
				.split(" ");
		for (int i = 0; i < digits.length; i++) {
			digits[i] = new JButton("" + (i));
			digits[i].addActionListener(digitListener);
			p.add(digits[i], positions[i]);
		}

		JButton dot = new JButton(".");
		dot.addActionListener(digitListener);
		JButton sign = new JButton("+/-");
		sign.addActionListener(signListener);
		p.add(dot, "5,5");
		p.add(sign, "5,4");

	}

	/**
	 * Initializies all of our defined functions
	 * 
	 * @param functions
	 *            the table in which we store the functions
	 */
	private static void fillFunctions(HashMap<String, IFunction> functions) {
		functions.put("cos", new Cosine());
		functions.put("ctg", new Ctg());
		functions.put("1/x", new Inverse());
		functions.put("ln", new Ln());
		functions.put("log", new Log());
		functions.put("sin", new Sine());
		functions.put("tan", new Tan());
		functions.put("arccos", new ArcCos());
		functions.put("arcctg", new ArcCotan());
		functions.put("arcsin", new ArcSin());
		functions.put("arctan", new ArcTan());
		functions.put("e^x", new Exp());
		functions.put("10^x", new Power10());
	}

	/**
	 * Initializes all of our defined operators
	 * 
	 * @param functions
	 *            the table in which we store the operators
	 */
	private static void fillOperators(HashMap<String, IOperator> operators) {
		operators.put("+", new Add());
		operators.put("-", new Sub());
		operators.put("*", new Multiply());
		operators.put("/", new Divide());
		operators.put("x^n", new Pow());
		operators.put("n\u221Ax", new Root());
	}
}
