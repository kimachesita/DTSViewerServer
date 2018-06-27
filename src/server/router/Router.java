package server.router;

import java.util.HashMap;

import server.http.HttpPacket;
import server.model.Model;

public class Router {
	
	private HashMap<String,RouteHandler> hm = new HashMap<>();
	
	public Router() {
		hm.put("GET/api/welcome", new WelcomeRouteHandler());
	}

	public void route(HttpPacket p) {
		String reqRoute = p.getReqMethod() + p.getReqRoute();
		try {
			RouteHandler h = hm.get(reqRoute);
			Model m = h.process(p.getReqParam());
			p.setResStatusLine(h.getStatusCode(), h.getStatusDescription());
			p.setResBody(m.getJSONObject().toJSONString());
		}catch(NullPointerException e) {
			//hm.get("ERR/501");
			System.out.println("Null");
		}
		
		
	}

}
