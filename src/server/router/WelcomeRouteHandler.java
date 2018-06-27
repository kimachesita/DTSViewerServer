package server.router;

import java.util.HashMap;

import server.model.Model;
import server.model.WelcomeObjectModel;

public class WelcomeRouteHandler extends RouteHandler {

	public WelcomeRouteHandler() {
		super(200, "OK");
	}
	
	@Override
	public Model process(HashMap<String,String> param) {
		return new WelcomeObjectModel();
	}

}
