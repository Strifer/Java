package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.servlets.GlasanjeServlet.Band;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet prepares all the necessary poll result data. The results are
 * displayed in the form of a table, a pie chart and a .xlss file.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet(urlPatterns = "/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * A simple JavaBean which associates <code>Band</code> with counted votes.
	 * 
	 * @author Filip Džidić
	 *
	 */
	public static class BandWithVote {
		/** the band being voted for */
		private GlasanjeServlet.Band band;
		/** the votes of the band */
		private int votes;

		/**
		 * Constructs a new java bean with the provided parameters.
		 * 
		 * @param band
		 *            the band being voted for
		 * @param votes
		 *            the votes of the band
		 */
		public BandWithVote(Band band, int votes) {
			super();
			this.band = band;
			this.votes = votes;
		}

		/**
		 * Getter method for the band.
		 * 
		 * @return the band
		 */
		public GlasanjeServlet.Band getBand() {
			return band;
		}

		/**
		 * Getter method for the votes.
		 * 
		 * @return the votes
		 */
		public int getVotes() {
			return votes;
		}

	}

	/**
	 * A simple JavaBean which associates band indices with counted votes.
	 * 
	 * @author Filip Džidić
	 *
	 */
	public static class VoteResult {
		/** the index of the band */
		private int index;
		/** the counted votes for the band */
		private int votes;

		/**
		 * Constructs a new java bean with the provided parameters.
		 * 
		 * @param index
		 *            the index of the band being voted for
		 * @param votes
		 *            the votes of the band
		 */
		public VoteResult(int index, int votes) {
			super();
			this.index = index;
			this.votes = votes;
		}

		/**
		 * Getter method for the index.
		 * 
		 * @return the index
		 */
		public int getIndex() {
			return index;
		}

		/**
		 * Getter method for the votes.
		 * 
		 * @return the votes
		 */
		public int getVotes() {
			return votes;
		}

	}

	/**
	 * This comparator is used to sort <code>VoteResult</code> by the number of
	 * votes.
	 * 
	 * @author Filip Džidić
	 *
	 */
	public class ComparatorByVotes implements Comparator<VoteResult> {

		@Override
		public int compare(VoteResult o1, VoteResult o2) {
			if (o1.getVotes() < o2.getVotes()) {
				return 1;
			}
			if (o1.getVotes() > o2.getVotes()) {
				return -1;
			}
			return 0;
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		if (!Paths.get(fileName).toFile().exists()) {
			int bandCount = (int) req.getServletContext().getAttribute(
					"bandCount");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bandCount - 1; i++) {
				sb.append(i + '\t' + 0 + '\n');
			}
			sb.append(bandCount - 1 + '\t' + 0);
			Files.write(Paths.get(fileName), sb.toString().getBytes(),
					StandardOpenOption.CREATE);
		}
		List<String> results = Files.readAllLines(Paths.get(fileName));
		List<VoteResult> voteResult = new ArrayList<>();
		for (String s : results) {
			String[] parameters = s.split("\\t", 2);
			voteResult.add(new VoteResult(Integer.parseInt(parameters[0]),
					Integer.parseInt(parameters[1])));
		}
		Collections.sort(voteResult, new ComparatorByVotes());
		List<BandWithVote> sortedBands = sortBands(req, voteResult);
		req.getSession().setAttribute("voteResults", sortedBands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req,
				resp);
	}

	/**
	 * This method sorts all the band names recorded in our session by their
	 * final vote tally.
	 * 
	 * @param req
	 *            the servlet request
	 * @param result
	 *            a list of the final vote tally
	 * @return sorted list of bands and their votes
	 */
	@SuppressWarnings("unchecked")
	public List<BandWithVote> sortBands(HttpServletRequest req,
			List<VoteResult> result) {
		List<GlasanjeServlet.Band> bands = (List<GlasanjeServlet.Band>) req
				.getServletContext().getAttribute("bandContext");
		List<BandWithVote> sortedBands = new ArrayList<>();
		for (VoteResult res : result) {
			sortedBands.add(new BandWithVote(bands.get(res.index - 1),
					res.votes));
		}
		createTopBands(sortedBands, req);
		return sortedBands;

	}

	/**
	 * This method creates a list of the top winners of the voting poll.
	 * 
	 * @param sortedBands
	 *            a list of bands sorted by their votes
	 * @param req
	 *            the servlet request
	 */
	private void createTopBands(List<BandWithVote> sortedBands,
			HttpServletRequest req) {
		int topVotes = sortedBands.get(0).votes;
		List<BandWithVote> topBands = new ArrayList<>();
		for (BandWithVote bv : sortedBands) {
			if (bv.votes == topVotes) {
				topBands.add(bv);
			} else {
				break;
			}
		}
		req.setAttribute("topBands", topBands);

	}
}
