package server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class HttpParser {

	private BufferedReader in;

	public HttpParser(Socket s){
		try {

			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		}catch (IOException e) {
			System.out.println("Error Initializing Input Stream");
		}
		
	}

	public HttpPacket parse() {

		HttpPacket request = null;

		try {
			
			request = new HttpPacket();

			//parse request header line
			StringBuilder b = new StringBuilder();
			b.setLength(0);
			while(true) {
				int c = in.read();
				if(c == '\r') {
					in.read();
					break;
				}
				b.append((char) c);
			}
			request.setReqHeaderLine(b.toString());

			//parse request header
			while(true) {
				b.setLength(0);
				while(true) {
					int c = in.read();
					if(c == '\r') {
						in.read();
						break;
					}
					b.append((char) c);
				}
				if(b.length() == 0) break;
				request.appendReqHeader(b.toString());
			}

			//parse body
			//System.out.println("parsing body");
			//request.printHeaderLine();
			//request.printHeader();
			//System.out.println(request.getContentLength());
			StringBuilder body = new StringBuilder();
			if(request.getContentLength() != 0) {
				for(int i=0; i < request.getContentLength(); i++) {
					body.append((char) in.read());
				}
			}
			request.setReqBody(body.toString());


		}catch(OutOfMemoryError e) {
			//e.printStackTrace();
			//solve this
		}catch(IOException e){
			System.out.println("Error Communicating to Socket.");
		}finally {
			try {
				in.close();
			} catch (IOException e) {}
		}

		return request;
	}
}
