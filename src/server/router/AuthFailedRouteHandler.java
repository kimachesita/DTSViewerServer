package server.router;

import java.util.HashMap;

import org.json.simple.JSONArray;

import server.data.DataModel;
import server.data.GenericDataModel;
import server.http.HttpPacketStatus;

public class AuthFailedRouteHandler extends RouteHandler {

	public AuthFailedRouteHandler() {
		super(HttpPacketStatus.UNAUTH);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel process(HashMap<String, String> param) {
		JSONArray j = new JSONArray();
		String message = "Authentication Failure. Please login";
		j.add(message);
		return new GenericDataModel(j);
	}

}
