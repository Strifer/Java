package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet handles adding comments to existing blog entries.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet("/servleti/newComment")
public class NewCommentServlet extends HttpServlet {
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
		BlogComment comment = new BlogComment();
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(
				Long.parseLong(req.getParameter("entry")));
		comment.setBlogEntry(entry);
		comment.setMessage(req.getParameter("text"));
		comment.setPostedOn(new Date());
		if (req.getParameter("anonymous") == null) {
			comment.setUsersEMail("Anonymous");
		} else {
			comment.setUsersEMail(entry.getCreator().getEmail());
		}
		DAOProvider.getDAO().addBlogComment(comment);
		resp.sendRedirect("/aplikacija5/servleti/author/"
				+ entry.getCreator().getNick() + "/" + entry.getId());
	}
}
