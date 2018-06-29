package server.router;

import java.util.HashMap;

import server.data.DataModel;
import server.data.WelcomeDataModel;
import server.http.HttpPacketStatus;

public class WelcomeRouteHandler extends RouteHandler {

	public WelcomeRouteHandler() {
		super(HttpPacketStatus.OK);
	}
	
	@Override
	public DataModel process(HashMap<String,String> param) {
		return new WelcomeDataModel();
	}

}
