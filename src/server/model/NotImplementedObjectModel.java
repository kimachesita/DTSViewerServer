package server.model;

import org.json.simple.JSONObject;

public class NotImplementedObjectModel extends Model {

	private String message = "API not implemented";

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getJSONObject() {
		JSONObject o = new JSONObject();
		o.put("message",message);
		return o;
	}
}
