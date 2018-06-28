package server.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;


public class HttpWriter {

	private Writer out;

	public HttpWriter(Socket s) {

		try {
			out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(),"US-ASCII"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void write(HttpPacket p) {
		try {
			out.write(p.buildPacketMsg());
			out.flush();
		} catch (IOException e) {
			System.out.println("Error Writing to Socket");
		}
	}

}
