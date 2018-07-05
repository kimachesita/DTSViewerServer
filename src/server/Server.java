package server;

import java.io.IOException;
import java.net.*;

import server.auth.Auth;
import server.db.DBConnection;

public class Server {

	public ServerSocket serverSocket;

	public Server(Integer p){
		
		boolean DBServiceStarted = false;
		boolean AuthServiceStarted = false;
		
		try {
			AuthServiceStarted = initializeAuth();
			DBServiceStarted = DBConnection.getInstance().connect();
			if(AuthServiceStarted && DBServiceStarted) { 
				System.out.println("Server: Startup check passed.");
				System.out.println("Server: Creating ServerSocket at port: " + p);
				serverSocket = new ServerSocket(p);
			}else 
				System.exit(1);
		} catch (IOException e) {
			System.out.println("Server: Error Opening ServerSocket: " + e.getMessage());
			System.exit(1);
		}
	}
	
	public boolean initializeAuth() {
		System.out.println("Server: Creating Auth Service");
		Auth.instance().addExceptions("POST/api/login");
		Auth.instance().addExceptions("GET/");
		Auth.instance().start();
		return true;
	}

	public void listen(){
		System.out.println("Server: Server Listening at " + serverSocket.getLocalSocketAddress());
		while(true) {
			try {

				HttpRequestConnection c = new HttpRequestConnection(serverSocket.accept());
				Thread t = new Thread(c);
				t.start();
			} catch (IOException e) {
				System.out.println("Server: Error Accepting Connections: " + e.getMessage());
				break;
			}

		}
		this.close();
	}

	public void close() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("Server: Error closing server socket: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		Integer port = Integer.valueOf(Config.instance().getValue("port"));
		Server httpd = new Server(port);
		httpd.listen();
	}
}
