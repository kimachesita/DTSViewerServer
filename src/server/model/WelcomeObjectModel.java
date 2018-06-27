package server.model;

import org.json.simple.JSONObject;

public class WelcomeObjectModel extends Model {
	
	private String message = "Hello, World!";

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getJSONObject() {
		JSONObject o = new JSONObject();
		o.put("message",message);
		return o;
	}

}
