package server.db.model;

import java.sql.Date;

public class DirectActivity extends TimesheetActivity{
	private Activity projectActivity;
	private User user;
	private String workOrder;
	private String projid;
	private Date date;
	
	public DirectActivity() {
		super();
	}
	public Activity getProjectActivity() {
		return projectActivity;
	}
	public void setProjectActivity(Activity projectActivity) {
		this.projectActivity = projectActivity;
	}
	public User getUser() {
		return user; 
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getWorkOrder() {
		return workOrder;
	}
	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}
	public String getProjid() {
		return projid;
	}
	public void setProjid(String projid) {
		this.projid = projid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}	
}
