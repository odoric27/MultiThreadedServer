package handlers;

import java.util.ArrayList;
import java.util.UUID;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
	private final String VERSION = "HTTP/1.1";
	private final String CRLF = "\r\n";
	private StringBuilder response;
	private ArrayList<String> headers;
	private HttpRequest req;
	private CookieHandler ch;
	private byte[] body;
	private boolean isNewCookie;
	
	public HttpResponse(HttpRequest req) {
		this.req = req;
		response = new StringBuilder();
		headers = new ArrayList<String>();
		
		switch(req.getMethod()) {
			
			case "GET":
				
				buildHeader(Status._200);
				buildBody("Welcome!");
				break;
			
			case "POST":
				
				buildHeader(Status._201);
				buildBody("Welcome!", req.getBody());
				break;
			
			default:
				buildHeader(Status._405);
		}
	}
	
	private void buildHeader(Status status) {
		headers.add(VERSION + " " + status);
		headers.add("Connection: close");
		headers.add("Server: MultiThreadedServer");
		headers.add("Cache-Control: public,max-age=10");
		CookieHandler ch = new CookieHandler();
		synchronized(ch) {
			if(req.hasCookie())
				ch.handle(req.getCookieID());
			else
				ch.handle();
		}
		headers.add(ch.getNewCookie());		
		isNewCookie = (ch.unmatchedCookie()) ? true  : false;
	}
	
	private void buildBody(String response) {
		if(!isNewCookie()) {
			response = response.substring(0, response.length() - 1);
			response += " back!\n";
		}
		this.body = response.getBytes();
	}
	
	private void buildBody(String response, char[] body) {
		if(!isNewCookie()) {
			response = response.substring(0, response.length() - 1);
			response += " back!\n";
		}
		else
			response += "\n";
		
		for(char c : body)
			response += c;
		
		this.body = response.getBytes();
	}
	
	private boolean isNewCookie() {
		return isNewCookie;
	}
	
	
	public void write(OutputStream os) throws IOException {
		DataOutputStream out = new DataOutputStream(os);
		
		for(String s : headers)
			out.writeBytes(s + CRLF);
		out.writeBytes(CRLF);
		
		if(body != null)
			out.write(body);
			
		out.writeBytes(CRLF);
		out.close();
	}
}

/*
 * 
 * Subset of HTTP Status Codes
 * 
 */

enum Status {
	_200("200 OK"), //
	_201("201 Created"), //
	_204("204 No Content"), //
	_400("400 Bad Request"), //
	_401("401 Unauthorized"), //
	_403("403 Forbidden"), //
	_404("404 Not Found"), //
	_405("405 Method Not Allowed"), //
	_500("500 Internal Server Error"), //
	_501("501 Not Implemented"); //


	private final String status;

	Status(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}
}
