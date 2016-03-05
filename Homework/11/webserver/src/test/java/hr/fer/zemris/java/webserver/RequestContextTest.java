package hr.fer.zemris.java.webserver;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import org.junit.Test;

public class RequestContextTest {

	@Test
	public void WriteStringTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String output = "HTTP/1.1 200 OK\r\n"
				+ "Content-Type: text/html; charset=UTF-8\r\n" + "\r\n" + "kek";
		RequestContext context = new RequestContext(bos, null, null, null);
		try {
			context.write("kek");
			assertTrue(output.equals(new String(bos.toByteArray())));
			assertTrue(context.getParameter("test") == null);
			assertTrue(context.getPersistentParameter("test") == null);
			assertTrue(context.getTemporaryParameter("test") == null);
		} catch (IOException ignorable) {

		}
	}

	@Test
	public void WriteByteTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String output = "HTTP/1.1 200 OK\r\n"
				+ "Content-Type: text/html; charset=UTF-8\r\n" + "\r\n" + "kek";
		RequestContext context = new RequestContext(bos, null, null, null);
		try {
			context.write("kek".getBytes());
			assertTrue(output.equals(new String(bos.toByteArray())));
		} catch (IOException ignorable) {

		}
	}

	@Test
	public void cookieConstructorTest() {
		RCCookie cookie = new RCCookie("name", "cookie", 320, "120", "/home",
				true);
		assertTrue(cookie.getHTTPOnly());
		assertTrue(cookie.getDomain().equals("120"));
		assertTrue(cookie.getMaxAge() == 320);
		assertTrue(cookie.getName().equals("name"));
		assertTrue(cookie.getValue().equals("cookie"));
		assertTrue(cookie.getPath().equals("/home"));
	}

	@Test
	public void persistentParameterMethodTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		HashMap<String, String> params = new HashMap<String, String>();
		RequestContext context = new RequestContext(bos, null, null, null);
		assertTrue(params.size() == context.getPersistentParameterNames()
				.size());

		params.put("blue", "red");
		context.setPersistentParameter("blue", "red");
		assertTrue(params.get("blue").equals(
				context.getPersistentParameter("blue")));
		context.removePersistentParameter("blue");
		assertFalse(params.get("blue").equals(
				context.getPersistentParameter("blue")));
	}

	@Test
	public void temporaryParameterMethodTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		HashMap<String, String> params = new HashMap<String, String>();
		RequestContext context = new RequestContext(bos, null, null, null);
		assertTrue(params.size() == context.getTemporaryParameterNames().size());

		params.put("blue", "red");
		context.setTemporaryParameter("blue", "red");
		assertTrue(params.get("blue").equals(
				context.getTemporaryParameter("blue")));
		context.removeTemporaryParameter("blue");
		assertFalse(params.get("blue").equals(
				context.getPersistentParameter("blue")));
	}

	@Test
	public void parameterMethodTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("blue", "red");
		RequestContext context = new RequestContext(bos, params, null, null);
		assertTrue(params.size() == context.getParameterNames().size());
		assertTrue(params.get("blue").equals(context.getParameter("blue")));
	}

	@Test
	public void encodingTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String output = "HTTP/1.1 200 OK\r\n"
				+ "Content-Type: text/html; charset=ISO_8859_1\r\n" + "\r\n"
				+ "";
		RequestContext context = new RequestContext(bos, null, null, null);
		try {
			context.setEncoding("ISO_8859_1");
			context.write("");
			assertTrue(output.equals(new String(bos.toByteArray())));
			assertTrue(context.getParameter("test") == null);
			assertTrue(context.getPersistentParameter("test") == null);
			assertTrue(context.getTemporaryParameter("test") == null);
		} catch (IOException ignorable) {

		}
	}

	@Test(expected = RuntimeException.class)
	public void encodingExceptionTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String output = "HTTP/1.1 200 OK\r\n"
				+ "Content-Type: text/html; charset=ISO_8859_1\r\n" + "\r\n"
				+ "";
		RequestContext context = new RequestContext(bos, null, null, null);
		try {
			context.write("");
			context.setEncoding("ISO_8859_1");
			assertTrue(output.equals(new String(bos.toByteArray())));
			assertTrue(context.getParameter("test") == null);
			assertTrue(context.getPersistentParameter("test") == null);
			assertTrue(context.getTemporaryParameter("test") == null);
		} catch (IOException ignorable) {

		}
	}

	@Test
	public void statusCodeTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String output = "HTTP/1.1 404 OK\r\n"
				+ "Content-Type: text/html; charset=UTF-8\r\n" + "\r\n" + "";
		RequestContext context = new RequestContext(bos, null, null, null);
		try {
			context.setStatusCode(404);
			context.write("");
			assertTrue(output.equals(new String(bos.toByteArray())));
			assertTrue(context.getParameter("test") == null);
			assertTrue(context.getPersistentParameter("test") == null);
			assertTrue(context.getTemporaryParameter("test") == null);
		} catch (IOException ignorable) {

		}
	}

	@Test(expected = RuntimeException.class)
	public void statusCodeExceptionTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String output = "HTTP/1.1 404 OK\r\n"
				+ "Content-Type: text/html; charset=UTF-8\r\n" + "\r\n" + "";
		RequestContext context = new RequestContext(bos, null, null, null);
		try {
			context.write("");
			context.setStatusCode(404);
			assertTrue(output.equals(new String(bos.toByteArray())));
			assertTrue(context.getParameter("test") == null);
			assertTrue(context.getPersistentParameter("test") == null);
			assertTrue(context.getTemporaryParameter("test") == null);
		} catch (IOException ignorable) {

		}
	}

	@Test
	public void statusTextTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String output = "HTTP/1.1 200 NO\r\n"
				+ "Content-Type: text/html; charset=UTF-8\r\n" + "\r\n" + "";
		RequestContext context = new RequestContext(bos, null, null, null);
		try {
			context.setStatusText("NO");
			context.write("");
			assertTrue(output.equals(new String(bos.toByteArray())));
			assertTrue(context.getParameter("test") == null);
			assertTrue(context.getPersistentParameter("test") == null);
			assertTrue(context.getTemporaryParameter("test") == null);
		} catch (IOException ignorable) {

		}
	}

	@Test(expected = RuntimeException.class)
	public void statusTextExceptionTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String output = "HTTP/1.1 200 NO\r\n"
				+ "Content-Type: text/html; charset=UTF-8\r\n" + "\r\n" + "";
		RequestContext context = new RequestContext(bos, null, null, null);
		try {
			context.write("");
			context.setStatusText("NO");
			assertTrue(output.equals(new String(bos.toByteArray())));
			assertTrue(context.getParameter("test") == null);
			assertTrue(context.getPersistentParameter("test") == null);
			assertTrue(context.getTemporaryParameter("test") == null);
		} catch (IOException ignorable) {

		}
	}

	@Test
	public void mimeTypeTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String output = "HTTP/1.1 200 OK\r\n"
				+ "Content-Type: text/plain; charset=UTF-8\r\n" + "\r\n" + "";
		RequestContext context = new RequestContext(bos, null, null, null);
		try {
			context.setMimeType("text/plain");
			context.write("");
			assertTrue(output.equals(new String(bos.toByteArray())));
			assertTrue(context.getParameter("test") == null);
			assertTrue(context.getPersistentParameter("test") == null);
			assertTrue(context.getTemporaryParameter("test") == null);
		} catch (IOException ignorable) {

		}
	}

	@Test(expected = RuntimeException.class)
	public void mimeTExceptionTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String output = "HTTP/1.1 200 OK\r\n"
				+ "Content-Type: text/plain; charset=UTF-8\r\n" + "\r\n" + "";
		RequestContext context = new RequestContext(bos, null, null, null);
		try {
			context.write("");
			context.setMimeType("text/plain");
			assertTrue(output.equals(new String(bos.toByteArray())));
			assertTrue(context.getParameter("test") == null);
			assertTrue(context.getPersistentParameter("test") == null);
			assertTrue(context.getTemporaryParameter("test") == null);
		} catch (IOException ignorable) {

		}
	}

	@Test
	public void withCookiesTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String output = "HTTP/1.1 200 OK\r\n"
				+ "Content-Type: text/html; charset=UTF-8\r\n"
				+ "Set-Cookie: name1=\"value1\"; Max-Age=1\r\n"
				+ "Set-Cookie: name1=\"value1\"; Domain=domain\r\n"
				+ "Set-Cookie: name2=\"value2\"; Path=path\r\n"
				+ "Set-Cookie: name3=\"value3\"; HTTPonly\r\n"
				+ "Set-Cookie: name4=\"value4\"; Domain=domain; Path=path; Max-Age=1; HTTPonly\r\n"
				+ "\r\n" + "";
		RequestContext context = new RequestContext(bos, null, null, null);
		context.addRCCookie(new RCCookie("name1", "value1", 1, null, null,
				false));
		context.addRCCookie(new RCCookie("name1", "value1", null, "domain",
				null, false));
		context.addRCCookie(new RCCookie("name2", "value2", null, null, "path",
				false));
		context.addRCCookie(new RCCookie("name3", "value3", null, null, null,
				true));
		context.addRCCookie(new RCCookie("name4", "value4", 1, "domain",
				"path", true));

		try {
			context.write("");
			assertTrue(output.equals(new String(bos.toByteArray())));
		} catch (IOException ignorable) {

		}
	}

}
