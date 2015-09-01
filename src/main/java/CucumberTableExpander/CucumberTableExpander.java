package main.java.CucumberTableExpander;

import java.util.ArrayList;

public class CucumberTableExpander {
	
	private String feature;
	private StringBuilder result = new StringBuilder();
	private ArrayList<String> listOutput = new ArrayList<String>();
	
	public CucumberTableExpander(String featureFile){
		String[]scenarios = featureFile.split("Scenario"); 
		
	    
	    for (int i = 1; i < scenarios.length; i++) {
			//Separate Scenario Outlines from regular scenarios
			if (scenarios[i].trim().startsWith("Outline:"))
			{
				//Remove Outline word
				scenarios[i] = scenarios[i].trim().replace("Outline:","");
				//Separate Scenario definitions from table with containing values
				String[] parts = scenarios[i].trim().split("Examples:|Scenarios:");//OTS
				//Instantiate outline class to work with definition and list of values
				CucumberTableOutline outline = new CucumberTableOutline(parts);
				this.result.append(outline.getOutlineOutput());
				this.listOutput.addAll(outline.getSeveralScenariosResult());
				
			}
			else
			{
				StringBuilder noFeatureScenario = new StringBuilder();
				noFeatureScenario.append("\nScenario: ").append(scenarios[i].trim()).append("\n");
				this.result.append(noFeatureScenario);
				this.listOutput.add(noFeatureScenario.toString());
			}
	    }
	    
	    // scenarios outlines n-times with table values.
	    this.feature = this.result.toString();
	    
	}
	
	public String getFeatureExpanded(){
		if (this.feature.isEmpty()){
			return "No Cucumber feature file provided";
		}
		else{
			return this.feature;
		}
	}

	public ArrayList<String> getFeatureExpandedAsList(){
		if (this.listOutput.isEmpty()){
			return null;
		}
		else{
			return this.listOutput;
		}
	}
	
}

