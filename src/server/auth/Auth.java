package server.auth;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import server.Config;
import server.http.HttpPacket;

public class Auth {

	private static Auth self;
	private static Long timeout = Long.valueOf(Config.instance().getValue("auth_timeout_mins")) * 60 * 1000;	
	private static Map<String,Long> tokenList = new ConcurrentHashMap<>();
	private static ArrayList<String> authExceptions = new ArrayList<>();
	private boolean enableTimeout = true;
	private String ldapServer;
	private String ldapBase;

	private ServiceWorker disposeManager;

	private Auth() {
		enableTimeout = Boolean.valueOf(Config.instance().getValue("auth_enable_timeout"));
		disposeManager = new ServiceWorker();
        ldapServer = "ldap://" + Config.instance().getValue("ldap_server") + ":" + Config.instance().getValue("ldap_port");
        ldapBase = Config.instance().getValue("ldap_base");
	}

	//service thread that monitors token expiration
	private class ServiceWorker extends Thread{

		public void run() {
			if(enableTimeout) System.out.println("Auth Service: Timeout Feature enabled");
			System.out.println("Auth Service: Authentication Service Dispose Manager running at timeout : " + timeout/(60*1000) + " min(s)");
			while(true) {
				if(enableTimeout) {
					Date now = new Date();

					for(Map.Entry<String, Long> entry: tokenList.entrySet()) {
						Long delta = getInterval(now.getTime(),entry.getValue());
						if(delta > timeout) {
							removeToken(entry.getKey());
							System.out.println("AUTH Service: A token has expired");						
							
						}
					}

					try {
						//printTokenList();
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}


	//get singleton instance
	public static Auth instance() {
		if(self == null) return new Auth();
		else return self;
	}

	//start the service thread
	public void start() {
		disposeManager.start();
	}

	public synchronized HttpPacket authenticator(HttpPacket p) {
		HttpPacket packet = p;
		String token = packet.getToken();
		Date now = new Date();
		
		String reqRoute = p.getReqMethod() + p.getReqRoute();
		//System.out.println("Incoming Token: " + token);
		//Auth.instance().printTokenList();
		//System.out.println(authExceptions.toString());
		//if not in the authExceptions process packet
		if(!authExceptions.contains((reqRoute))) {
			if(token == null || !isValid(token)) {
				packet.setReqMethod("");
				packet.setReqRoute("AuthFailed");
			}else {
				//update token timestamp
				tokenList.replace(token, tokenList.get(token), now.getTime());
			}
		}

		return packet;
	}

	//add token to the token list
	public synchronized Token addToken(String uid) {
		Token t = new Token(uid);
		Date d = new Date();
		
		//remove existing if any
		tokenList.remove(t.getToken());
		
		//add new token entry with updated time
		tokenList.put(t.getToken(), d.getTime());
		printTokenList();
		return t;
	}

	//remove token from token list
	public synchronized void removeToken(String token) {
		tokenList.remove(token);
		printTokenList();
	}

	public void addExceptions(String t) {
		authExceptions.add(t);
	}

	/* Some utility/convenience methods */
	private boolean isValid(String t) {
		if(!tokenList.containsKey(t)) return false;
		return true;		
	}

	private long getInterval(Long n, Long d) {
		return n - d;
	}

	//verify credentials via ldap
	public boolean isAuthorize (String username, String password) {

		String ldapUser = "uid=" + username + "," + ldapBase;
		
        // Setup environment for authenticating
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapServer);
        env.put(Context.SECURITY_AUTHENTICATION, "Simple");
        env.put(Context.SECURITY_PRINCIPAL, ldapUser);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {
                DirContext context = new InitialDirContext(env);
                context.close();

        } catch (AuthenticationNotSupportedException ex) {
            return false;
        } catch (AuthenticationException ex1) {
            return false;
        } catch (NamingException ex2) {
            return false;
        }
        
        return true;
    }

	//for debugging purposes
	public void printTokenList() {
		//System.out.println(tokenList.toString());
		System.out.println("AUTH Service: There are " + tokenList.size() + " user(s) authenticated");
	}
}
