package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet showcases the main index page of our web application.
 * <p>
 * This webapp comes with seven main pages.
 * </p>
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
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);

	}
}
