package src.main.java.OTS;

import java.util.ArrayList;

public class Outline {

	private ArrayList<String> outlineLine  = new ArrayList<String>();
	private ArrayList<HeaderElement> headers   = new ArrayList<HeaderElement>();
	private ArrayList<String> headersNames   = new ArrayList<String>();
	private StringBuilder outlineOutputWork = new StringBuilder();
	private StringBuilder outlineOutput = new StringBuilder();
	
	public Outline(String[] parts) {
		addFeatureContent(parts);
	}

	public String getOutlineLine(int id) {
		return this.outlineLine.get(id);
	}

	public void addToOutlineLine(String outlineLineEntry) {
		this.outlineLine.add(outlineLineEntry);
	}
	

	public void addOutlineLines(String[] outlineLineEntries) {
		
		for (int j = 0; j < outlineLineEntries.length; j++) {
			String outlineLineValue = outlineLineEntries[j].toString();
			this.outlineOutputWork.append(outlineLineValue).append("\n");
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
				HeaderElement headerElement = new HeaderElement();
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
				int numOfHeaders = headers.size();
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
			}
		}
		
	}

	private void addFeatureContent(String[] parts) {
		//Divide lines of Scenarios
		this.addOutlineLines(parts[0].trim().split("\n"));//OTS
		
		//Separate each line on the table
		String[] scenarioOnTable = parts[1].trim().split("\n");//OTS
		this.addScenarioValues(scenarioOnTable);
		
	}

	public String getOutlineOutput() {
		return outlineOutput.toString();
	}

	
	
}
