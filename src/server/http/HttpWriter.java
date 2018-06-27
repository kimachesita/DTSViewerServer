package server.http;

import java.io.IOException;
//import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;


public class HttpWriter {
	
	//private PrintWriter writer;
	private PrintStream writer;
	
	public HttpWriter(Socket s) {
		//writer = new PrintWriter(new OutputStreamWriter(System.out));
		try {
			writer = new PrintStream(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void write(HttpPacket p) {
			writer.print(p.getResStatusLine() + "\r\n");
			writer.print(p.getResHeader());
			writer.print("\r\n");
			writer.print(p.getResBody());
	}

}
