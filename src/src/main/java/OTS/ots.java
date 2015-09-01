package src.main.java.OTS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ots {
	
	public static void main(String[] args) throws IOException {
		// Class for testing Cucumber Table Expander

		//Read the feature data from a file
		String file = readFile("resources/test.feature");
	    //Call the cucumber table expander passing the file readed as string and return the 
		//	cucumber feature expanded
		String cucumberTableExpanded = new CucumberTableExpander(file).getFeatureExpanded();

		//Display the original file and the processed one.
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
