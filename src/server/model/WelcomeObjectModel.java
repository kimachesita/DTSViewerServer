package server.model;

import org.json.simple.JSONArray;


public class WelcomeObjectModel extends Model {
	
	private String message = "Hello, World!";

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getJSONArray() {
		JSONArray a = new JSONArray();
		a.add(message);
		return a;
	}

}
