package server.db.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;

public class UserModel extends Model{

	public UserModel() throws SQLException {
		super();
	}
	
	public HashMap<String, String> getUser(String userid) throws SQLException {
		ResultSet rs = null;
		HashMap<String, String> user = new HashMap<>();
		try {
			Query query = new Query(QueryList.getStatement("userByUserId"));	
			query.addParam(userid);
			rs = executeQuery(query);		
			
			while (rs.next()) {
				user.put("id", rs.getString("id"));
				user.put("fullname", rs.getString("givenname") + " " + rs.getString("surname"));		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.getDb().closeResultSet(rs);
		}
		return user;
	}
	//previously getuser
	public JSONArray getUserByUsername(String username) {
		ResultSet rs = null;
		ArrayList<HashMap<String, String>> userList = new ArrayList<>();
		try {
			
			Query query = new Query(QueryList.getStatement("userByUsername"));	
			query.addParam(username);
			rs = executeQuery(query);	

			if (rs.next()) {
				HashMap<String, String> user = new HashMap<>();
				user.put("userid", rs.getString("userid"));
				user.put("fullname", rs.getString("givenname") + " " + rs.getString("surname") );
				user.put("email", rs.getString("email"));
				user.put("position", rs.getString("position"));
				user.put("department", rs.getString("department"));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.getDb().closeResultSet(rs);
		}
		return toJSONArray(userList);
	}
	

}
