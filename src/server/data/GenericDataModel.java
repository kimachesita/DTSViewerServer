package server.data;

import org.json.simple.JSONArray;

public class GenericDataModel extends DataModel {

	private JSONArray data;

	public GenericDataModel(JSONArray d) {
		data = d;
	}
	
	@Override
	public JSONArray getJSONArray() {
		return data;
	}
}
