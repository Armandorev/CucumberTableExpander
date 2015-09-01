package main.java.CucumberTableExpander;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CucumberTableExpanderUseSample {
	
	public static void main(String[] args) throws IOException {
		// Class for testing Cucumber Table Expander

		//Read the feature data from a file
		String file = readFile("src/main/resources/test.feature");
	    //Call the cucumber table expander passing the file readed as string and return the 
		//	cucumber feature expanded
		String cucumberTableExpanded = new CucumberTableExpander(file).getFeatureExpanded();
		ArrayList<String> cucumberTableExpandedAsList = new CucumberTableExpander(file).getFeatureExpandedAsList();
		
		//Display the original file and the processed one.
	    System.out.println("original------------------------> : \n"+ file);
	    System.out.println("expanded------------------------> : \n"+ cucumberTableExpanded);

	    System.out.println("expanded-as-list-----------------> : \n");
	    for (int j = 0; j < cucumberTableExpandedAsList.size(); j++) {
			String outlineLineValue = cucumberTableExpandedAsList.get(j).toString();
			System.out.println("Scenario List element("+j+")"+outlineLineValue);
		}
	}

	static String readFile(String path) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, "UTF-8");
			}
	
}
