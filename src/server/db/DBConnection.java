package server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import server.Config;
import server.FileHandler;

public class DBConnection {
	
	private static DBConnection self;
	private static Connection connect;
	private static HashMap<String, String> dbDetail;
	private FileHandler fileHandler;

	public DBConnection(){
		dbDetail = new HashMap<>();
		fileHandler = new FileHandler();
	}
	
	public static DBConnection getInstance() {
		if(self == null) return new DBConnection();
		else return self;
			
	}
	
	public boolean connect(){
		try {		
			//read db config file
			//dbDetail = fileHandler.readFile("trunk/src/server/db/config-db.txt");
			
			Class.forName("com.mysql.cj.jdbc.Driver");
//			connect = DriverManager.getConnection("jdbc:mysql://"+ 
//					dbDetail.get("host") 		+ ":" +
//					dbDetail.get("port") 		+ "/" +
//					dbDetail.get("database")	+ "?" +
//					dbDetail.get("ext"), 
//					dbDetail.get("username"), 
//					dbDetail.get("password"));
			
			connect = DriverManager.getConnection("jdbc:mysql://"+ 
					Config.instance().getValue("db_host")		+ ":" +
					Config.instance().getValue("db_port")		+ "/" +
					Config.instance().getValue("db_database")	+ "?" +
					Config.instance().getValue("db_ext"), 
					Config.instance().getValue("db_username"), 
					Config.instance().getValue("db_password"));
			
			return true;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("DB : Error on connect attempt. Exiting now.");
			return false;
		} 
	}

	public Connection getConnect() {
		return connect;
	}
	
	public void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();	           
			}
		}
	}   

	public void closePreparedStatement(PreparedStatement ps) {
		if(ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();	            
			}
		}
	}  

	public void closeConnection() {
		if(connect != null) {
			try {
				connect.close();
			} catch (SQLException e) {
				e.printStackTrace();	          
			}
		}
	} 
}
