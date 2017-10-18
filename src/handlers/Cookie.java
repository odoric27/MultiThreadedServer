package handlers;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Cookie {
	private String id;
	private String lastVisit;
	private String expires;
	
	
	
	public Cookie() {
		this.id = UUID.randomUUID().toString();
		this.lastVisit = new Date().toString();
		Date date = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		this.expires = c.getTime().toString();
	}
	
	public Cookie(String id) {
		this.id = id;
		this.lastVisit = new Date().toString();
		Date date = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		this.expires = c.getTime().toString();
	}
	
	public String toString() {
		String result = String.format(
				"Set-Cookie: sessionId=%s---%s; Expires=%s",
				id, lastVisit, expires);
		
		return result;
	}
	
	public String getID() {
		return this.id;
	}
}
