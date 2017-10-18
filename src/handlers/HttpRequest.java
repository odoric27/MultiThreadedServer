package handlers;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class HttpRequest {
	private BufferedReader in;
	private String method;
	private String path;
	private String version;
	private int length;
	private char[] body;
	private boolean hasCookie;
	private String[] cookieValues;
	private String[] subCookieValues;
	private HashMap<String, String> headers;
	private static int counter = 1;

	public HttpRequest(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		headers = new HashMap<String, String>();
		
		String line = in.readLine();
		parseRequest(line);
		
		System.out.println("\nRequest: " + counter++);
		while((line = in.readLine()) != null && !(line.equals(""))) {
			System.out.println(line);
			parseHeaders(line);
		}
		if(headers.get("Cookie") != null ) {
			hasCookie = true;
			parseCookie(headers.get("Cookie"));
		}
		
		parseBody(in);

	}
		
	private void parseRequest(String line) {
		String[] temp = line.split(" ");
		method = temp[0];
		path = temp[1];
		version = temp[2];
	}
	
	private void parseHeaders(String line) {
		String[] temp = line.split(":\\s");
		headers.put(temp[0], temp[1]);
	}
	
	private void parseBody(BufferedReader in) throws IOException {
		if(headers.get("Content-Length") != null) {
			length = Integer.parseInt(headers.get("Content-Length"));
			this.body = new char[length];
			in.read(body);
		}
	}
	
	private void parseCookie(String cookie) {
		cookieValues = cookie.split("; ");
		subCookieValues = cookieValues[0].split("=")[1].split("---");
	}
	
	public String getMethod() {
		return this.method;
	}
	
	public String[] getCookieValues() {
		return cookieValues;
	}
	
	public String[] getSubCookieValues() {
		return subCookieValues;
	}
	
	public boolean hasCookie() {
		return hasCookie;
	}
	
	public String getCookieID() {
		return subCookieValues[0];
	}
	
	public char[] getBody() {
		return body;
	}
}
	