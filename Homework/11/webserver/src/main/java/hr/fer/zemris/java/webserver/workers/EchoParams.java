package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This is a purely demonstrational class used to showcase conventional
 * settings.
 * 
 * @author Filip Džidić
 *
 */
public class EchoParams implements IWebWorker {

	/**
	 * Lists every general parameter argument provided upon this object's call.
	 */
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		Set<String> names = context.getParameterNames();

		try {
			context.write("<html><body>");
			context.write("<h1>EchoNode</h1>");
			if (names == null) {
				context.write("<p>There were no provided parameters</p>");
			} else {
				for (String name : names) {
					context.write("<p>Parameter: " + name + "="
							+ context.getParameter(name) + "</p>");
				}
			}
			context.write("</body></html>");
		} catch (IOException ex) {
			// Log exception to servers log...
			ex.printStackTrace();
		}

	}

}
