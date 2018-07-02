package server.router;

import java.sql.SQLException;
import java.util.HashMap;

import org.json.simple.JSONArray;

import server.data.DataModel;
import server.data.GenericDataModel;
import server.db.model.User;
import server.http.HttpPacketStatus;

public class GetIndirectActivityRouteHandler extends RouteHandler {

	public GetIndirectActivityRouteHandler() {
		super(HttpPacketStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel process(HashMap<String, String> param) {

		String userId = param.get("userId");
		
		JSONArray j = new JSONArray();

		if(userId == null ) {
			userId = "";
		}
		

		try {
			User user = new User();
			j = user.getUserIndirectActivity(userId);
		} catch (SQLException e) {
			super.setStatus(HttpPacketStatus.SERVERERROR);
			j.add("Cannot connect to DB");
		}
		return new GenericDataModel(j);
	}

}
