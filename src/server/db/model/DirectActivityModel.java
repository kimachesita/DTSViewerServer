package server.db.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;

public class DirectActivityModel extends Model{

	public DirectActivityModel() throws SQLException {
		super();
	}

	//previously getWorkOrderList of WorkOrder
	public JSONArray getDirectActivityByProject(String projectid, String dateFrom, String dateTo) {
		ResultSet rs = null;		
		ArrayList<HashMap<String, String>> woList = new ArrayList<>();

		try {			
			Query query = new Query(QueryList.getStatement("directActivityByProject"));			
			query.addParam(projectid);
			query.addParam(dateFrom);
			query.addParam(dateTo);
			rs = executeQuery(query);	

			while (rs.next()){
				HashMap<String, String> wo = new HashMap<>();

				wo.put("projid", rs.getString("projid"));				
				wo.put("workorder", rs.getString("workorder"));
				wo.put("projactid", rs.getString("projactid"));
				wo.put("projactname", rs.getString("projactname"));
				wo.put("phaseid", rs.getString("phaseid"));
				wo.put("phasename", rs.getString("phasename"));
				wo.put("actid", rs.getString("actid"));
				wo.put("actname", rs.getString("actname"));
				wo.put("actcatname", rs.getString("actcatname"));	
				wo.put("date", rs.getString("date"));
				wo.put("reghours", rs.getString("reghours"));
				wo.put("othours", rs.getString("othours"));
				woList.add(wo);;	
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			getDb().closeResultSet(rs);
		}
		return toJSONArray(woList);
	}
	//previously getUsersSummaryOfDirectActivity
	public JSONArray getDirectActivityByUser(String projid, String userid, String dateFrom, String dateTo) {
		ResultSet rs = null;
		ArrayList<HashMap<String, String>> dirList = new ArrayList<>();
		try {

			Query query = new Query(QueryList.getStatement("directActivityByUser"));			
			query.addParam(projid);
			query.addParam(userid);
			query.addParam(dateFrom);
			query.addParam(dateTo);
			rs = executeQuery(query);	

			while (rs.next()){
				HashMap<String, String> dirAct = new HashMap<>();
				dirAct.put("projid", rs.getString("projid"));				
				dirAct.put("workorder", rs.getString("workorder"));
				dirAct.put("userid", rs.getString("userid"));
				dirAct.put("projactid", rs.getString("projactid"));
				dirAct.put("projactname", rs.getString("projactname"));
				dirAct.put("phaseid", rs.getString("phaseid"));
				dirAct.put("phasename", rs.getString("phasename"));
				dirAct.put("actid", rs.getString("actid"));
				dirAct.put("actname", rs.getString("actname"));
				dirAct.put("actcatname", rs.getString("actcatname"));	
				dirAct.put("date", rs.getString("date"));
				dirAct.put("reghours", rs.getString("reghours"));
				dirAct.put("othours", rs.getString("othours"));		
				dirList.add(dirAct);		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			getDb().closeResultSet(rs);
		}
		return toJSONArray(dirList);
	}	


	public JSONArray getUserDirectActivityTotalHours (String projid, String dateFrom, String dateTo) {
		ResultSet rs = null;
		ArrayList<HashMap<String, String>> dirList = new ArrayList<>();
		try {		
			Query query = new Query(QueryList.getStatement("userDirectActivityTotalHours"));			
			query.addParam(projid);
			query.addParam(dateFrom);
			query.addParam(dateTo);
			rs = executeQuery(query);

			while (rs.next()) {
				HashMap<String, String> dir = new HashMap<>();
				dir.put("projid", rs.getString("projid"));
				dir.put("userid", rs.getString("userid"));
				dir.put("fullname", rs.getString("givenname") + " " + rs.getString("surname"));
				dir.put("totalReg", rs.getString("reghours"));
				dir.put("totalOT", rs.getString("othours"));
				dirList.add(dir);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getDb().closeResultSet(rs);
		}
		return toJSONArray(dirList);
	}
	
	
	public JSONArray getDirectActivitySummaryHoursByEngineer(String projectid, String userid, String dateFrom, String dateTo) {
		ResultSet rs = null;
		ArrayList<HashMap<String, String>> dirList = new ArrayList<>();
		try {		
			Query query = new Query(QueryList.getStatement("directActivitySummaryHoursByEngineer"));			
			query.addParam(projectid);
			query.addParam(userid);
			query.addParam(dateFrom);
			query.addParam(dateTo);
			rs = executeQuery(query);
			
			while (rs.next()){
				HashMap<String, String> dir = new HashMap<>();
				dir.put("projid", rs.getString("projid"));
				dir.put("userid", rs.getString("userid"));
				dir.put("workorder", rs.getString("workorder"));
				dir.put("date", rs.getDate("date").toString());
				dir.put("totalReg", rs.getString("totalReg"));
				dir.put("totalOT", rs.getString("totalOT"));
				dirList.add(dir);

			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			getDb().closeResultSet(rs);
		}
		return toJSONArray(dirList);
	}
	
	
}
