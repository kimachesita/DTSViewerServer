package server.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;

import server.auth.Auth;
import server.data.DataModel;
import server.data.GenericDataModel;
import server.http.HttpPacketStatus;

public class LogoutRouteHandler extends RouteHandler {
	
	private static final List<String> REQUIRED_PARAM = new ArrayList<>();
	
	private String token;
	
	public LogoutRouteHandler() {
		super(HttpPacketStatus.OK, REQUIRED_PARAM);
	}
	
	public void setToken(String s) {
		token = s;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel process(HashMap<String, String> param) {
		
		JSONArray res = new JSONArray();
		
		if(token == null) {
			super.setStatus(HttpPacketStatus.BADREQUEST);
			res.add("Error Logging out user.");
		}else {
			Auth.instance().removeToken(token);
			res.add("User successfully logged out");
			Auth.instance().printTokenList();
		}
		
		return new GenericDataModel(res);
	}

}
