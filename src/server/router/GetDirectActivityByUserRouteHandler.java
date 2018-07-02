package server.router;

import java.sql.SQLException;
import java.util.HashMap;

import org.json.simple.JSONArray;

import server.data.DataModel;
import server.data.GenericDataModel;
import server.db.model.WorkOrder;
import server.http.HttpPacketStatus;

public class GetDirectActivityByUserRouteHandler extends RouteHandler {

	public GetDirectActivityByUserRouteHandler() {
		super(HttpPacketStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel process(HashMap<String, String> param) {
		String projectId = param.get("projectId");
		String userId = param.get("userId");
		String dateFrom = param.get("dateFrom");
		String dateTo = param.get("dateTo");
		
		JSONArray j = new JSONArray();
		
		if(projectId == null ) {
			projectId = "";
		}
		
		if(userId == null ) {
			userId = "";
		}
		
		if(dateFrom == null ) {
			dateFrom = "";
		}
		
		if(dateTo == null ) {
			dateTo = "";
		}
			
		
		try {
			WorkOrder wo = new WorkOrder();
			j = wo.getDirectActivityByUser(projectId,userId,dateFrom,dateTo);
		} catch (SQLException e) {
			super.setStatus(HttpPacketStatus.SERVERERROR);
			j.add("Cannot connect to DB");
		}
		return new GenericDataModel(j);
	}

}
