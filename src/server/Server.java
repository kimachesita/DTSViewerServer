package server;

import java.io.IOException;
import java.net.*;

import server.auth.Auth;

public class Server {

	public ServerSocket serverSocket;

	public Server(Integer p){
		initializeAuth();
		System.out.println("Creating ServerSocket at port: " + p);
		try {
			serverSocket = new ServerSocket(p);
		} catch (IOException e) {
			System.out.println("Error Opening ServerSocket: " + e.getMessage());
		}
	}
	
	public void initializeAuth() {
		System.out.println("Creating Auth Service");
		Auth.instance().addExceptions("POST/api/login");
		Auth.instance().addExceptions("GET/");
		Auth.instance().start();
	}

	public void listen(){
		System.out.println("Server Listening at " + serverSocket.getLocalSocketAddress());
		while(true) {
			try {

				Connection c = new Connection(serverSocket.accept());
				Thread t = new Thread(c);
				t.start();
			} catch (IOException e) {
				System.out.println("Error Accepting Connections: " + e.getMessage());
				break;
			}

		}
		this.close();
	}

	public void close() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("Error closing server socket: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		Server httpd = new Server(3000);
		httpd.listen();
	}
}
