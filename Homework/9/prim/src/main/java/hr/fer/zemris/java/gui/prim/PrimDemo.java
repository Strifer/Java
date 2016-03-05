package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This class shows the usage of our custom ListModel class.
 * 
 * @author Filip Džidić
 *
 */
public class PrimDemo extends JFrame {

	/**
	 * serial number
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The main method creates our GUI.
	 * 
	 * @param args
	 *            no arguments should be provided
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			PrimDemo op = new PrimDemo();
			op.setVisible(true);
		});
	}

	/**
	 * Default constructor constructs and initializes our GUI.
	 */
	public PrimDemo() {
		setLocation(10, 10);
		setSize(300, 300);
		setTitle("Prime Numbers");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGui();
	}

	/**
	 * This method initializes all of our GUI components.
	 */
	private void initGui() {
		PrimListModel lm = new PrimListModel();
		JList<Integer> l1 = new JList<Integer>(lm);
		JList<Integer> l2 = new JList<Integer>(lm);
		JButton butt = new JButton("next");
		JScrollPane j1 = new JScrollPane(l1);
		JScrollPane j2 = new JScrollPane(l2);

		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, j1, j2);
		butt.addActionListener(l -> {
			((PrimListModel) lm).next();
		});
		pane.setResizeWeight(0.5);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(pane, BorderLayout.CENTER);
		getContentPane().add(butt, BorderLayout.PAGE_END);

	}
}
