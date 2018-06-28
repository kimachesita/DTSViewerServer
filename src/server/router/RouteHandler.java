package server.router;

import java.util.HashMap;

import server.http.HttpPacketStatus;
import server.model.Model;

public abstract class RouteHandler {
	
	private HttpPacketStatus status;
	
	public RouteHandler(HttpPacketStatus s) {
		status = s;
	}
	
	public abstract Model process(HashMap<String,String> param);
	
	public HttpPacketStatus getStatus() {
		return status;
	}
}
