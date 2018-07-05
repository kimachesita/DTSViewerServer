package server.db.model;

import java.util.HashMap;

public class QueryList {
	public static HashMap<String, String> statementList;

	public QueryList() {
		statementList = new HashMap<>();
		
		statementList.put("directActivityByProject", "SELECT "
				+ "proj.id as projid, "
				+ "proj.nspprojectname AS projname, "
				+ "wo.workordernumber AS workorder, "
				+ "projact.activity_id AS projactid, "
				+ "projact.name as projactname, "
				+ "ph.id AS phaseid, "
				+ "ph.name AS phasename, "							
				+ "cat.id AS actcatID, "
				+ "cat.name AS actcatname, "
				+ "act.id AS actid,  "
				+ "act.name AS actname, "
				+ "ts.date AS date, "
				+ "dts.regularHours AS reghours, "
				+ "dts.OTHours AS othours " 						
				+ "FROM pworeg_project as proj " 
				+ "LEFT JOIN pworeg_wo_project as wo ON proj.id = wo.project_id "
				+ "LEFT JOIN dts_direct_activity as dts ON proj.id = dts.pworeg_project_id AND wo.workordernumber = dts.pworeg_workorder_id "
				+ "LEFT JOIN dts_timesheet as ts ON dts.dtstimesheet_id = ts.id "
				+ "LEFT JOIN dts_project_activity as projact ON dts.projectactivity_id = projact.id " 
				+ "LEFT JOIN dts_activity as act ON projact.activity_id = act.id " 
				+ "LEFT JOIN dts_activity_category as cat ON projact.activity_category_id = cat.id " 
				+ "LEFT JOIN dts_phase as ph ON projact.phase_id = ph.id " 
				+ "WHERE proj.id = ? AND ts.date BETWEEN  ? AND ? order by wo.workordernumber, ph.id, act.id;");
		statementList.put("directActivityByUser", "SELECT "
				+ "proj.id as projid, "
				+ "proj.nspprojectname AS projname, "
				+ "wo.workordernumber AS workorder, "
				+ "projact.activity_id AS projactid, "
				+ "ts.user_id AS userid, "
				+ "projact.name as projactname, "
				+ "ph.id AS phaseid, "
				+ "ph.name AS phasename, "							
				+ "cat.id AS actcatID, "
				+ "cat.name AS actcatname, "
				+ "act.id AS actid,  "
				+ "act.name AS actname, "
				+ "ts.date AS date, "
				+ "dts.regularHours AS reghours, "
				+ "dts.OTHours AS othours " 						
				+ "FROM pworeg_project as proj " 
				+ "LEFT JOIN pworeg_wo_project as wo ON proj.id = wo.project_id\r\n" + 
				"LEFT JOIN dts_direct_activity as dts ON proj.id = dts.pworeg_project_id AND wo.workordernumber = dts.pworeg_workorder_id\r\n" + 
				"LEFT JOIN dts_timesheet as ts ON dts.dtstimesheet_id = ts.id\r\n" + 
				"LEFT JOIN dts_project_activity as projact ON dts.projectactivity_id = projact.id\r\n" + 
				"LEFT JOIN dts_activity as act ON projact.activity_id = act.id\r\n" + 
				"LEFT JOIN dts_activity_category as cat ON projact.activity_category_id = cat.id\r\n" + 
				"LEFT JOIN dts_phase as ph ON projact.phase_id = ph.id\r\n" + 
				"WHERE proj.id = ? AND ts.user_id = ? AND ts.date BETWEEN  ? AND ? order by wo.workordernumber, ph.id, act.id;"); 
		statementList.put("projectList", "SELECT "
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
				+ "INNER JOIN fos_user u ON proj.manager = u.id ;");
		statementList.put("projectListByStatus", "SELECT "
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
				+ "INNER JOIN fos_user u ON proj.manager = u.id WHERE proj.status = ?;");
		statementList.put("userByUserId", "SELECT * FROM fos_user WHERE id = ?;");
		statementList.put("projectHours", "SELECT SUM(regularHours+OTHours) AS totalhrs FROM dts_direct_activity WHERE pworeg_project_id = ?;");
		statementList.put("memberCountOfProject", "SELECT "
				+ "COUNT(DISTINCT p.member_id) AS membercount "
				+ "FROM pworeg_project_member AS p\r\n" 
				+ "INNER JOIN fos_user AS u ON p.member_id = u.id\r\n" 
				+ "WHERE proj_id = ?;");	
		statementList.put("indirectActivityByUser", "SELECT "
				+ "ts.user_id AS userid, "
				+ "act.id AS actid, "
				+ "act.name AS actname, "
				+ "cat.name AS actcatname, "
				+ "ts.date, "
				+ "indirect.regularHours AS reghours, "
				+ "indirect.OTHours AS othours "
				+ "FROM dts_indirect_activity as indirect "
				+ "LEFT JOIN dts_activity AS act ON indirect.activity_id = act.id "
				+ "LEFT JOIN dts_activity_category AS cat ON act.activitycategory = cat.id "
				+ "LEFT JOIN dts_timesheet AS ts ON indirect.dtstimesheet_id = ts.id "
				+ "WHERE ts.user_id = ? AND ts.date BETWEEN ? AND ? ORDER BY cat.id, act.id");		
		statementList.put("userDirectActivityTotalHours", "SELECT "
				+ "dir.pworeg_project_id AS projid, "
				+ "u.givenname AS givenname, "
				+ "u.surname AS surname, "
				+ "u.id as userid, "
				+ "SUM(dir.regularHours) AS reghours, "
				+ "SUM(OThours) AS othours FROM dts_timesheet as ts "
				+ "JOIN dts_direct_activity AS dir ON ts.id = dir.dtstimesheet_id "
				+ "LEFT JOIN fos_user as u ON ts.user_id = u.id "
				+ "WHERE dir.pworeg_project_id = ? AND ts.date BETWEEN ? AND ? GROUP BY u.id ORDER by u.id;");
		statementList.put("userByUsername", "SELECT "
				+ "u.id AS userid, "
				+ "u.givenname AS givenname, "
				+ "u.surname AS surname, "
				+ "u.username AS username, "
				+ "u.email AS email, "
				+ "pos.positionname AS position, "
				+ "dep.departmentname AS department "
				+ "FROM fos_user AS u "
				+ "LEFT JOIN org_position AS pos ON u.position_id = pos.id "
				+ "LEFT JOIN org_department AS dep ON u.department_id = dep.id "
				+ "WHERE username = ? ;");
		
		statementList.put("directActivitySummaryHoursByEngineer", "SELECT \r\n" + 
				"proj.id as projid, \r\n" + 
				"ts.user_id AS userid, \r\n" + 
				"proj.nspprojectname AS projname, \r\n" + 
				"wo.workordernumber AS workorder, \r\n" + 
				"ts.date AS date, \r\n" + 
				"SUM(dts.regularHours) AS totalReg, \r\n" + 
				"SUM(dts.OTHours) AS totalOT \r\n" + 
				"FROM pworeg_project as proj\r\n" + 
				"LEFT JOIN pworeg_wo_project as wo ON proj.id = wo.project_id\r\n" + 
				"LEFT JOIN dts_direct_activity as dts ON proj.id = dts.pworeg_project_id AND wo.workordernumber = dts.pworeg_workorder_id\r\n" + 
				"LEFT JOIN dts_timesheet as ts ON dts.dtstimesheet_id = ts.id\r\n" + 
				"WHERE proj.id = ? AND ts.user_id = ? AND ts.date BETWEEN  ? AND ? GROUP BY ts.date order by ts.date;");

	}

	public static HashMap<String, String> getStatementList() {
		return statementList;
	}	
	public static String getStatement(String name) {
		return statementList.get(name);
	}
}
