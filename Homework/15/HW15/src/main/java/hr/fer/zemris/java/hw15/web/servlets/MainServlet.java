package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The main introductory page servlet offers users to make a new account, log in
 * or procede to browse existing blogs.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nick = (String) req.getSession().getAttribute("nick");
		if (nick != null) {
			System.out.println("This is " + nick);
		}
		Boolean logged = Boolean.valueOf(nick != null);
		req.setAttribute("logged", logged);
		List<BlogUser> users = DAOProvider.getDAO().getUsers();
		req.setAttribute("users", users);
		req.getRequestDispatcher("../WEB-INF/pages/index.jsp").forward(req,
				resp);
	}
}
