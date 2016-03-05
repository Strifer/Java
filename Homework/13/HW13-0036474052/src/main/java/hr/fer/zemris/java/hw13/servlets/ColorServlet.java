package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This servlet sets the background color of the user's session to the provided
 * parameter.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet(name = "setColor", urlPatterns = { "/setColor" })
public class ColorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String color = req.getParameter("color").toString();
		session.setAttribute("pcolor", color);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);

	}

}
