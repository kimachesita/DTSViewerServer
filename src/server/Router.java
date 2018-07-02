package server;

import java.util.HashMap;

import server.data.DataModel;
import server.http.HttpPacket;
import server.router.AuthFailedRouteHandler;
import server.router.GetProjectsRouteHandler;
import server.router.GetDirectActivityHoursRouteHandler;
import server.router.GetDirectActivitySummaryRouteHandler;
import server.router.GetDirectActivityByUserRouteHandler;
import server.router.GetIndirectActivityRouteHandler;
import server.router.GetWorkorderRouteHandler;
import server.router.LoginRouteHandler;
import server.router.NotImplementedRouteHandler;
import server.router.RouteHandler;
import server.router.WelcomeRouteHandler;

public class Router {

	private HashMap<String,RouteHandler> hm = new HashMap<>();

	public Router() {
		hm.put("GET/", new WelcomeRouteHandler());
		hm.put("POST/api/login", new LoginRouteHandler());
		hm.put("POST/api/projects", new GetProjectsRouteHandler());
		hm.put("POST/api/workorder", new GetWorkorderRouteHandler());
		hm.put("POST/api/directactivitysummary", new GetDirectActivitySummaryRouteHandler());
		hm.put("POST/api/directactivityhours", new GetDirectActivityHoursRouteHandler());
		hm.put("POST/api/directactivitybyuser", new GetDirectActivityByUserRouteHandler());
		hm.put("POST/api/indirectactivity", new GetIndirectActivityRouteHandler());
		hm.put("NotImplemented", new NotImplementedRouteHandler());
		hm.put("AuthFailed", new AuthFailedRouteHandler());
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
