package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.beans.Poll;
import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * This servlet showcases the main index page of our web application.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet(name = "index", urlPatterns = { "/index" })
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Poll> list = DAOProvider.getDao().getPolls();
		req.setAttribute("polls", list);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);

	}
}
