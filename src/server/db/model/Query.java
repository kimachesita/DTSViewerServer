package server.db.model;

import java.util.ArrayList;

public class Query {
	private String statement;
	private ArrayList<Object> param;
	
	public Query (String statement) {
		this.statement = statement;
		param = new ArrayList<>();
	}
	
	public void addParam(Object obj) {
		param.add(obj);		
	}
	
	public ArrayList<Object> getParam() {
		return this.param;		
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
	
}
