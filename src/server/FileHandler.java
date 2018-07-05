package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FileHandler {
		
	public HashMap<String, String> readFile(String source) {
		HashMap<String, String> details = new HashMap<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(source));
			
			String line;
			while ((line = reader.readLine()) != null){
				String[] parts = line.split(":");
				details.put(parts[0].replace(";", ""), parts[1].replace(";", ""));
			}
			reader.close();				
		} catch (IOException e) {
			e.printStackTrace();
		}
		return details;
	}
	
}
