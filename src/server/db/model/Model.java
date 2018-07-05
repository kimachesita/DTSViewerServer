package server.db.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.db.DBConnection;

public abstract class Model {

	public Model() throws SQLException {
		QueryList list = new QueryList();
	}

	protected DBConnection getDb() {
		return DBConnection.getInstance();
	}

	protected ResultSet executeQuery(Query query) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			if(query.getParam()!=null) {
				ps = DBConnection.getInstance().getConnect().prepareStatement(query.getStatement());
				for (int i = 1; i <= query.getParam().size(); i++) {
					ps.setObject(i, query.getParam().get(i-1));
				}
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	@SuppressWarnings("unchecked")
	protected JSONArray toJSONArray (ArrayList<HashMap<String, String>> arr) {
		JSONArray jArr = new JSONArray();
		for(HashMap<String, String> hm : arr) {
			jArr.add(toJSONObject(hm));
		}
		return jArr;
	}

	protected JSONObject toJSONObject (HashMap<String, String> hm) {
		JSONObject jObj = new JSONObject(hm);	
		return jObj; 
	}
}

