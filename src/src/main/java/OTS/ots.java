package src.main.java.OTS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ots {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String file = readFile("resources/test.feature");
	    String cucumberTableExpanded = new CucumberTableExpander(file).getFeatureExpanded();

	    System.out.println("original------------------------> : \n"+ file);
	    System.out.println("expanded------------------------> : \n"+ cucumberTableExpanded);
	}

	static String readFile(String path) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, "UTF-8");
			}
	
}
