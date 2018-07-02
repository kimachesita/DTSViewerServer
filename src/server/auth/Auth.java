package server.auth;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import server.http.HttpPacket;
//import java.util.Map;

public class Auth {

	private static Auth self;
	private long timeout = 5 * 60 * 1000;	//for 5 min only
	private static HashMap<String,Long> tokenList = new HashMap<>();
	private static ArrayList<String> authExceptions = new ArrayList<>();
	public boolean enableTimeout = true;

	private ServiceWorker disposeManager;

	private Auth() {
		disposeManager = new ServiceWorker();
	}

	//service thread that monitors token expiration
	private class ServiceWorker extends Thread{

		public void run() {
			System.out.println("Authentication Service Dispose Manager running");
			while(true) {
				if(enableTimeout) {
					Date now = new Date();

					for(Map.Entry<String, Long> entry: tokenList.entrySet()) {
						Long delta = getInterval(now.getTime(),entry.getValue());
						//System.out.println("delta: " + delta + " vs " + timeout );
						if(delta > timeout) {
							//System.out.println(entry.getKey() + " token has timedout");						
							removeToken(entry.getKey());
						}
					}

					try {
						//printTokenList();
						Thread.sleep(2000);
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

	public void start() {
		disposeManager.start();
	}

	public synchronized HttpPacket authenticate(HttpPacket p) {
		HttpPacket loc = p;
		String token = p.getToken();
		
		System.out.println("Incoming Token: " + token);
		Auth.instance().printTokenList();
		
		if(!authExceptions.contains((p.getReqMethod() + p.getReqRoute()))) {
			if(token == null || !isValid(token)) {
				loc.setReqMethod("");
				loc.setReqRoute("AuthFailed");
			}
		}

		return loc;
	}

	public synchronized Token addToken(String uid) {
		Token t = new Token(uid);
		Date d = new Date();
		if(tokenList.get(t.getToken()) == null) {
			tokenList.put(t.getToken(), d.getTime());
		}
		printTokenList();
		return t;
	}

	public synchronized void removeToken(String token) {
		tokenList.remove(token);
		printTokenList();
	}

	public void addExceptions(String t) {
		authExceptions.add(t);
		System.out.println(authExceptions.toString());
	}

	/* Some utility/convenience methods */
	private boolean isValid(String t) {
		if(!tokenList.containsKey(t)) return false;
		return true;		
	}

	private long getInterval(Long n, Long d) {
		return n - d;
	}

	public boolean isAuthorize (String username, String password) {
        String SERVER = "ldap://172.28.58.200" + ":389";
        String base = "ou=people, dc=ntsp,dc=nec,dc=co,dc=jp";
        String user = "uid=" + username + "," + base;

        // Setup environment for authenticating
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, SERVER);
        env.put(Context.SECURITY_AUTHENTICATION, "Simple");
        env.put(Context.SECURITY_PRINCIPAL, user);
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
		System.out.println(tokenList.toString());
	}
}
