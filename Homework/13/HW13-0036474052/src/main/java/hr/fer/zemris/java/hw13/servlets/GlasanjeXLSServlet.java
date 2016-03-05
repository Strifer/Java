package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.servlets.GlasanjeRezultatiServlet.BandWithVote;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet creates a new .xlss excel file holding the voting poll results.
 * 
 * @author Filip Džidić
 *
 */
@WebServlet(urlPatterns = "/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int rowCount = 0;
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Voting results");
		HSSFRow rowhead = sheet.createRow(rowCount++);
		rowhead.createCell(0).setCellValue("Band name");
		rowhead.createCell(1).setCellValue("Votes");
		rowhead.createCell(2).setCellValue("Song example");
		@SuppressWarnings("unchecked")
		List<BandWithVote> data = (List<BandWithVote>) req.getSession()
				.getAttribute("voteResults");
		for (BandWithVote vote : data) {
			HSSFRow row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(vote.getBand().getName());
			row.createCell(1).setCellValue(vote.getVotes());
			row.createCell(2).setCellValue(vote.getBand().getLink());
		}
		resp.setContentType("application/xls");
		resp.setHeader("Content-disposition",
				"attachment; filename=pollResults.xls");
		hwb.write(resp.getOutputStream());
		hwb.close();
		resp.getOutputStream().flush();

	}
}
