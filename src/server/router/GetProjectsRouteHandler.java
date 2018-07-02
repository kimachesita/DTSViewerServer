package server.router;

import java.sql.SQLException;
import java.util.HashMap;

import org.json.simple.JSONArray;

import server.data.DataModel;
import server.data.GenericDataModel;
import server.db.model.Project;
import server.http.HttpPacketStatus;

public class GetProjectsRouteHandler extends RouteHandler {

	public GetProjectsRouteHandler() {
		super(HttpPacketStatus.OK);

	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public DataModel process(HashMap<String, String> param) {
		
		String uid = param.get("userId");
		JSONArray j = new JSONArray();
		if(uid == null) {
			uid = "";
		}
		try {
			Project proj = new Project();
			j = proj.getProjectList(uid);
		} catch (SQLException e) {
			super.setStatus(HttpPacketStatus.SERVERERROR);
			j.add("Cannot connect to DB");
		}
		return new GenericDataModel(j);
		
	}

}
