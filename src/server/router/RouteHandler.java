package server.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.data.DataModel;
import server.data.ParamErrorDataModel;
import server.http.HttpPacketStatus;

public abstract class RouteHandler {
	
	private HttpPacketStatus status;
	private List<String> requiredParam = new ArrayList<>();
	
	public RouteHandler(HttpPacketStatus s,List<String> rp) {
		status = s;
		requiredParam = rp;
	}
	
	public void setStatus(HttpPacketStatus s) {
		status = s;
	}
	
	public HttpPacketStatus getStatus() {
		return status;
	}
	
	public DataModel processRequest(HashMap<String,String> param) {
		
//		if(!paramlengthCheck(param)) {
//			status = HttpPacketStatus.EXPFAILED;
//			return new ParamErrorDataModel();
//		}
		
		if(!paramProvidedCheck(param)) {
			status = HttpPacketStatus.EXPFAILED;
			return new ParamErrorDataModel();
		}
		
		if(!paramNullCheck(param)) {
			status = HttpPacketStatus.EXPFAILED;
			return new ParamErrorDataModel();
		}
		
		//System.out.println("RouteHandler Options Passed");
		return process(param);
	}

	public abstract DataModel process(HashMap<String, String> param);

	private boolean paramNullCheck(HashMap<String, String> param) {
		if(param == null) param = new HashMap<>();
		//System.out.println(param.toString() + " :1 " + requiredParam);
		for(Map.Entry<String, String> entry: param.entrySet()) {
			//if param null return false
			if (entry.getValue() == null) return false;
		}
		return true;
	}

	private boolean paramProvidedCheck(HashMap<String, String> param) {
		if(param == null) param = new HashMap<>();
		//System.out.println(param.toString() + " :2 " + requiredParam);
		for(String p: requiredParam) {
			//if param not found return false
			if(!param.containsKey(p)) return false;
		}
		
		return true;
	}

//	private boolean paramlengthCheck(HashMap<String, String> param) {
//		if(param == null) param = new HashMap<>();
//		//System.out.println(param.toString() + " :3 " + requiredParam);
//		//if lenth does not match return false
//		if(param.size() != requiredParam.size())
//			return false;
//		return true;
//	}
	
	
	
	//public abstract DataModel process(HashMap<String,String> param);
}
