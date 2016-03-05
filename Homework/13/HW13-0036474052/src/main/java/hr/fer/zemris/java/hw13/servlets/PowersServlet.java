package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet creates a new .xlss file holding data of integers and their
 * powers.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet(urlPatterns = "/powers")
public class PowersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer startFrom = null;
		Integer endAt = null;
		Integer n = null;
		try {
			startFrom = Integer.valueOf(req.getParameter("a"));
			if (startFrom < -100 || startFrom > 100) {
				req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(
						req, resp);
				return;
			}
		} catch (Exception e) {
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}

		try {
			endAt = Integer.valueOf(req.getParameter("b"));
			if (endAt < -100 || endAt > 100) {
				req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(
						req, resp);
				return;
			}
		} catch (Exception e) {
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}

		try {
			n = Integer.valueOf(req.getParameter("n"));
			if (n < 1 || n > 5) {
				req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(
						req, resp);
				return;
			}
		} catch (Exception e) {
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}

		if (startFrom > endAt) {
			Integer temp = startFrom;
			startFrom = endAt;
			endAt = temp;
		}

		HSSFWorkbook hwb = new HSSFWorkbook();
		int rowCount = 0;
		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet("Sheet " + i);
			HSSFRow rowhead = sheet.createRow(rowCount++);
			rowhead.createCell(0).setCellValue("x");
			rowhead.createCell(1).setCellValue("x^" + i);
			for (int j = startFrom; j <= endAt; j++) {
				HSSFRow row = sheet.createRow(rowCount++);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
			rowCount = 0;
		}
		resp.setContentType("application/xls");
		resp.setHeader("Content-disposition", "attachment; filename=powers.xls");
		hwb.write(resp.getOutputStream());
		hwb.close();
		resp.getOutputStream().flush();

	}

}
