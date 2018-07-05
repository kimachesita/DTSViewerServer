package server.router;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;

import server.data.DataModel;
import server.data.GenericDataModel;
import server.db.model.IndirectActivityModel;
import server.http.HttpPacketStatus;

public class GetIndirectActivityRouteHandler extends RouteHandler {

	private static final List<String> REQUIRED_PARAM = new ArrayList<>();
	static {

		REQUIRED_PARAM.add("userId");
		REQUIRED_PARAM.add("dateFrom");
		REQUIRED_PARAM.add("dateTo");
	}
	
	public GetIndirectActivityRouteHandler() {
		super(HttpPacketStatus.OK, REQUIRED_PARAM);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel process(HashMap<String, String> param) {

		JSONArray res = new JSONArray();
		
		try {
			IndirectActivityModel user = new IndirectActivityModel();	
			res = user.getUserIndirectActivity(param.get(REQUIRED_PARAM.get(0)),
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
