package server.db.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;

public class IndirectActivityModel extends Model{

	public IndirectActivityModel() throws SQLException {
		super();
	}

	public JSONArray getUserIndirectActivity(String userid, String dateFrom, String dateTo ) {
		ResultSet rs = null;
		ArrayList<HashMap<String, String>> indirList = new ArrayList<>();
		try {
			Query query = new Query(QueryList.getStatement("indirectActivityByUser"));		
			query.addParam(userid);
			query.addParam(dateFrom);
			query.addParam(dateTo);
			rs = executeQuery(query);	
		
			while(rs.next()) {
				HashMap<String, String> indir = new HashMap<>();
				indir.put("userid", rs.getString("userid"));
				indir.put("actid", rs.getString("actid"));
				indir.put("actname", rs.getString("actname"));
				indir.put("actcatname", rs.getString("actcatname"));
				indir.put("date", rs.getString("date").toString());
				indir.put("reghours", rs.getString("reghours"));
				indir.put("othours", rs.getString("othours"));
				indirList.add(indir);
			}				
			 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.getDb().closeResultSet(rs);
		}
		return toJSONArray(indirList);
	}
	
}
