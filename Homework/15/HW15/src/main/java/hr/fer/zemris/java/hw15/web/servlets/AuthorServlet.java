package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlets main job is to handle BlogUser specific URL-s. It handles
 * displaying specific blogs, making new blog entries, editing existing blog
 * entries and displaying specific blog entries with their comments.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String s = req.getPathInfo().substring(1);
		if (s.isEmpty()) {
			resp.sendRedirect("/aplikacija5/servleti/main");
		}
		String[] pars = s.split("/");
		BlogUser user = DAOProvider.getDAO().getBlogUser(pars[0]);
		if (user == null) {
			resp.sendRedirect("/aplikacija5/servleti/main");
			return;
		}
		if (pars.length == 1) {
			req.setAttribute("selected", user);
			req.setAttribute("entries",
					DAOProvider.getDAO().getBlogEntries(user));
			DAOProvider.getDAO().getBlogEntries(user).toString();
			req.getRequestDispatcher("/WEB-INF/pages/BlogDisplay.jsp").forward(
					req, resp);
			return;
		} else if (pars.length == 2) {
			req.setAttribute("blogUser", user);
			switch (pars[1]) {
				case "new":
					req.setAttribute("action", "new");
					req.setAttribute("nick", pars[0]);
					req.getRequestDispatcher("/WEB-INF/pages/EntryCreator.jsp")
							.forward(req, resp);
					;
					return;
				case "edit":
					String entryID = req.getParameter("entryID");
					if (entryID == null) {
						resp.sendRedirect("/aplikacija5/servleti/main");
						return;
					}
					BlogEntry entryE = null;
					try {
						entryE = DAOProvider.getDAO().getBlogEntry(
								Long.parseLong(entryID));
					} catch (NumberFormatException e) {
						resp.sendRedirect("/aplikacija5/servleti/main");
						return;
					}

					if (entryE == null) {
						resp.sendRedirect("/aplikacija5/servleti/main");
						return;
					}
					req.setAttribute("blogEntry", entryE);
					req.setAttribute("action", "edit");
					req.getRequestDispatcher("/WEB-INF/pages/EntryCreator.jsp")
							.forward(req, resp);
					return;
				default:
					BlogEntry entry = null;
					try {
						entry = DAOProvider.getDAO().getBlogEntry(
								Long.parseLong(pars[1]));
					} catch (NumberFormatException e) {
						resp.sendRedirect("/aplikacija5/servleti/main");
						return;
					}

					if (entry == null) {
						resp.sendRedirect("/aplikacija5/servleti/main");
						return;
					}
					req.setAttribute("blogEntry", entry);
					req.setAttribute("blogComments", DAOProvider.getDAO()
							.getBlogComments(entry));
					req.getRequestDispatcher("/WEB-INF/pages/EntryDisplay.jsp")
							.forward(req, resp);
			}
		}
	}

}
