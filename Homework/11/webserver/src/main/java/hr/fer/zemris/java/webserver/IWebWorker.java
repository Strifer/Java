package hr.fer.zemris.java.webserver;

/**
 * This interface defines a specific task being performed by the server upon
 * invocation.
 * 
 * @author Filip Džidić
 *
 */
public interface IWebWorker {
	/**
	 * This method processes and performs a specialized task.
	 * 
	 * @param context
	 *            reference to the object used for executing server functions
	 */
	public void processRequest(RequestContext context);
}