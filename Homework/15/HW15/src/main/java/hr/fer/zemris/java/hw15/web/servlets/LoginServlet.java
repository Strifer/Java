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
 * This servlet handles logging in users.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet("/servleti/login")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handle(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handle(req, resp);
	}

	/**
	 * A general request handling method. It goes through the filled form and
	 * keeps asking for input until the user enters the correct information.
	 * 
	 * @param req
	 *            request context
	 * @param resp
	 *            response
	 * @throws IOException
	 *             if an IO error occurs
	 * @throws ServletException
	 *             if a Servlet error occurs
	 */
	private void handle(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Submit".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath());
			return;
		}

		RegistrationFormular form = new RegistrationFormular();
		form.createFromRequest(req);
		form.validateLogin();

		if (form.hasErrors()) {
			req.setAttribute("logZapis", form);
			List<BlogUser> users = DAOProvider.getDAO().getUsers();
			req.setAttribute("users", users);
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req,
					resp);
			return;
		}
		BlogUser user = DAOProvider.getDAO().getBlogUser(form.getNick());
		req.getSession().setAttribute("currentUserId", user.getId());
		req.getSession().setAttribute("currentUserFn", user.getFirstName());
		req.getSession().setAttribute("currentUserLn", user.getLastName());
		req.getSession().setAttribute("user",
				DAOProvider.getDAO().getBlogUser(form.getNick()));
		resp.sendRedirect("main");

	}

}
