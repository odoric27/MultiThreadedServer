package servers;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import handlers.RequestHandler;

public class MultiThreadedServer {
	protected int serverPort;
	protected ServerSocket socket;
    protected ExecutorService threadPool;
	
    public MultiThreadedServer() {
    		this(8081);
    }
    
	public MultiThreadedServer(int port) {
		this.serverPort = port;
		this.threadPool = Executors.newCachedThreadPool();
	}
	
	public MultiThreadedServer(int port, int numThreads) {
		this.serverPort = port;
		this.threadPool = Executors.newFixedThreadPool(numThreads);
	}
	
	public void run() {
		try {
			openSocket();
			System.out.println("Server is running on localhost: " + this.serverPort);
			while(!this.socket.isClosed()) {
				Socket client = socket.accept();
				System.out.println("Connection accepted from " + client.getInetAddress());
				threadPool.execute(new RequestHandler(client));
			}
		}
		catch(IOException e) {
			System.out.println("Server has stopped unexpectedly. ");
		}
		finally {
			closeSocket();
			this.threadPool.shutdown();
			System.out.println("Finishing tasks; server shutting down...");
		}
	}
	
	private synchronized void openSocket() throws IOException {
		this.socket = new ServerSocket(this.serverPort);
		
	}
	
	private synchronized void closeSocket() {
		try {
			this.socket.close();
		} catch (IOException e) {
			System.out.println("Error closing socket.");
		}
	}
	
	public static void main(String[] args) {
		MultiThreadedServer server = new MultiThreadedServer(8081, 5);
		server.run();
	}
}
