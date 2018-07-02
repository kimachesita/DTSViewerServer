package server.db.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class User extends Model{
	private int id;
	private String givenName;
	private String middleName;
	private String surname;
	private String position;
	private String department;
	public User() throws SQLException {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	public User getUser(String username, String password) {
		User user = null;
		try {					
			ResultSet rs = null;
			user = new User();
			PreparedStatement ps = super.getDb().getConnect().prepareStatement("SELECT * FROM fos_user WHERE username=? AND password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();

			while (rs.next()){
				user.setId(rs.getInt("id"));
				user.setGivenName(rs.getString("givenname"));
				user.setMiddleName(rs.getString("middlename"));
				user.setSurname(rs.getString("surname"));
//				user.setPosition(rs.getString("position"));
//				user.setDepartment(rs.getString("department"));
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public User getUser(int userid) {
		User user = null;
		try {	
			ResultSet rs = null;
			user = new User();
			PreparedStatement ps = super.getDb().getConnect().prepareStatement("SELECT * FROM fos_user WHERE id=?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()){
				user.setId(rs.getInt("id"));
				user.setGivenName(rs.getString("givenname"));
				user.setMiddleName(rs.getString("middlename"));
				user.setSurname(rs.getString("surname"));
//				user.setPosition(rs.getString("position"));
//				user.setDepartment(rs.getString("department"));
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public JSONArray getUsersSummaryOfDirectActivity(String projid, String dateFrom, String dateTo) {
		ArrayList<DirectActivity>  dirArr = new ArrayList<>();
		try {					
			ResultSet rs = null;
			PreparedStatement ps = super.getDb().getConnect().prepareStatement("SELECT "
					+ "dir.pworeg_project_id AS projid, "
					+ "u.givenname AS givenname, "
					+ "u.surname AS surname, "
					+ "u.id as userid, "
					+ "ts.date as date, "
					+ "dir.regularHours as reghours, "
					+ "dir.OTHours as othours "
					+ "FROM dts_timesheet as ts " 
					+ "JOIN dts_direct_activity AS dir ON ts.id = dir.dtstimesheet_id " 
					+ "LEFT JOIN fos_user as u ON ts.user_id = u.id " 
					+ "WHERE ts.user_id = 14 AND dir.pworeg_project_id = ? AND ts.date BETWEEN ? AND ? ORDER BY date;");
			ps.setString(1, projid);
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			rs = ps.executeQuery();

			while (rs.next()){
				DirectActivity dir = new DirectActivity();
				dir.setProjid(rs.getString("projid"));
				User u = new User();
				u.setId(rs.getInt("userid"));
				u.setGivenName(rs.getString("givenname"));
				u.setSurname(rs.getString("surname"));
				dir.setUser(u);
				dir.setDate(rs.getDate("date"));				
				dir.setRegHours(rs.getDouble("reghours"));
				dir.setOTHours(rs.getDouble("othours"));
				
				dirArr.add(dir);
			}
			rs.close();
			super.getDb().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toJSONArray(dirArr);
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getUserDirectActivityTotalHours (String projid, String dateFrom, String dateTo) {
		ResultSet res = null;
		JSONArray jarr = new JSONArray();
		try {			
			PreparedStatement ps = super.getDb().getConnect().prepareStatement("SELECT "
					+ "u.givenname AS givenname, "
					+ "u.surname AS surname, "
					+ "u.id as userid, "
					+ "SUM(regularHours) AS totalReg, "
					+ "SUM(OTHours) AS totalOT "
					+ "FROM dts_timesheet as ts " 
					+ "JOIN dts_direct_activity AS dir ON ts.id = dir.dtstimesheet_id " 
					+ "LEFT JOIN fos_user as u ON ts.user_id = u.id " 
					+ "WHERE ts.user_id = 14 AND dir.pworeg_project_id = ? AND ts.date BETWEEN ? AND ? ORDER BY date;");		
			ps.setString(1, projid);
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			res = ps.executeQuery();
			res.next();
			JSONObject obj = new JSONObject();
			obj.put("fullname", res.getString("givenname") + res.getString("surname"));
			obj.put("userid", res.getString("userid"));
			obj.put("totalReg", res.getString("totalReg"));
			obj.put("totalOT", res.getString("totalOT"));
			jarr.add(obj);
			
			res.close();
			super.getDb().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return jarr;
	}	
	
	@SuppressWarnings("unchecked")
	public JSONArray getUserIndirectActivity(String userid) {
		ResultSet res = null;
		JSONArray jarr = new JSONArray();
		try {			
			PreparedStatement ps = super.getDb().getConnect().prepareStatement("SELECT "
					+ "ts.user_id AS userid, "
					+ "act.id AS actid, "
					+ "act.name AS actname, "
					+ "cat.name AS actcatname, "
					+ "date, "
					+ "indirect.regularHours AS reghours, "
					+ "indirect.OTHours AS othours "
					+ "FROM dts_indirect_activity as indirect " 
					+ "LEFT JOIN dts_activity AS act ON indirect.activity_id =act.id " 
					+ "LEFT JOIN dts_activity_category AS cat ON act.activitycategory = cat.id " 
					+ "LEFT JOIN dts_timesheet AS ts ON indirect.dtstimesheet_id = ts.id " 
					+ "WHERE ts.user_id = ? ORDER BY act.id, date;");		
			ps.setString(1, userid);
			res = ps.executeQuery();
			
			while(res.next()) {
				JSONObject obj = new JSONObject();
				obj.put("userid", res.getString("userid"));
				obj.put("actid", res.getString("actid"));
				obj.put("actname", res.getString("actname"));
				obj.put("actcatname", res.getString("actcatname"));
				obj.put("date", res.getString("date"));
				obj.put("reghours", res.getString("reghours"));
				obj.put("othours", res.getString("othours"));
				jarr.add(obj);
			}
			res.close();
			super.getDb().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return jarr;

	}

	
	public String getFullName() {
		return 	givenName + " " + surname;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray toJSONArray(ArrayList<DirectActivity> arr) {			
		JSONArray jarr = new JSONArray();
		for (DirectActivity p: arr) {
			jarr.add(toJSONObject(p));
		}
		return jarr;
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject(DirectActivity d) {
		JSONObject obj = new JSONObject();
		obj.put("projid", d.getProjid());
		obj.put("userid", d.getUser().getId());
		obj.put("fullname", d.getUser().getFullName());
		obj.put("date", d.getDate().toString());
		obj.put("reghours", d.getRegHours());
		obj.put("othours", d.getOTHours());
		return obj;	
	}
	

}
