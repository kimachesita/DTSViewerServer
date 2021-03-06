package server.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;

import server.data.DataModel;
import server.data.GenericDataModel;
import server.http.HttpPacketStatus;

public class AuthFailedRouteHandler extends RouteHandler {

	private static final List<String> REQUIRED_PARAM = new ArrayList<>();
	
	public AuthFailedRouteHandler() {
		super(HttpPacketStatus.UNAUTH, REQUIRED_PARAM);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel process(HashMap<String, String> param) {
		JSONArray res = new JSONArray();
		String message = "Authentication Failure. Please login";
		res.add(message);
		return new GenericDataModel(res);
	}

}
