package server.db.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class User extends Model {
	public User() throws SQLException {
		super();
		// TODO Auto-generated constructor stub
	}

	private int id;
	private String givenName;
	private String middleName;
	private String surname;
	private String position;
	private String userName;
	private String password;
	private String department;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public User getUser(String username, String password) throws SQLException {
		User user = new User();
		try {					
			ResultSet rs = null;
			PreparedStatement ps = super.getDb().getConnect().prepareStatement("SELECT * FROM fos_user WHERE username=? AND password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();

			while (rs.next()){
				user.setId(rs.getInt("id"));
				user.setGivenName(rs.getString("givenname"));
				user.setMiddleName(rs.getString("middlename"));
				user.setSurname(rs.getString("surname"));
				//user.setPosition(rs.getString("position"));
				user.setUserName(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				//user.setDepartment(rs.getString("department"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public User getUser(int userid) throws SQLException {
		User user = new User();
		try {			
			
			ResultSet rs = null;
			PreparedStatement ps = super.getDb().getConnect().prepareStatement("SELECT * FROM fos_user WHERE id=?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()){
				user.setId(rs.getInt("id"));
				user.setGivenName(rs.getString("givenname"));
				user.setMiddleName(rs.getString("middlename"));
				user.setSurname(rs.getString("surname"));
				//user.setPosition(rs.getString("position"));
				user.setUserName(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				//user.setDepartment(rs.getString("department"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public String getFullName() {
		return 	givenName + " " + middleName + " " + surname;
	}

}
