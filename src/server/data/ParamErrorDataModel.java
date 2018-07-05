package server.data;

import org.json.simple.JSONArray;

public class ParamErrorDataModel extends DataModel {

	private String message = "API Parameter Error";

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getJSONArray() {
		JSONArray a = new JSONArray();
		a.add(message);
		return a;
	}

}
