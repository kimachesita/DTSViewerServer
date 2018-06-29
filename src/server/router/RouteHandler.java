package server.router;

import java.util.HashMap;

import server.data.DataModel;
import server.http.HttpPacketStatus;

public abstract class RouteHandler {
	
	private HttpPacketStatus status;
	
	public RouteHandler(HttpPacketStatus s) {
		status = s;
	}
	
	public void setStatus(HttpPacketStatus s) {
		status = s;
	}
	
	public HttpPacketStatus getStatus() {
		return status;
	}
	
	public abstract DataModel process(HashMap<String,String> param);
}
