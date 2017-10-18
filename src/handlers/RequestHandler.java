package handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.io.PrintWriter;



public class RequestHandler implements Runnable {
	protected Socket sock = null;
	
	protected StringBuilder request = null;

	protected int contentLength = 0;
	protected PrintWriter out = null;
	protected BufferedReader in = null;
	
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
			e.printStackTrace();
		}
	}
}
