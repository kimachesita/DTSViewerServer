package server.db.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;

public class ProjectModel extends Model {

	public ProjectModel() throws SQLException {
		super();
	}
	
	public JSONArray getProjectList () {		
		ResultSet rs = null;
		ArrayList<HashMap<String, String>> projList = new ArrayList<>();

		try {
			Query query = new Query(QueryList.getStatement("projectList"));			
			rs = executeQuery(query);
			
			while (rs.next()){
				HashMap<String, String> proj = new HashMap<>();
				proj.put("projid", rs.getString("projid"));
				proj.put("projectname", rs.getString("projname"));
				proj.put("commonname", rs.getString("projcommonname"));
				proj.put("dateregistered", rs.getString("dateregistered"));
				proj.put("manager", new UserModel().getUser(rs.getString("managerid")).get("fullname"));
				proj.put("leader", new UserModel().getUser(rs.getString("leaderid")).get("fullname"));							
				proj.put("status", rs.getString("status"));		
				proj.put("memberCount", String.valueOf(getMemberCountOfProject(rs.getInt("projid"))));		
				proj.put("totalhrs", String.valueOf(getProjectHours(rs.getInt("projid"))));
				projList.add(proj);					
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.getDb().closeResultSet(rs);
		}
		return toJSONArray(projList);
	}
	
	public JSONArray getProjectListByStatus (String status) {		
		ResultSet rs = null;
		ArrayList<HashMap<String, String>> projList = new ArrayList<>();

		try {
			Query query = new Query(QueryList.getStatement("projectListByStatus"));		
			query.addParam(status);
			rs = executeQuery(query);
			
			while (rs.next()){
				HashMap<String, String> proj = new HashMap<>();
				proj.put("projid", rs.getString("projid"));
				proj.put("projectname", rs.getString("projname"));
				proj.put("commonname", rs.getString("projcommonname"));
				proj.put("dateregistered", rs.getString("dateregistered"));
				proj.put("manager", new UserModel().getUser(rs.getString("managerid")).get("fullname"));
				proj.put("leader", new UserModel().getUser(rs.getString("leaderid")).get("fullname"));							
				proj.put("status", rs.getString("status"));		
				proj.put("memberCount", String.valueOf(getMemberCountOfProject(rs.getInt("projid"))));		
				proj.put("totalhrs", String.valueOf(getProjectHours(rs.getInt("projid"))));
				projList.add(proj);					
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.getDb().closeResultSet(rs);
		}
		return toJSONArray(projList);
	}
	
	private int getMemberCountOfProject (int projectid) {
		int ret = 0;
		ResultSet rs = null;
		try {			
			Query query = new Query(QueryList.getStatement("memberCountOfProject"));	
			query.addParam(projectid);
			rs = executeQuery(query);
			rs.next();
			ret =  rs.getInt("membercount");
	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.getDb().closeResultSet(rs);
		}
		return ret;
	}
	
	private Double getProjectHours (int projectid) {
		Double ret = null;
		ResultSet rs = null;
		try {			
			Query query = new Query(QueryList.getStatement("projectHours"));	
			query.addParam(projectid);
			rs = executeQuery(query);		
			rs.next();
			ret =  rs.getDouble("totalHrs");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			super.getDb().closeResultSet(rs);
		}	
		return ret;
	}	
}
