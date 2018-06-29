package server.data;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DummyProjectsDataModel extends DataModel {

	@Override
	public JSONArray getJSONArray() {
		
		
		
		String data = "[{\"leader\":\"NSP bspg Manager\",\"totalhrs\":0.0,\"commonname\":\"Proj1\",\"manager\":\"NSP bspg Manager\",\"name\":\"Project 1\",\"dateregistered\":\"2001-01-01 05:00:16\",\"status\":\"a\"},"
				+ "{\"leader\":\"NSP bspg Manager\",\"totalhrs\":0.0,\"commonname\":\"Proj2\",\"manager\":\"NSP bspg Manager\",\"name\":\"Project 2\",\"dateregistered\":\"2002-02-01 05:00:16\",\"status\":\"a\"},"
				+ "{\"leader\":\"NSP bspg Manager\",\"totalhrs\":0.0,\"commonname\":\"Proj3\",\"manager\":\"NSP bspg Manager\",\"name\":\"Project 3\",\"dateregistered\":\"2003-03-01 05:00:16\",\"status\":\"a\"},"
				+ "{\"leader\":\"NSP bspg Manager\",\"totalhrs\":0.0,\"commonname\":\"Proj4\",\"manager\":\"NSP bspg Manager\",\"name\":\"Project 4\",\"dateregistered\":\"2004-04-01 05:00:16\",\"status\":\"a\"},"
				+ "{\"leader\":\"NSP bspg Manager\",\"totalhrs\":0.0,\"commonname\":\"Proj5\",\"manager\":\"NSP bspg Manager\",\"name\":\"Project 5\",\"dateregistered\":\"2005-05-01 05:00:16\",\"status\":\"a\"}]";

		try {
			JSONArray a = (JSONArray) new JSONParser().parse(data);
			return a;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		
	}

}
