package server.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import server.data.DataModel;
import server.data.WelcomeDataModel;
import server.http.HttpPacketStatus;

public class WelcomeRouteHandler extends RouteHandler {
	
	private static final List<String> REQUIRED_PARAM = new ArrayList<>();

	public WelcomeRouteHandler() {
		super(HttpPacketStatus.OK, REQUIRED_PARAM);
	}
	
	@Override
	public DataModel process(HashMap<String,String> param) {
		return new WelcomeDataModel();
	}

}
