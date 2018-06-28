package server;

import java.util.HashMap;

import server.http.HttpPacket;
import server.model.Model;
import server.router.NotImplementedRouteHandler;
import server.router.RouteHandler;
import server.router.WelcomeRouteHandler;

public class Router {

	private HashMap<String,RouteHandler> hm = new HashMap<>();

	public Router() {
		hm.put("GET/api/welcome", new WelcomeRouteHandler());
		hm.put("NotImplemented", new NotImplementedRouteHandler());
	}

	public HttpPacket route(HttpPacket p) {
		String reqRoute = p.getReqMethod() + p.getReqRoute();
		RouteHandler h;
		try {

			h = hm.get(reqRoute);

		}catch(NullPointerException e) {
			h = hm.get("NotImplemented");
		}

		Model m = h.process(p.getReqParam());
		p.setResponse(h.getStatus(), m.getJSONArray().toJSONString());

		return p;
	}

}
