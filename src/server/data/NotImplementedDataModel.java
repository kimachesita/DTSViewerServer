package server.data;

import org.json.simple.JSONArray;

public class NotImplementedDataModel extends DataModel {

	private String message = "API not implemented";

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getJSONArray() {
		JSONArray a = new JSONArray();
		a.add(message);
		return a;
	}

}
