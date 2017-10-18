package handlers;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class CookieHandler {
	
	private String currentCookie;
	private String newCookie;
	
	private static ConcurrentHashMap<String, String> COOKIE_STORAGE = 
			new ConcurrentHashMap<String, String>();
	
	public void handle(String id) {
		Cookie cookie = null;
		if(COOKIE_STORAGE.get(id) != null) {
			//System.out.println("found cookie!");
			currentCookie = COOKIE_STORAGE.get(id);
			cookie = new Cookie(id);
		}
		else {
			//System.out.println("creating new cookie!");
			cookie = new Cookie();
			id = cookie.getID();
		}
			
		
		newCookie = cookie.toString();
		COOKIE_STORAGE.put(id, newCookie);
	}
	
	public void handle() {
		Cookie cookie = new Cookie();
		String id = cookie.getID();
		newCookie = cookie.toString();
		COOKIE_STORAGE.put(id, newCookie);
	}
	
	public String getNewCookie() {
		return newCookie;
	}
	
	public String getCurrentCookie() {
		return currentCookie;
	}
	
	public boolean unmatchedCookie() {
		return (currentCookie == null);
	}
}
