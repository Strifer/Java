package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet creates a trigonometric table of a certain range of values.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet(urlPatterns = "/trigonometric")
public class TrigonometryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * This is a simple JavaBean which holds integers and their sine and cosine
	 * values.
	 * 
	 * @author Filip Džidić
	 *
	 */
	public class SineCos {
		/** the x value in degrees */
		private int degrees;
		/** sin(x) */
		private double sin;
		/** cos(x) */
		private double cos;

		/**
		 * Constructs a new <code>SineCos</code> with the provided parameters.
		 * 
		 * @param degrees
		 *            the x value in degrees
		 * @param sin
		 *            sin(x)
		 * @param cos
		 *            cos(x)
		 */
		public SineCos(int degrees, double sin, double cos) {
			this.degrees = degrees;
			this.sin = sin;
			this.cos = cos;
		}

		/**
		 * Getter method for the degrees value.
		 * 
		 * @return the degrees
		 */
		public int getDegrees() {
			return degrees;
		}

		/**
		 * Getter method for the calculated sine.
		 * 
		 * @return the sine value
		 */
		public double getSin() {
			return sin;
		}

		/**
		 * Getter method for the calculated cosine.
		 * 
		 * @return the cos value
		 */
		public double getCos() {
			return cos;
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Integer startFrom = null;
		Integer endAt = null;

		try {
			startFrom = Integer.valueOf(req.getParameter("a"));
		} catch (Exception e) {
			startFrom = 0;
		}

		try {
			endAt = Integer.valueOf(req.getParameter("b"));
		} catch (Exception e) {
			endAt = 360;
		}

		List<SineCos> result = new ArrayList<SineCos>();
		for (int i = startFrom; i <= endAt; i++) {
			result.add(new SineCos(i, Math.round(1000.0 * Math.sin(Math
					.toRadians(i))) / 1000.0, Math.round(1000.0 * Math.cos(Math
					.toRadians(i))) / 1000.0));
		}
		req.setAttribute("results", result);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(
				req, resp);

	}
}
