package server.router;

import java.sql.SQLException;
import java.util.HashMap;

import org.json.simple.JSONArray;

import server.data.DataModel;
import server.db.model.Project;
import server.http.HttpPacketStatus;

public class GetProjectsRouteHandler extends RouteHandler {

	public GetProjectsRouteHandler() {
		super(HttpPacketStatus.OK);

	}

	private class ProjectDataModel extends DataModel{

		private JSONArray data;

		public ProjectDataModel(JSONArray d) {
			data = d;
		}
		

		@Override
		public JSONArray getJSONArray() {
			return data;
		}

	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public DataModel process(HashMap<String, String> param) {
		
		String uid = param.get("user_id");
		JSONArray j = null;
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
		return new ProjectDataModel(j);
		
	}

}
