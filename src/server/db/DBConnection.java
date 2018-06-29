package server.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DBConnection {

	private Connection connect;
	private Statement statement;
	HashMap<String, String> dbDetail = new HashMap<>();

	public DBConnection(){

	}
	
	public void connect() throws SQLException{
		try {		
			//read db config file
			readFile();
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://"+ 
					dbDetail.get("host") + ":" +
					dbDetail.get("port") 		+ "/" +
					dbDetail.get("database")	+ "?" +
					dbDetail.get("ext"), 
					dbDetail.get("username"), 
					dbDetail.get("password"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public Connection getConnect() {
		return connect;
	}

	private void readFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/server/db/config-db.txt"));
			String line;
			while ((line = reader.readLine()) != null){
				String[] parts = line.split(":");
				dbDetail.put(parts[0].replace(";", ""), parts[1].replace(";", ""));
			}
			reader.close();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// You need to close the resultSet
	public void close() {
		try {
			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}
