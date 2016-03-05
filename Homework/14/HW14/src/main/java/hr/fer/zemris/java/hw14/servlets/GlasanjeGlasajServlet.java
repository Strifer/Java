package hr.fer.zemris.java.hw14.servlets;

import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.dao.DAOProvider;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet gives a single vote to the provided band ID. If the band ID is
 * not found in the band list this servlet leads to an error page.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet(urlPatterns = "/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Long id = Long.parseLong(req.getParameter("id"));
		try {
			DAOProvider.getDao().incrementVote(id);
		} catch (DAOException ex) {
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}
		Long pollID = DAOProvider.getDao().getPollOption(id).getPollID();
		req.getSession().setAttribute("pollID", pollID);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
