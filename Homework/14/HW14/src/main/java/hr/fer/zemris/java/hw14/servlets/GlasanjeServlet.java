package hr.fer.zemris.java.hw14.servlets;

import hr.fer.zemris.java.hw14.beans.Poll;
import hr.fer.zemris.java.hw14.beans.PollOptions;
import hr.fer.zemris.java.hw14.dao.DAOProvider;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet leads to a page holding the voting poll. The client is offered a
 * list of options to vote for. Voting leads to the final result.
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

		Long pollID = Long.parseLong(req.getParameter("pollID"));
		List<PollOptions> list = DAOProvider.getDao().getPollOptions(pollID);
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		req.setAttribute("specifiedPoll", poll);
		req.setAttribute("pollList", list);
		req.getRequestDispatcher("WEB-INF/pages/glasanjeIndex.jsp").forward(
				req, resp);
	}

}
