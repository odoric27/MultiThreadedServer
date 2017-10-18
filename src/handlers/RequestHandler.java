package handlers;

import java.io.IOException;
import java.net.Socket;

public class RequestHandler implements Runnable {
	protected Socket sock = null;
	
	public RequestHandler(Socket socket) {
		this.sock = socket;
	}
	
	@Override
	public void run() {

		try {
			HttpRequest req = new HttpRequest(sock.getInputStream());
			HttpResponse resp = new HttpResponse(req);
			resp.write(sock.getOutputStream());
			sock.close();
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
