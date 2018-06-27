package server.router;

import java.util.HashMap;

import server.model.Model;

public abstract class RouteHandler {
	
	private Integer statusCode;
	private String statusDesc;
	
	public RouteHandler(Integer code,String desc) {
		statusCode = code;
		statusDesc = desc;
	}
	
	public abstract Model process(HashMap<String,String> param);
	
	public Integer getStatusCode() {
		return statusCode;
	}
	
	public String getStatusDescription() {
		return statusDesc;
	}
}
