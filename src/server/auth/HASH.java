package server.auth;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HASH {
	
	public static final int NONEMPTY = 1;
	
	public static String digest(String msg, String keyString, String algo) {
	    String digest = null;
	    try {
	      SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
	      Mac mac = Mac.getInstance(algo);
	      mac.init(key);

	      byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

	      StringBuffer hash = new StringBuffer();
	      for (int i = 0; i < bytes.length; i++) {
	        String hex = Integer.toHexString(0xFF & bytes[i]);
	        if (hex.length() == NONEMPTY) {
	          hash.append('0');
	        }
	        hash.append(hex);
	      }
	      digest = hash.toString();
	    } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException e) {
	    	System.out.println("Error Hashing Value");
	    }
	    return digest;
	  }

}
