package server.router;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;

import server.data.DataModel;
import server.data.GenericDataModel;
import server.db.model.ProjectModel;
import server.http.HttpPacketStatus;

public class GetProjectListByStatusUserRouteHandler extends RouteHandler {

	private static final List<String> REQUIRED_PARAM = new ArrayList<>();
	static {
		REQUIRED_PARAM.add("status");
	}
	
	public GetProjectListByStatusUserRouteHandler() {
		super(HttpPacketStatus.OK, REQUIRED_PARAM);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel process(HashMap<String, String> param) {
		
		JSONArray res = new JSONArray();

		try {
			ProjectModel wo = new ProjectModel();
			res = wo.getProjectListByStatus(param.get(REQUIRED_PARAM.get(0)));
		} catch (SQLException e) {
			super.setStatus(HttpPacketStatus.SERVERERROR);
			res.add("Cannot connect to DB");
		}
		return new GenericDataModel(res);
	}
	
}
