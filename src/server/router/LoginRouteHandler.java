package server.router;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import server.auth.Auth;
import server.auth.Token;
import server.data.DataModel;
import server.data.GenericDataModel;
import server.db.model.UserModel;
import server.http.HttpPacketStatus;

public class LoginRouteHandler extends RouteHandler {

	private static final List<String> REQUIRED_PARAM = new ArrayList<>();
	static {

		REQUIRED_PARAM.add("username");
		REQUIRED_PARAM.add("password");
	}

	public LoginRouteHandler() {
		super(HttpPacketStatus.OK, REQUIRED_PARAM);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel process(HashMap<String, String> param) {

		boolean authenticated = Auth.instance().isAuthorize(param.get(REQUIRED_PARAM.get(0)), param.get(REQUIRED_PARAM.get(1)));

		JSONArray res = new JSONArray();

		try {
			UserModel user = new UserModel();
			
			if(res != null && authenticated) {
				
				res = user.getUserByUsername(param.get(REQUIRED_PARAM.get(0)));
				Token tok = Auth.instance().addToken(param.get(REQUIRED_PARAM.get(0)));
				JSONObject temp = (JSONObject) res.get(0);
				temp.put("AuthToken", tok.getToken());

			}else {
				super.setStatus(HttpPacketStatus.UNAUTH);
				res.add("Authentication Failure, wrong credentials, or user not yet registered");
			}
		} catch (SQLException e) {
			super.setStatus(HttpPacketStatus.SERVERERROR);
			res.add("Cannot connect to DB");
		} catch (IndexOutOfBoundsException e) {
			super.setStatus(HttpPacketStatus.SERVERERROR);
			res.add("User not found.");
		}

		return new GenericDataModel(res);
	}
}
