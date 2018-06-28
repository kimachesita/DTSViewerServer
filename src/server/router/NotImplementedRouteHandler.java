package server.router;

import java.util.HashMap;

import server.http.HttpPacketStatus;
import server.model.Model;

public class NotImplementedRouteHandler extends RouteHandler {

	public NotImplementedRouteHandler() {
		super(HttpPacketStatus.NOTIMPLEMENTED);
	}

	@Override
	public Model process(HashMap<String, String> param) {
		return new server.model.NotImplementedObjectModel();
	}

}
