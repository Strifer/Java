package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The class <code>RequestContext</code> models a writer used for generating
 * HTTP requests and writing them to a given output stream.
 * <p>
 * It comes equipped with several methods for defining character encodings and
 * default HTTP header values. It also enables the user to store general,
 * temporary and persistent parameters for later quick retrieval. <br>
 * Finally it enables an easy way of storing <code>RCCookies</code> which are
 * used in HTTP requests.
 * 
 * @author Filip Džidić
 *
 */
public class RequestContext {
	/** the output stream upon which we write all the data sent to this object */
	private OutputStream outputStream;
	/** the charset used for decoding bytes of data */
	private Charset charset;

	/**
	 * the encoding used for decoding <code>String</code> set to UTF-8 as
	 * default
	 */
	private String encoding = "UTF-8";
	/**
	 * the statusCode used in HTTP header, set to 200 as default.
	 */
	private int statusCode = 200;
	/** the textStatus used in HTTP header, set to OK as default. */
	private String statusText = "OK";
	/** the mimeType used in HTTP header, set to text/html as default */
	private String mimeType = "text/html";

	/** a collection of all the general parameters and their associated values */
	private Map<String, String> parameters;
	/** a collection of all the temporary parameters and their associated values */
	private Map<String, String> temporaryParameters;
	/**
	 * a collection of all the persistent parameters and their associated values
	 */
	private Map<String, String> persistentParameters;
	/** a collection of all the RCCookies contained by this object */
	private List<RCCookie> outputCookies;
	/** used for recording if the header has already been generated */
	private boolean headerGenerated;

	/**
	 * Constructs a new instance of <code>RequestContext</code> with he provided
	 * parameters.
	 * 
	 * @param outputStream
	 *            the <code>OutputStream</code> being used to write data on
	 * @param parameters
	 *            general parameters used by the object
	 * @param persistentParameters
	 *            persistent parameters used by the object
	 * @param outputCookies
	 *            list of all cookies used by the object
	 * @throws IllegalArgumentException
	 *             if outputStream is null
	 */
	public RequestContext(OutputStream outputStream,
			Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies) {
		if (outputStream == null) {
			throw new IllegalArgumentException("Stream cannot be null");
		}
		this.temporaryParameters = new HashMap<String, String>();
		this.outputStream = outputStream;
		if (parameters == null) {
			this.parameters = new HashMap<String, String>();
		} else {
			this.parameters = parameters;
		}
		if (persistentParameters == null) {
			this.persistentParameters = new HashMap<String, String>();
		} else {
			this.persistentParameters = persistentParameters;
		}
		if (outputCookies == null) {
			this.outputCookies = new ArrayList<RCCookie>();
		} else {
			this.outputCookies = outputCookies;
		}

	}

	/**
	 * Encoding setter.
	 * 
	 * @throws RuntimeException
	 *             if the header has already been generated
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new RuntimeException("Header already created");
		}
		this.encoding = encoding;
	}

	/**
	 * Status code setter.
	 * 
	 * @throws RuntimeException
	 *             if the header has already been generated
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException("Header already created");
		}
		this.statusCode = statusCode;
	}

	/**
	 * Status text setter.
	 * 
	 * @throws RuntimeException
	 *             if the header has already been generated
	 * @param statusText
	 *            the statusText to set
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException("Header already created");
		}
		this.statusText = statusText;
	}

	/**
	 * MimeType setter.
	 * 
	 * @throws RuntimeException
	 *             if the header has already been generated
	 * @param mimeType
	 *            the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException("Header already created");
		}
		this.mimeType = mimeType;
	}

	/**
	 * Retrieves a parameter from the parameter map.
	 * 
	 * @param name
	 *            the name of the parameter
	 * @return the associated parameter or null if it does not exist
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Retrieves a <code>Set</code> of all parameter names found in the
	 * parameter map.
	 * 
	 * @return <code>Set</code> of all the parameter names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet((Set<String>) parameters.keySet());
	}

	/**
	 * Retrieves a parameter from the persistent parameter map.
	 * 
	 * @param name
	 *            the name of the persistent parameter
	 * @return the associated persistent parameter or null if it does not exist
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Retrieves a <code>Set</code> of all parameter names found in the
	 * persistent parameter map.
	 * 
	 * @return <code>Set</code> of all the persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet((Set<String>) persistentParameters
				.keySet());
	}

	/**
	 * Associates the specified persistent parameter name with a new value. If
	 * persistent parameter is already present its associated value is updated.
	 * 
	 * @param name
	 *            the persistent parameter being updated
	 * @param value
	 *            the new value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Removes the specified persistent parameters from the persistent
	 * parameters map (if present).
	 * 
	 * @param name
	 *            the persistent parameter being removed
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Retrieves a parameter from the temporary parameter map.
	 * 
	 * @param name
	 *            the name of the temporary parameter
	 * @return the associated temporary parameter or null if it does not exist
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Retrieves a <code>Set</code> of all parameter names found in the
	 * temporary parameter map.
	 * 
	 * @return <code>Set</code> of all the temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet((Set<String>) temporaryParameters
				.keySet());
	}

	/**
	 * Associates the specified temporary parameter name with a new value. If
	 * temporary parameter is already present its associated value is updated.
	 * 
	 * @param name
	 *            the temporary parameter being updated
	 * @param value
	 *            the new value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Removes the specified temporary parameters from the temporary parameters
	 * map (if present).
	 * 
	 * @param name
	 *            the temporary parameter being removed
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Adds provided <code>RCCookie</code> to the <code>RCCookie</code> list.
	 * 
	 * @param rcCookie
	 *            the cookie being added.
	 */
	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);

	}

	/**
	 * Writes data to the object's <code>OutputStream</code>. If a HTTP header
	 * hasn't already been generated it is generated and written to the
	 * <code>OutputStream</code>. Any further changes to this object's main
	 * parameters after creating the header are not allowed.
	 * 
	 * @param data
	 *            the data being written
	 * @return reference to this updated object
	 * @throws IOException
	 *             if an IOError occurs
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			createHeader();
		}
		outputStream.write(data);
		return this;
	}

	/**
	 * Writes <code>String</code> data to the object's <code>OutputStream</code>
	 * . If a HTTP header hasn't already been generated it is generated and
	 * written to the <code>OutputStream</code>. Any further changes to this
	 * object's main parameters after creating the header are not allowed.
	 * 
	 * @param text
	 *            the <code>String</code> data being written
	 * @return reference to this updated object
	 * @throws IOException
	 *             if an IOError occurs
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			createHeader();
		}
		outputStream.write(text.getBytes(charset));
		return this;
	}

	/**
	 * Creates a new HTTP header and writes it to <code>OutputStream</code>.
	 * This method is called upon first attempt at writing to this object's
	 * <code>OutputStream</code>.
	 * 
	 * @throws IOException
	 *             if an IOError occurs
	 */
	private void createHeader() throws IOException {
		charset = Charset.forName(encoding);
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		if (mimeType.startsWith("text/")) {
			sb.append("Content-Type: " + mimeType + "; charset=" + encoding
					+ "\r\n");
		} else {
			sb.append("Content-Type: " + mimeType + "\r\n");
		}
		if (!outputCookies.isEmpty()) {
			sb.append(makeCookies());
		}
		sb.append("\r\n");
		String body = sb.toString();
		outputStream.write(body.getBytes(StandardCharsets.ISO_8859_1));
		headerGenerated = true;
	}

	/**
	 * This method creates a <code>String</code> representation of all the
	 * cookies held in the cookie list.
	 * <p>
	 * Cookies follow the following format:<br>
	 * <code>Set-Cookie: name =”[value]”; Domain=[domain]; Path=[path]; Max-Age=[maxAge]</code>
	 * <br>
	 * Each cookie must have a defined named, the other parameters are omitted
	 * from this output if they are not present.
	 * 
	 * @return <code>String</code> representation of all the cookies in the
	 *         cookie list
	 */
	private String makeCookies() {
		StringBuilder sb = new StringBuilder();
		for (RCCookie cookie : outputCookies) {
			sb.append("Set-Cookie: " + cookie.getName() + "=\""
					+ cookie.getValue() + "\"");
			if (cookie.getDomain() != null) {
				sb.append("; Domain=" + cookie.getDomain());
			}
			if (cookie.getPath() != null) {
				sb.append("; Path=" + cookie.getPath());
			}
			if (cookie.getMaxAge() != null) {
				sb.append("; Max-Age=" + cookie.getMaxAge());
			}
			if (cookie.getHTTPOnly() == true) {
				sb.append("; HTTPonly");
			}
			sb.append("\r\n");
		}
		return sb.toString();
	}

	/**
	 * The class <code>RCCookie</code> models a single cookie used by our
	 * <code>RequestContext</code> class. <br>
	 * Each <code>RCCookie</code> defines six values.
	 * 
	 * @author Filip Džidić
	 *
	 */
	public static class RCCookie {
		/** the name associated with the cookie */
		private String name;
		/** the value associated with the cookie's name */
		private String value;
		/** the cookie's domain */
		private String domain;
		/** path to the cookie */
		private String path;
		/** max age defined for the cookie */
		private Integer maxAge;
		/** HTTPonly indicator */
		private boolean HTTPonly;

		/**
		 * Creates a new <code>RCCookie</code> by providing all the necessary
		 * parameters.
		 * 
		 * @param name
		 *            the name associated with the cookie
		 * @param value
		 *            the value associated with the cookie's name
		 * @param maxAge
		 *            the cookie's maxAge
		 * @param domain
		 *            the cookie's domain
		 * @param path
		 *            the cookie's filepath
		 * @param HTTPonly
		 *            indicates if the cookie is HTTPonly
		 */
		public RCCookie(String name, String value, Integer maxAge,
				String domain, String path, boolean HTTPonly) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.HTTPonly = HTTPonly;
		}

		/**
		 * Getter for name.
		 * 
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter for value.
		 * 
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Getter for domain.
		 * 
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Getter for path.
		 * 
		 * @return the path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Getter for maxAge.
		 * 
		 * @return the maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Getter for HTTPonly status.
		 * 
		 * @return the HTTPonly status
		 */
		public boolean getHTTPOnly() {
			return HTTPonly;
		}

	}

}
