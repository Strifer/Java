package hr.fer.zemris.java.hw14.servlets;

import hr.fer.zemris.java.hw14.beans.PollOptions;
import hr.fer.zemris.java.hw14.dao.DAOProvider;

import java.io.IOException;
import java.util.LinkedList;
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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Long pollID = Long.parseLong(req.getSession().getAttribute("pollID")
				.toString());
		List<PollOptions> sortedOptions = DAOProvider.getDao()
				.getSortedPollOptions(pollID);
		req.getSession().setAttribute("sortedOptions", sortedOptions);
		List<PollOptions> topOptions = new LinkedList<PollOptions>();
		long maxVote = sortedOptions.get(0).getVotesCount();
		for (PollOptions po : sortedOptions) {
			if (po.getVotesCount() == maxVote) {
				topOptions.add(po);
			}
		}
		req.setAttribute("topOptions", topOptions);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req,
				resp);

	}
}
