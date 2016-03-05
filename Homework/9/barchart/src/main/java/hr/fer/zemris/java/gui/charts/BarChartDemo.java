package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This class demosntrates the functionality of our chart.
 * 
 * @author Filip Džidić
 *
 */
public class BarChartDemo extends JFrame {
	/** serial number */
	private static final long serialVersionUID = 1L;
	/** the chart data is stored here */
	private static BarChart chart;
	/** the source file name is stored here */
	private static String source;

	/**
	 * The main method creates a window with our chart. The user must provide a
	 * file holding our chart data
	 * 
	 * @param args
	 *            a single argument, the path to the file containing the chart
	 *            data
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Please provide a path to file");
			System.exit(1);
		}
		List<String> list = Files.readAllLines(Paths.get(args[0]));

		if (list.size() != 6) {
			System.err.println("File has bad format");
			System.exit(1);
		}

		String xtitle = list.get(0);
		String ytitle = list.get(1);
		ArrayList<XYValue> valueList = new ArrayList<>();
		String[] values = list.get(2).split(" ");
		if (values.length <= 1) {
			System.err.println("Points have bad format");
			System.exit(1);
		}
		for (String value : values) {
			String[] point = value.split(",");
			if (point.length != 2) {
				System.err.println("Point has bad format " + value);
				System.exit(1);
			}
			valueList.add(new XYValue(Integer.parseInt(point[0]), Integer
					.parseInt(point[1])));
		}
		int ymin = Integer.parseInt(list.get(3));
		int ymax = Integer.parseInt(list.get(4));
		int step = Integer.parseInt(list.get(5));
		chart = new BarChart(valueList, xtitle, ytitle, ymin, ymax, step);
		source = args[0];
		SwingUtilities.invokeLater(() -> {
			BarChartDemo op = new BarChartDemo();
			op.setVisible(true);
		});

	}

	/**
	 * The default constructor creates the window holding our chart
	 */
	public BarChartDemo() {
		setLocation(10, 10);
		setSize(1280, 720);
		setTitle("I created this literally 30 minutes before the deadline I'm so sorry you have to see how ugly it is");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGui();
	}

	/**
	 * Initializes our GUI elements and components
	 */
	private void initGui() {
		JLabel labela = new JLabel("Source:" + source);
		BarChartComponent component = new BarChartComponent(chart);

		labela.setHorizontalAlignment(SwingConstants.CENTER);
		labela.setOpaque(true);
		labela.setBackground(Color.WHITE);

		component.setOpaque(true);
		component.setBackground(Color.WHITE);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(labela, BorderLayout.NORTH);
		getContentPane().add(component, BorderLayout.CENTER);
	}
}
