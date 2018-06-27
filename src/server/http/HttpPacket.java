package server.http;

import java.util.HashMap;
import java.util.Map;

public class HttpPacket {
	
	private String[] reqHeaderLine = new String[3];
	private HashMap<String,String> reqHeader = new HashMap<>();
	private String reqBody;
	private HashMap<String,String> reqParam = new HashMap<>();
	private String resStatusLine;
	private HashMap<String,String> resHeader = new HashMap<>();
	private String resBody;
	

	public int setReqHeaderLine(String l) {
		String[] c = l.split("\\s");
		if(c.length != 3) {
			return -1;
		}else {
			reqHeaderLine[0] = c[0];	//method
			reqHeaderLine[1] = c[1];	//route
			reqHeaderLine[2] = c[2];	//HTTP version
			return 0;
		}
	}
	
	public String getReqMethod() {
		return reqHeaderLine[0];
	}
	
	public String getReqRoute() {
		return reqHeaderLine[1];
	}
	
	public String getReqHTTPVersion() {
		return reqHeaderLine[2];
	}

	public int appendReqHeader(String l) {
		String[] h = l.split(":");
		if(h.length != 2) {
			return -1;
		}else {
			reqHeader.put(h[0], h[1].trim());
			return 0;
		}
		
	}
	
	public void setReqBody(String b) {
		reqBody = b;
	}
	
	public String getReqBody() {
		return reqBody;
	}
	
	public HashMap<String,String> getReqParam(){
		return reqParam;
	}
	
	public Integer getContentLength() {
		try {
			String val = reqHeader.get("content-length");
			return Integer.valueOf(val);
		}catch(Exception e) {
			return 0;
		}
	}
	
	public void setResStatusLine(Integer c, String cD) {
		resStatusLine = reqHeaderLine[2] + " " + c.toString() + " " + cD;
	}
	
	public void setResBody(String json) {

		resHeader.put("Content-type", "text/json");
		resHeader.put("Content-length", json.length() + "");
		resHeader.put("Connection", "close");
		resBody = json;
	}
	
	public String getResStatusLine() {
		return resStatusLine;
	}
	
	public String getResHeader() {
		String res = "";
		for(Map.Entry<String, String> entry: resHeader.entrySet()) {
			res += entry.getKey() + ": " + entry.getValue() + "\r\n";
		}
		return res;
	}
	
	public String getResBody() {
		return resBody;
	}
	
	
	
	
	//for debugging purposes
	public void printHeaderLine() {
		System.out.println("Method: " + reqHeaderLine[0]);
		System.out.println("URI: " + reqHeaderLine[1]);
		System.out.println("HTTP v: " + reqHeaderLine[2]);
	}
	
	public void printHeader() {
		for(Map.Entry<String, String> entry: reqHeader.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}

}
