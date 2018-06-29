package server.data;

import org.json.simple.JSONArray;


public class WelcomeDataModel extends DataModel {
	
	private String message = "DTS Viewer Server. Access API via http://172.28.61.10:3000/api/*";

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getJSONArray() {
		JSONArray a = new JSONArray();
		a.add(message);
		return a;
	}

}
