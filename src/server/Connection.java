package server;

import java.net.Socket;

import server.http.HttpPacket;
import server.http.HttpParser;
import server.http.HttpWriter;
import server.router.Router;

public class Connection implements Runnable{
	
	private HttpParser httpParser;
	private HttpWriter httpWriter;
	public Router router;

	public Connection(Socket s) {
		httpParser = new HttpParser(s);
		httpWriter = new HttpWriter(s);
		router = new Router();
	}

	@Override
	public void run() {
		HttpPacket p = httpParser.parse();
		router.route(p);
		httpWriter.write(p);
	}

}
