package server.db.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Project extends Model {
	public Project() throws SQLException {
		super();
	}

	private int id;
	private String name;
	private String commonName;
	private User leader;
	private User manager;
	private String status;
	private Date startDate;
	private Date endDate;
	private Double regHours;
	private Double OTHours;
	private Double totalHrs;
	private int memCount;
	private String dateRegistered;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public User getLeader() {
		return leader;
	}
	public void setLeader(User leader) {
		this.leader = leader;
	}
	public User getManager() {
		return manager;
	}
	public void setManager(User manager) {
		this.manager = manager;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public Double getTotalHours() {
		return totalHrs;
	}
	public int getMemCount() {
		return memCount;
	}
	public void setMemCount(int memCount) {
		this.memCount = memCount;
	}
	public Double getTotalHrs() {
		return totalHrs;
	}
	public void setTotalHrs(Double totalHrs) {
		this.totalHrs = totalHrs;
	}
	public String getDateRegistered() {
		return dateRegistered;
	}
	public void setDateRegistered(String dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

	public JSONArray getProjectList(String userID) {		
		ResultSet rs = null;
		ArrayList<Project>  projList = new ArrayList<>();
		try {
			PreparedStatement ps = super.getDb().getConnect().prepareStatement("SELECT "
					+ "proj.id AS projid, "
					+ "nspprojectname AS projname, "
					+ "projectcommonname AS projcommonname, "
					+ "dateregistered AS dateregistered, "
					+ "proj.projectstartdate AS startdate, "
					+ "proj.projectenddate AS enddate, "
					+ "manager AS managerid, "
					+ "leader AS leaderid, "
					+ "u.givenname AS givenname, " 
					+ "u.surname AS surname, "
					+ "proj.status AS status "
					+ "FROM pworeg_project AS proj " 
					+ "INNER JOIN fos_user u ON proj.manager = u.id " 
					+ "WHERE proj.manager = ?;");
			ps.setString(1, userID);
			rs = ps.executeQuery();	

			while (rs.next()){
				Project p = new Project();
				p.setId(rs.getInt("projid"));
				p.setName(rs.getString("projname"));
				p.setCommonName(rs.getString("projcommonname"));			
				p.setDateRegistered(rs.getString("dateregistered"));	
				
				User manager = new User();				
				p.setManager(manager.getUser(rs.getInt("managerid")));
				
				User leader = new User();				
				p.setLeader(leader.getUser(rs.getInt("leaderid")));

				p.setStatus(rs.getString("status"));
				p.setStartDate(rs.getDate("startdate"));
				p.setEndDate(rs.getDate("enddate"));	
				p.setMemCount(getMemberCount(p.getId()));
				p.setTotalHrs(getProjectHours(p.getId()));						
				projList.add(p);		
			}					
			rs.close();
			super.getDb().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toJSONArray(projList);
	}
	

	private Double getProjectHours (int projectid) {
		Double ret = null;
		ResultSet res = null;
		try {			
			PreparedStatement ps = super.getDb().getConnect().prepareStatement("SELECT SUM(regularHours+OTHours) AS totalhrs FROM dts_direct_activity WHERE pworeg_project_id = ?;");		
			ps.setInt(1, projectid);
			res = ps.executeQuery();
			res.next();
			ret =  res.getDouble("totalHrs");
			res.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return ret;
	}	

	
	//TO CONTINUE
	@SuppressWarnings("unused")
	private Double getMemberList (int projectid) {
		Double ret = null;
		ResultSet res = null;
		try {			
			PreparedStatement ps = super.getDb().getConnect().prepareStatement("SELECT "
					+ "DISTINCT p.member_id AS memberid, "
					+ "p.proj_id AS projid, "
					+ "u.givenname AS givenname, "
					+ "u.surname AS surname "
					+ "FROM pworeg_project_member AS p\r\n" 
					+ "INNER JOIN fos_user AS u ON p.member_id = u.id\r\n" 
					+ "WHERE proj_id = ?;");		
			ps.setInt(1, projectid);
			res = ps.executeQuery();

			while (res.next()){
				//ret =  res.getsi;
			}
			res.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return ret;
	}	
	
	
	private int getMemberCount(int projectid) {
		int ret = 0;
		ResultSet res = null;
		try {			
			PreparedStatement ps = super.getDb().getConnect().prepareStatement("SELECT COUNT(DISTINCT p.member_id) AS membercount FROM pworeg_project_member AS p\r\n" + 
					"INNER JOIN fos_user AS u ON p.member_id = u.id\r\n" + 
					"WHERE proj_id = ?;");		
			ps.setInt(1, projectid);
			res = ps.executeQuery();
			res.next();
			ret =  res.getInt("membercount");	
			res.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return ret;
	}	

	@SuppressWarnings("unchecked")
	public JSONArray toJSONArray(ArrayList<Project> arr) {			
		JSONArray jarr = new JSONArray();
		for (Project p: arr) {
			jarr.add(toJSONObject(p));
		}
		return jarr;
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject(Project p) {
		JSONObject obj = new JSONObject();
		obj.put("projid", p.id);
		obj.put("projectname", p.name);
		obj.put("commonname", p.commonName);
		obj.put("dateregistered", p.dateRegistered);
		obj.put("totalhrs", p.totalHrs);
		obj.put("manager", p.getManager().getFullName());
		obj.put("leader", p.getLeader().getFullName());
		obj.put("membercount", p.memCount);
		obj.put("status", p.getStatus());
		return obj;	
	}
}
