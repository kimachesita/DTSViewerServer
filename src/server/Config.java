package server;

import java.util.HashMap;

public class Config {
	
	private FileHandler fr;
	private static HashMap<String, String> configs;
	private static Config self;
	
	private Config() {
		fr = new FileHandler();
		configs = new HashMap<>();
		configs = fr.readFile("trunk/src/server/config.cfg");
	}
	
	public static Config instance() {
		if(self == null) self = new Config();
		return self;
	}
	
	public String getValue(String key) {
		return configs.get(key);
	}

	public void printConfig() {
		System.out.println(configs.toString());
	}
}
