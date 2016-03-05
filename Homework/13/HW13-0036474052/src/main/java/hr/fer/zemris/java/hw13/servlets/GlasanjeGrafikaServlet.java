package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.servlets.GlasanjeRezultatiServlet.BandWithVote;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

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
 * This servlet generates a graphical representation of poll results in the form
 * of a pie chart.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet(urlPatterns = "/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		List<BandWithVote> data = (List<BandWithVote>) req.getSession()
				.getAttribute("voteResults");
		JFreeChart chart = createChart(createDataset(data), "Band poll");
		ServletOutputStream stream = resp.getOutputStream();
		resp.setContentType("image/png");
		ChartUtilities.writeBufferedImageAsPNG(stream, chart
				.createBufferedImage(600, 400, BufferedImage.TYPE_3BYTE_BGR,
						null));
		stream.close();
	}

	/**
	 * Creates a dataset for filling charts.
	 * 
	 * @param data
	 *            a list of all the bands and their received votes
	 * @return the new dataset
	 */
	private PieDataset createDataset(List<BandWithVote> data) {
		DefaultPieDataset result = new DefaultPieDataset();
		for (BandWithVote bv : data) {
			result.setValue(bv.getBand().getName(), bv.getVotes());
		}
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
