package server.model;

import org.json.simple.JSONObject;

public class ErrorObjectModel extends Model {

	private String message = "Internal Server Error";

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getJSONObject() {
		JSONObject o = new JSONObject();
		o.put("message",message);
		return o;
	}

}
