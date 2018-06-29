package server.auth;

import java.util.Base64;

import org.json.simple.JSONObject;

public class Token {
	
	private static final String SECRET = "secret";
	private JSONObject header = new JSONObject();
	private JSONObject payload = new JSONObject();
	private String tokenStr;
	
	@SuppressWarnings("unchecked")
	public Token(String uid) {
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		payload.put("uid", uid);
		
		String headerEncoded = Base64.getEncoder().encodeToString(header.toJSONString().getBytes());
		String payloadEncoded = Base64.getEncoder().encodeToString(payload.toJSONString().getBytes());
		String data = headerEncoded + "." + payloadEncoded;
		String signature = HASH.digest(data, SECRET, "HmacSHA256");
		tokenStr = headerEncoded + "." + payloadEncoded + "." + signature;
	}
	
	public String getToken() {
		return tokenStr;
	}
}
