package server.data;

import org.json.simple.JSONArray;


public class ErrorObjectDataModel extends DataModel {

	private String message = "Internal Server Error";

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getJSONArray() {
		JSONArray a = new JSONArray();
		a.add(message);
		return a;
	}

}
