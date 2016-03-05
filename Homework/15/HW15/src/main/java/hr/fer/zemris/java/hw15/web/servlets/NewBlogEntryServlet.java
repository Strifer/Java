package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet handles creating new blog entries inside existing accounts.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet("/servleti/newBlog")
public class NewBlogEntryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Submit".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath());
			return;
		}
		BlogEntry entry = new BlogEntry();
		entry.setCreatedAt(new Date());
		entry.setLastModifiedAt(new Date());
		entry.setText((String) req.getParameter("text"));
		entry.setTitle((String) req.getParameter("title"));
		entry.setCreator(DAOProvider.getDAO().getBlogUser(
				(String) req.getParameter("nick")));

		DAOProvider.getDAO().addBlogEntry(entry);
		resp.sendRedirect("/aplikacija5/servleti/author/"
				+ entry.getCreator().getNick() + "/" + entry.getId());

	}
}
