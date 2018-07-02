package server.router;

import java.sql.SQLException;
import java.util.HashMap;

import org.json.simple.JSONArray;

import server.data.DataModel;
import server.data.GenericDataModel;
import server.db.model.User;
import server.http.HttpPacketStatus;

public class GetDirectActivityHoursRouteHandler extends RouteHandler {

	public GetDirectActivityHoursRouteHandler() {
		super(HttpPacketStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel process(HashMap<String, String> param) {
		String projectId = param.get("projectId");
		String dateFrom = param.get("dateFrom");
		String dateTo = param.get("dateTo");

		JSONArray j = new JSONArray();

		if(projectId == null ) {
			projectId = "";
		}

		if(dateFrom == null ) {
			dateFrom = "";
		}

		if(dateTo == null ) {
			dateTo = "";
		}

		try {
			User user = new User();
			j = user.getUserDirectActivityTotalHours(projectId, dateFrom , dateTo);
		} catch (SQLException e) {
			super.setStatus(HttpPacketStatus.SERVERERROR);
			j.add("Cannot connect to DB");
		}
		return new GenericDataModel(j);
	}
}
