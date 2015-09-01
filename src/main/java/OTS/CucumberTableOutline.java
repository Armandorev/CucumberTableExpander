package main.java.OTS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CucumberTableOutline {

	private ArrayList<String> outlineLine  = new ArrayList<String>();
	private ArrayList<CucumberTableHeaderElement> headers   = new ArrayList<CucumberTableHeaderElement>();
	private ArrayList<String> headersNames   = new ArrayList<String>();
	private StringBuilder outlineOutputWork = new StringBuilder();
	private StringBuilder outlineOutput = new StringBuilder();
	private ArrayList<String> severalScenariosResult   = new ArrayList<String>();

	public CucumberTableOutline(String[] parts) {
		addFeatureContent(parts);
	}

	public String getOutlineLine(int id) {
		return this.outlineLine.get(id);
	}

	public void addToOutlineLine(String outlineLineEntry) {
		this.outlineLine.add(outlineLineEntry);
	}
	

	public void addOutlineLines(String[] outlineLineEntries) {
		
		//Read the Outline Definition and find one variable name on each line.
		//TODO Manage more than one variable per line.
		for (int j = 0; j < outlineLineEntries.length; j++) {
			String outlineLineValue = outlineLineEntries[j].toString();
			this.outlineOutputWork.append(outlineLineValue).append("\n");
			//Find the variable on the line.
			if (outlineLineValue.contains("<"))
			{
				String variableName = outlineLineValue.substring(outlineLineValue.indexOf("<") + 1);
				variableName = variableName.substring(0, variableName.indexOf(">"));
				this.headersNames.add(variableName.trim());
			}
			this.outlineLine.add(outlineLineEntries[j].toString());
		}
	}
	
	public int getLength()
	{
		return this.outlineLine.size();
	}

	private void addScenarioValues(String[] scenarioOnTable) {
		//Read variable names from the first line of the table
		String[] variableNamesOnValues = scenarioOnTable[0].trim().split("\\|");//OTS
		for (int i = 0; i < variableNamesOnValues.length; i++) {
			if (!variableNamesOnValues[i].isEmpty())
			{
				//Include Header element to the Scenario
				int position = this.headersNames.indexOf(variableNamesOnValues[i].trim());
				CucumberTableHeaderElement headerElement = new CucumberTableHeaderElement();
				headerElement.setHeaderPosition(position);
				headerElement.setHeaderName(variableNamesOnValues[i].trim());
				headerElement.setHeaderPositionOnTable(i);
				this.headers.add(headerElement);
			}
		}
		//TODO : Remove Empty variable lines (Leading delimiters)

		int indexOfHeader;
		for (int j = 1; j < scenarioOnTable.length; j++) {
			if (!scenarioOnTable[j].isEmpty())
			{
				indexOfHeader = 0;
				String ScenarioGenerated = outlineOutputWork.toString();
				//Divide Each Value for variable
				String[] variableValue = scenarioOnTable[j].trim().split("\\|");//OTS
				for (int k = 0; k < variableValue.length; k++) {
					if ((!variableValue[k].isEmpty())&&(indexOfHeader<headers.size()))
					{

						//indexOfHeader<=headers.size()
						ScenarioGenerated = ScenarioGenerated.replace(this.headers.get(indexOfHeader).getHeaderNameWithSymbols().trim(), 
											variableValue[k].trim());
						indexOfHeader++;
					}
				}
				this.outlineOutput.append("\n").append("Scenario: ").append(ScenarioGenerated);
				//NEW ROW WITH DATA on ScenarioGenerated
				this.severalScenariosResult.add("\nScenario: "+ScenarioGenerated);
			}
		}
		
	}

	private void addFeatureContent(String[] parts) {
		//Divide lines of Scenarios
		this.addOutlineLines(parts[0].trim().split("\n"));//OTS
		
		//Separate each line on the table
		ArrayList<String> scenarioontableList = new ArrayList<String>(Arrays.asList(parts[1].trim().split("\n")));//OTS
		for (Iterator<String> iteration = scenarioontableList.listIterator(); iteration.hasNext(); ) {
		    String elementToCheck = iteration.next();
		    if (elementToCheck.startsWith("#")) {
		    	iteration.remove();
		    }
		}
		String[] arrayOfScenario = new String[scenarioontableList.size()];
		for (int i = 0; i < scenarioontableList.size(); i++) {
			arrayOfScenario[i] = scenarioontableList.get(i);	
		}
		this.addScenarioValues(arrayOfScenario);
		
	}

	public String getOutlineOutput() {
		return outlineOutput.toString();
	}

	public ArrayList<String> getSeveralScenariosResult() {
		return severalScenariosResult;
	}
	
	
}
