package server.auth;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import server.http.HttpPacket;
//import java.util.Map;

public class Auth {
	
	private static Auth self;
	private static long timeout = 10*60;
	private static HashMap<String,Long> tokenList = new HashMap<>();
	private static final ArrayList<String> authExemptions = new ArrayList<>();
	public boolean enableTimeout = false;
	
	private Auth() {
		if(enableTimeout) {
			ServiceWorker sw = new ServiceWorker();
			sw.start();
		}
	}
	
	//future service thread that monitors token expiration
	private class ServiceWorker extends Thread{
		
		public void run() {
			System.out.println("Authentication Service worker running");
			//while(true) {}
		}
	}
	
	
	//get singleton instance
	public static Auth getAuth() {
		if(self == null) {
			return new Auth();
		}else {
			return self;
		}
	}
	
//	public static synchronized Token createToken(String uid) {
//		Token t = new Token(uid);
//		Date d = new Date();
//		if(tokenList.get(t.getToken()) == null) {
//			tokenList.put(t.getToken(), d.getTime());
//		}
//		printTokenList();
//		return t;
//	}
	
	public static synchronized void removeToken(String token) {
		tokenList.remove(token);
		printTokenList();
	}
	
//	public static synchronized HttpPacket authenticate(HttpPacket p) {
//		if(p.getToken() == null) {
//			
//		}
//	}
	
	public 
	
	
	//for debugging purposes
	public static void printTokenList() {
		System.out.println(tokenList.toString());
	}
}
