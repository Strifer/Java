package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

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
		String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		if (!Paths.get(fileName).toFile().exists()) {
			int bandCount = (int) req.getServletContext().getAttribute(
					"bandCount");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bandCount - 1; i++) {
				sb.append((i + 1) + "\t" + Integer.valueOf(0).toString() + "\n");
			}
			sb.append(bandCount + "\t" + Integer.valueOf(0).toString());
			Files.write(Paths.get(fileName), sb.toString().getBytes(),
					StandardOpenOption.CREATE);
		}
		List<String> results = Files.readAllLines(Paths.get(fileName));
		Integer index = null;
		try {
			index = Integer.valueOf(req.getParameter("id"));
		} catch (Exception e) {
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}
		if (index < 1 || index > results.size()) {
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}
		String res = results.get(index - 1);
		int votes = Integer.parseInt(res.split("\\t")[1]) + 1;
		results.set(index - 1, "" + index + '\t' + votes);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < results.size() - 1; i++) {
			sb.append(results.get(i) + '\n');
		}
		sb.append(results.get(results.size() - 1));
		Files.write(Paths.get(fileName), sb.toString().getBytes(),
				StandardOpenOption.CREATE);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
