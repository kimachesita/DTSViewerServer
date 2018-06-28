package server;

import java.io.IOException;
import java.net.Socket;

import server.http.HttpPacket;
import server.http.HttpParser;
import server.http.HttpWriter;

public class Connection implements Runnable{
	
	private Socket socket;
	private HttpParser httpParser;
	private HttpWriter httpWriter;
	public Router router;

	public Connection(Socket s) {
		socket = s;
		httpParser = new HttpParser(s);
		httpWriter = new HttpWriter(s);
		router = new Router();
	}

	@Override
	public void run() {
		
		System.out.println("Running thread for connection" + socket.getRemoteSocketAddress());
		HttpPacket p = httpParser.parseRequest();
		httpWriter.write(router.route(p));
		
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("Error Closing Socket" + socket.getRemoteSocketAddress());
		}
	}

}
