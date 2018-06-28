package server;

import java.io.IOException;
import java.net.*;

public class Server {

	public ServerSocket serverSocket;

	public Server(Integer p){
		System.out.println("Creating ServerSocket at port: " + p);
		try {
			serverSocket = new ServerSocket(p);
		} catch (IOException e) {
			System.out.println("Error Opening ServerSocket: " + e.getMessage());
		}
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
