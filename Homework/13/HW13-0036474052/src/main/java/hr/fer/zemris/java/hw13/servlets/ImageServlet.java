package hr.fer.zemris.java.hw13.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * This servlet showcases the functionality of the <code>JFreeChart</code>. It
 * creates a single .png image holding a piechart.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet(urlPatterns = "/reportImage")
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JFreeChart chart = createChart(createDataset(), "OS usage");
		ServletOutputStream stream = resp.getOutputStream();
		resp.setContentType("image/png");
		ChartUtilities.writeBufferedImageAsPNG(stream, chart
				.createBufferedImage(600, 400, BufferedImage.TYPE_3BYTE_BGR,
						null));
		stream.close();
	}

	/**
	 * Creates a dataset holding hard coded data on OS usage.
	 * 
	 * @return the new dataset used for creating the chart
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		return result;

	}

	/**
	 * Creates a new chart with provided data.
	 * 
	 * @param dataset
	 *            data holding the necessary parameters
	 * @param title
	 *            the name of the new pie
	 * @return the new chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}
}
