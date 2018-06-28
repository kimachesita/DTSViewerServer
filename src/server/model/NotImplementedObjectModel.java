package server.model;

import org.json.simple.JSONArray;

public class NotImplementedObjectModel extends Model {

	private String message = "API not implemented";

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getJSONArray() {
		JSONArray a = new JSONArray();
		a.add(message);
		return a;
	}

}
