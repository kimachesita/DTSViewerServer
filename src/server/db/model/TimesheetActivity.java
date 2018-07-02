package server.db.model;

import java.util.Date;

public abstract class TimesheetActivity {
	private int id;
	private Double regHours;
	private Double OTHours;
	private Date dateCreated;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getRegHours() {
		return regHours;
	}
	public void setRegHours(Double regHours) {
		this.regHours = regHours;
	}
	public Double getOTHours() {
		return OTHours;
	}
	public void setOTHours(Double oTHours) {
		OTHours = oTHours;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	
}
