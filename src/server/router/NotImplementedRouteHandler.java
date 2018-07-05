package server.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import server.data.DataModel;
import server.http.HttpPacketStatus;

public class NotImplementedRouteHandler extends RouteHandler {

	private static final List<String> REQUIRED_PARAM = new ArrayList<>();
	
	public NotImplementedRouteHandler() {
		super(HttpPacketStatus.NOTIMPLEMENTED, REQUIRED_PARAM);
	}

	public DataModel process(HashMap<String, String> param) {
		return new server.data.NotImplementedDataModel();
	}

}
