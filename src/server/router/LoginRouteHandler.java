package server.router;

import java.util.HashMap;

import org.json.simple.JSONArray;

import server.auth.Auth;
import server.auth.Token;
import server.data.DataModel;
import server.data.GenericDataModel;
import server.http.HttpPacketStatus;

public class LoginRouteHandler extends RouteHandler {

	public LoginRouteHandler() {
		super(HttpPacketStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel process(HashMap<String, String> param) {
		JSONArray j = new JSONArray();
		String username = param.get("username");
		String password = param.get("password");
		
		if(username == null) username = "";
		if(password == null) password = "";
		
		//authenticate to LDAP Here
		boolean authenticated = Auth.instance().isAuthorize(username, password);
		
		if(authenticated) {
			Token tok = Auth.instance().addToken(username);
			j.add(tok.getToken());
		}else{
			j.add("Authentication Failure, wrong credentials, try again");
		}
		
		return new GenericDataModel(j);
	}
}
