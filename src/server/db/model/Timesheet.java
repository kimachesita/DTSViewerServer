package server.db.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Timesheet extends Model {
	private int id;
	private User user;
	private Date date;
	private Date dateSubmitted;
	private Date dateApproved;
	private float dailyHours;
	private String status;
	private ArrayList<Activity> actList; 
	public Timesheet() throws SQLException {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDateSubmitted() {
		return dateSubmitted;
	}
	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}
	public Date getDateApproved() {
		return dateApproved;
	}
	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}
	public float getDailyHours() {
		return dailyHours;
	}
	public void setDailyHours(float dailyHours) {
		this.dailyHours = dailyHours;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<Activity> getActList() {
		return actList;
	}
	public void setActList(ArrayList<Activity> actList) {
		this.actList = actList;
	}
//TO CONTINUE
	public Timesheet getUserTimeSheet(String userID, String projid, String dateFrom, String dateTo) {
		ResultSet rs = null;
		Timesheet ts = null;
		try {
			ts = new Timesheet();
			PreparedStatement ps = super.getDb().getConnect().prepareStatement("SELECT "
					+ "dir.pworeg_project_id AS projid, "
					+ "u.givenname AS givenname, "
					+ "u.surname AS surname, "
					+ "u.id as userid, "
					+ "ts.date as date, "
					+ "dir.regularHours as reghours, "
					+ "dir.OTHours as othours "
					+ "FROM dts_timesheet as ts\r\n" 
					+ "JOIN dts_direct_activity AS dir ON ts.id = dir.dtstimesheet_id\r\n" 
					+ "LEFT JOIN fos_user as u ON ts.user_id = u.id\r\n" 
					+ "WHERE ts.user_id = 14 AND dir.pworeg_project_id = 2 AND ts.date BETWEEN '2015-04-01' AND '2015-04-01' ORDER BY date;");
			ps.setString(1, userID);
			ps.setString(2, projid);
			ps.setString(3, dateFrom);
			ps.setString(4, dateTo);
			rs = ps.executeQuery();

			while(rs.next()) {
				//TO CONTINUE
				ts.setId(rs.getInt("id"));
			}
			rs.close();
			super.getDb().close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	
}
