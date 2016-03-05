package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet's main jobs is to save and add newly registered accounts to our
 * data model.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet("/servleti/save")
public class SaveAccountServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			handle(req, resp);
		} catch (NoSuchAlgorithmException ignorable) {
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			handle(req, resp);
		} catch (NoSuchAlgorithmException ignorable) {
		}
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
			throws IOException, ServletException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Submit".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath());
			return;
		}

		RegistrationFormular form = new RegistrationFormular();
		form.createFromRequest(req);
		form.validate();

		if (form.hasErrors()) {
			req.setAttribute("zapis", form);
			List<BlogUser> users = DAOProvider.getDAO().getUsers();
			req.setAttribute("users", users);
			req.getRequestDispatcher("/WEB-INF/pages/registrationForm.jsp")
					.forward(req, resp);
			return;
		}
		BlogUser user = new BlogUser();
		form.makeBlogUser(user);
		DAOProvider.getDAO().addBlogUser(user);
		req.getSession().setAttribute("user", user);
		resp.sendRedirect(req.getServletContext().getContextPath());
	}

}
