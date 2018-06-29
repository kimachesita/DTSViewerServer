package server.db.model;

import java.sql.SQLException;

import server.db.DBConnection;

public abstract class Model {
	private DBConnection db;

	public Model() throws SQLException {
		db = new DBConnection();		
		db.connect();
	}

	public DBConnection getDb() {
		return db;
	}
}
