package server;

import java.io.IOException;
import java.net.Socket;

import server.auth.Auth;
import server.http.HttpPacket;
import server.http.HttpParser;
import server.http.HttpWriter;

public class HttpRequestConnection implements Runnable{
	
	private Socket socket;
	private HttpParser httpParser;
	private HttpWriter httpWriter;
	private Router router;

	public HttpRequestConnection(Socket s) {
		socket = s;
		httpParser = new HttpParser(s);
		httpWriter = new HttpWriter(s);
		router = new Router();
	}

	@Override
	public void run() {
		HttpPacket p = httpParser.parseRequest();
		System.out.println("HttpRequest: Processing " + p.getReqMethod() + p.getReqRoute() + " from " + socket.getRemoteSocketAddress());
		p = Auth.instance().authenticator(p);
		httpWriter.write(router.route(p));
			
		try {
			socket.close();
				
		} catch (IOException e) {
			System.out.println("Error Closing Socket" + socket.getRemoteSocketAddress());
		}
	}

}
