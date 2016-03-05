package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet leads to a page holding the band voting poll. The client is
 * offered a list of bands to vote for. Voting leads to the final result.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet(urlPatterns = "/glasanje")
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt");
		List<Band> bands = new ArrayList<Band>();
		List<String> strings = Files.readAllLines(Paths.get(fileName));
		for (String s : strings) {
			String[] parameters = s.split("\\t", 3);
			bands.add(new Band(Integer.parseInt(parameters[0]), parameters[1],
					parameters[2]));
		}
		req.getServletContext().setAttribute("bandContext", bands);
		req.setAttribute("bands", bands);
		req.getServletContext().setAttribute("bandCount", bands.size());
		req.getRequestDispatcher("WEB-INF/pages/glasanjeIndex.jsp").forward(
				req, resp);
	}

	/**
	 * A simple JavaBean which represents bands inside our voting poll.
	 * 
	 * @author Filip Džidić
	 *
	 */
	public static class Band {
		/** the index of the band */
		private int index;
		/** the band's name */
		private String name;
		/** a link to one of the band's song */
		private String link;

		/**
		 * Getter method for the index.
		 * 
		 * @return the index of the band
		 */
		public int getIndex() {
			return index;
		}

		/**
		 * Getter method for the name.
		 * 
		 * @return the name of the band
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter method for the link.
		 * 
		 * @return the link to one of the band's songs
		 */
		public String getLink() {
			return link;
		}

		/**
		 * Constructs a new <code>Band</code> with provided parameters.
		 * 
		 * @param index
		 *            the index of the band
		 * @param name
		 *            the band's name
		 * @param link
		 *            a link to one of the band's songs
		 */
		public Band(int index, String name, String link) {
			this.index = index;
			this.name = name;
			this.link = link;
		}

	}

}
