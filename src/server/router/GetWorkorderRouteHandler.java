package server.router;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;

import server.data.DataModel;
import server.data.GenericDataModel;
import server.db.model.DirectActivityModel;
import server.http.HttpPacketStatus;

public class GetWorkorderRouteHandler extends RouteHandler {

	
	private static final List<String> REQUIRED_PARAM = new ArrayList<>();
	static {

		REQUIRED_PARAM.add("projectId");
		REQUIRED_PARAM.add("dateFrom");
		REQUIRED_PARAM.add("dateTo");
	}
	
	public GetWorkorderRouteHandler() {
		super(HttpPacketStatus.OK, REQUIRED_PARAM);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DataModel process(HashMap<String, String> param) {

		JSONArray res = new JSONArray();
			
		try {
			DirectActivityModel wo = new DirectActivityModel();
			res = wo.getDirectActivityByProject(param.get(REQUIRED_PARAM.get(0)),
					param.get(REQUIRED_PARAM.get(1)),
					param.get(REQUIRED_PARAM.get(2))
					);
		} catch (SQLException e) {
			super.setStatus(HttpPacketStatus.SERVERERROR);
			res.add("Cannot connect to DB");
		}
		return new GenericDataModel(res);
	}

}
