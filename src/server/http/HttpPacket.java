package server.http;

import java.util.HashMap;
import java.util.Map;

public class HttpPacket {
	
	//variables
	private String[] headerLine = new String[3];
	private HashMap<String,String> header = new HashMap<>();
	private HashMap<String,String> param = new HashMap<>();
	private String body;
	

	//used for HttpPacket Request Creation on serverside
	public HttpPacket(String s) {
		
		clearPacket();
		
		if(s.matches("")) {
			//do nothing,create null packet
			return;
		}else {
			String[] parsed = s.split("\r\n");
			
			//parse headerLine
			String[] rhl = parsed[0].split(" ");
			if(rhl.length != 3) {
				//do nothing,create null packet
				return;
			}
			headerLine[0] = rhl[0];	//method
			headerLine[1] = rhl[1];  //route
			headerLine[2] = rhl[2];  //HttpVersion
			
			
			//parse header
			for(int i=1;i< parsed.length; i++) {
				//do doublecheck here
				String[] h = parsed[i].split(":");
				header.put(h[0], h[1].trim());
			}
			
		}
		
	}
	
	
	//used for HttpPacket Request creation on client side
	public HttpPacket(String method,String route,String p) {
		clearPacket();
		
		
		//for header
		headerLine[0] = method;
		headerLine[1] = route;
		headerLine[2] = "HTTP/1.1";
		
		//add aux header entry here
		header.put("Connection", "close");
		
		//for request parameters as content
		if(p!= null) {
			header.put("Content-type", "text/JSON");
			header.put("content-length", p.length() + "");
			body = p;
		}
		
	}
	
	//used by parser to parsed and record request parameters
	public void setRequestBody(String b) {
		body = b;
		String[] param = b.split("&");
		for(int i=0;i<param.length;i++) {
			this.param.put(param[i].split("=")[0], param[i].split("=")[1]);
		}
	}
	
	public void setResponseBody(String b) {
		body = b;
	}
	
	public String getReqMethod() {
		return headerLine[0];
	}
	
	public String getReqRoute() {
		return headerLine[1];
	}
	
	public String getReqHTTPVersion() {
		return headerLine[2];
	}

	
	
	public HashMap<String,String> getReqParam(){
		return param;
	}
	
	public Integer getContentLength() {
		try {
			String val = header.get("content-length");
			return Integer.valueOf(val);
		}catch(Exception e) {
			return 0;
		}
	}
	
	
	public String getBody() {
		return body;
	}
	//methods for http response creation
	
	public void setResponse(HttpPacketStatus s,String jsonStr) {
	
		clearPacket();
		
		
		headerLine[0] = "HTTP/1.1";
		headerLine[1] = s.getKey();
		headerLine[2] = s.getValue();
		header.put("Content-type", "text/JSON");
		header.put("content-length", jsonStr.length() + "");
		header.put("Connection", "close");
		body = jsonStr;
	}
	
	public String buildPacketMsg() {
		StringBuilder res = new StringBuilder();
		
		res.append(headerLine[0] + " " + headerLine[1] + " " + headerLine[2] + "\r\n");
		
		for(Map.Entry<String, String> entry: header.entrySet()) {
			res.append(entry.getKey() + ": " + entry.getValue() + "\r\n");
		}
		
		res.append("\r\n");
		
		res.append(body);
		
		return res.toString();
	}
	
	//utility functions
	private void clearPacket() {
		
		
		headerLine[0] = null;
		headerLine[1] = null;
		headerLine[2] = null;
		
		header.clear();
		param.clear();
		body = "";
		
	}
	
	
	//for debugging purposes
	public void printHeaderLine() {
		System.out.println("Method: " + headerLine[0]);
		System.out.println("URI: " + headerLine[1]);
		System.out.println("HTTP v: " + headerLine[2]);
	}
	
	public void printHeader() {
		for(Map.Entry<String, String> entry: header.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
	
	public void printParam() {
		for(Map.Entry<String, String> entry: param.entrySet()) {
			System.out.println("param>> " + entry.getKey() + " : " + entry.getValue());
		}
	}

}
