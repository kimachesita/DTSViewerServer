package server.router;

import java.util.HashMap;

import server.data.DataModel;
import server.http.HttpPacketStatus;

public class NotImplementedRouteHandler extends RouteHandler {

	public NotImplementedRouteHandler() {
		super(HttpPacketStatus.NOTIMPLEMENTED);
	}

	@Override
	public DataModel process(HashMap<String, String> param) {
		return new server.data.NotImplementedDataModel();
	}

}
