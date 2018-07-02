package server.db.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WorkOrder extends Model{
	private String workOrderNumber;
	private String projectid;
	private String userid;
	private ProjectActivity projActivity;
	private Date date;
	private Double regHours;
	private Double OTHours;
	
	public WorkOrder() throws SQLException {
		super();
	}
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}
	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public ProjectActivity getProjectActivity() {
		return projActivity;
	}
	public void setProjectActivity(ProjectActivity projectactivity) {
		this.projActivity = projectactivity;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public ProjectActivity getProjActivity() {
		return projActivity;
	}
	public void setProjActivity(ProjectActivity projActivity) {
		this.projActivity = projActivity;
	}
	
	public JSONArray getWorkOrderList(String projectid, String dateFrom, String dateTo) {
		ResultSet rs = null;
		ArrayList<WorkOrder>  woList = new ArrayList<>();
		try {
			PreparedStatement ps = super.getDb().getConnect()
					.prepareStatement("SELECT "
							+ "proj.id as projid, "
							+ "proj.nspprojectname AS projname, "
							+ "wo.workordernumber AS workorder, "
							+ "pa.activity_id AS projactid, "
							+ "pa.name as projactname, "
							+ "ph.id AS phaseid, "
							+ "ph.name AS phasename, "							
							+ "cat.id AS actcatID, "
							+ "cat.name AS actcatname, "
							+ "a.id AS actid,  "
							+ "a.name AS actname, "
							+ "ts.date AS date, "
							+ "act.regularHours AS reghours, "
							+ "act.OTHours AS othours " 
							+ "FROM pworeg_project AS proj " 
							+ "JOIN pworeg_wo_project AS wo ON proj.id = wo.project_id " 
							+ "JOIN dts_direct_activity AS act ON wo.workordernumber = act.pworeg_workorder_id AND wo.project_id = act.pworeg_project_id AND wo.project_id = ? " 
							+ "JOIN dts_timesheet AS ts ON ts.id = act.dtstimesheet_id AND ts.date BETWEEN  ? AND ? " 
							+ "JOIN dts_project_activity AS pa ON act.projectactivity_id = pa.activity_id " 
							+ "LEFT JOIN dts_activity AS a ON a.id = pa.activity_id " 
							+ "LEFT JOIN dts_activity_category AS cat ON cat.id = pa.activity_category_id "
							+ "LEFT JOIN dts_phase AS ph ON ph.id = pa.phase_id order by wo.workordernumber, a.id;");
			ps.setString(1, projectid);
			ps.setString(2, dateFrom);
			ps.setString(3, dateTo);
			rs = ps.executeQuery();	
			while (rs.next()){
				WorkOrder wo = new WorkOrder();
				wo.setProjectid(rs.getString("projid"));
				wo.setWorkOrderNumber(rs.getString("workorder"));
				wo.setProjectActivity(new ProjectActivity(
						rs.getInt("projactid"), 
						rs.getString("projactname"), 
						new Activity(rs.getInt("actid"), rs.getString("actname"), rs.getString("actcatname")),					
						rs.getString("phaseid"), rs.getString("phasename"))
						);
				wo.setDate(rs.getDate("date"));
				wo.setRegHours(rs.getDouble("reghours"));	
				wo.setOTHours(rs.getDouble("othours"));			
				woList.add(wo);		
			}					
			rs.close();
			super.getDb().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return toJSONArray(woList);
	}
	
	public JSONArray getDirectActivityByUser(String projectid, String userid, String dateFrom, String dateTo) {
		ResultSet rs = null;
		ArrayList<WorkOrder>  woList = new ArrayList<>();
		try {
			PreparedStatement ps = super.getDb().getConnect()
					.prepareStatement("SELECT "
							+ "proj.id as projid, "
							+ "proj.nspprojectname AS projname, "
							+ "ts.user_id AS userid, "
							+ "wo.workordernumber AS workorder, "
							+ "pa.activity_id AS projactid, "
							+ "pa.name as projactname, "
							+ "ph.id AS phaseid, "
							+ "ph.name AS phasename, "							
							+ "cat.id AS actcatID, "
							+ "cat.name AS actcatname, "
							+ "a.id AS actid,  "
							+ "a.name AS actname, "
							+ "ts.date AS date, "
							+ "act.regularHours AS reghours, "
							+ "act.OTHours AS othours " 
							+ "FROM pworeg_project AS proj " 
							+ "JOIN pworeg_wo_project AS wo ON proj.id = wo.project_id " 
							+ "JOIN dts_direct_activity AS act ON wo.workordernumber = act.pworeg_workorder_id AND wo.project_id = act.pworeg_project_id " 
							+ "JOIN dts_timesheet AS ts ON ts.id = act.dtstimesheet_id " 
							+ "JOIN dts_project_activity AS pa ON act.projectactivity_id = pa.activity_id " 
							+ "LEFT JOIN dts_activity AS a ON a.id = pa.activity_id " 
							+ "LEFT JOIN dts_activity_category AS cat ON cat.id = pa.activity_category_id "
							+ "LEFT JOIN dts_phase AS ph ON ph.id = pa.phase_id "
							+ "WHERE wo.project_id = ? AND ts.user_id = ? AND ts.date BETWEEN ? AND ? ORDER BY ts.date;");
			ps.setString(1, projectid);
			ps.setString(2, userid);
			ps.setString(3, dateFrom);
			ps.setString(4, dateTo);
			rs = ps.executeQuery();	
			while (rs.next()){
				WorkOrder wo = new WorkOrder();
				wo.setProjectid(rs.getString("projid"));
				wo.setUserid(rs.getString("userid"));
				wo.setWorkOrderNumber(rs.getString("workorder"));
				wo.setProjectActivity(new ProjectActivity(
						rs.getInt("projactid"), 
						rs.getString("projactname"), 
						new Activity(rs.getInt("actid"), rs.getString("actname"), rs.getString("actcatname")),					
						rs.getString("phaseid"), rs.getString("phasename"))
						);
				wo.setDate(rs.getDate("date"));
				wo.setRegHours(rs.getDouble("reghours"));	
				wo.setOTHours(rs.getDouble("othours"));			
				woList.add(wo);		
			}					
			rs.close();
			super.getDb().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return toJSONArray(woList);
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray toJSONArray(ArrayList<WorkOrder> arr) {			
		JSONArray jarr = new JSONArray();
		for (WorkOrder p: arr) {
			jarr.add(toJSONObject(p));
		}
		return jarr;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject(WorkOrder w) {
		JSONObject obj = new JSONObject();
		obj.put("projid", w.projectid);
		obj.put("userid", w.userid);
		obj.put("workorder", w.workOrderNumber);
		obj.put("projactid", w.projActivity.getProjActid());
		obj.put("projactname", w.projActivity.getName());
		obj.put("phaseid", w.projActivity.getPhaseId());
		obj.put("phasename", w.projActivity.getPhaseName());
		obj.put("actid", w.projActivity.getActivity().getId());
		obj.put("actname", w.projActivity.getActivity().getName());
		obj.put("actcatname", w.projActivity.getActivity().getCategory());	
		obj.put("date", w.date.toString());
		obj.put("reghours", w.regHours);
		obj.put("othours", w.OTHours);
		return obj;	
	}
}
