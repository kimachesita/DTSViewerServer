package server;

import java.util.HashMap;

import server.data.DataModel;
import server.http.HttpPacket;
import server.router.GetProjectsRouteHandler;
import server.router.NotImplementedRouteHandler;
import server.router.RouteHandler;
import server.router.WelcomeRouteHandler;

public class Router {

	private HashMap<String,RouteHandler> hm = new HashMap<>();

	public Router() {
		hm.put("GET/", new WelcomeRouteHandler());
		hm.put("POST/api/projects", new GetProjectsRouteHandler());
		hm.put("NotImplemented", new NotImplementedRouteHandler());
	}

	public HttpPacket route(HttpPacket p) {
		String reqRoute = p.getReqMethod() + p.getReqRoute();
		RouteHandler h;
		
		if((h = hm.get(reqRoute)) == null) {
			h = hm.get("NotImplemented");
		}

		DataModel m = h.process(p.getReqParam());
		p.setResponse(h.getStatus(), m.getJSONArray().toJSONString());

		return p;
	}

}
