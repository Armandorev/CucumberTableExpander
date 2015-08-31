package src.main.java.OTS;

public class CucumberTableExpander {
	
	private String feature;
	private StringBuilder result = new StringBuilder();
	
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
				Outline outline = new Outline(parts);
				this.result.append(outline.getOutlineOutput());
				
			}
			else
			{
				this.result.append("\nScenario: ").append(scenarios[i].trim());
			}
	    }
	    
	    //TODO : replace this with actual string concatenation of Scenarios
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
	
}

