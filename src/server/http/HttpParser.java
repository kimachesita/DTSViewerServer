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

	//method to parse request @ server side
	public HttpPacket parseRequest() {

		StringBuilder b = new StringBuilder();
		while(true) {
			String line;
			try {
				line = in.readLine();
				if(line == null) break;
				if(!line.matches("")) {
					b.append(line + "\r\n");
				}else {
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}

		}

		HttpPacket p = new HttpPacket(b.toString());

		//System.out.println(p.getContentType());
		//p.getContentType().matches("application/x-www-form-urlencoded"
		if( p.getContentLength() != 0) {
			StringBuilder body = new StringBuilder();
			for(int i = 0; i < p.getContentLength();i++) {
				try {
					int c = in.read();
					body.append((char) c);
				} catch (IOException e) {
					System.out.println("Error parsing body");
				}

			}
			p.setRequestBody(body.toString());
		}

		return p;
	}

	//method to parse response @ client side
	public HttpPacket parseResponse() {

		StringBuilder b = new StringBuilder();
		while(true) {
			String line;
			try {
				line = in.readLine();
				if(line == null) break;
				if(!line.matches("")) {
					b.append(line + "\r\n");
				}else {
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}

		}

		HttpPacket p = new HttpPacket(b.toString());

		if( p.getContentLength() != 0 ) {
			StringBuilder body = new StringBuilder();
			for(int i = 0; i < p.getContentLength();i++) {
				try {
					int c = in.read();
					body.append((char) c);
				} catch (IOException e) {
					System.out.println("Error parsing body");
				}

			}
			p.setResponseBody(body.toString());
		}

		return p;
	}

}
