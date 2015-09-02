package main.java.CucumberTableExpander;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CucumberTableExpander {

// Delimiters for different Scenarios on the Cucumber Feature File
private static final String SCENARIO_DELIMITERS = "Scenario";
//Delimiters for specific Scenarios with tables
private static final String SCENARIO_OUTLINE_DELIMITER = "Outline";
//Delimiters for separate table value lines from definition
private static final String SCENARIO_TABLE_SEPARATOR = "Examples:|Scenarios:";


// **************************************************************
// * Cucumber Table Expander. (Armando Sanchez Medina) 			*
// *  - Input : String containing Cucumber Feature File 		*
// *  - Output : Cucumber feature with all table scenarios 		*
// * 			returned as one scenario replacing variables	* 
// *			with values from the table. Possible returns	*
// *			as :											*
// * 			1) Single String with all Scenarios concant.	*
// *			2) List of Strings.								*
// **************************************************************

	
	private String feature;
	private StringBuilder result = new StringBuilder();
	private ArrayList<String> listOutput = new ArrayList<String>();
	// Values for delimiters
	// Delimiters for different Scenarios on the Cucumber Feature File
	//private static String scenarioDelimiters = "Scenario" ;
	// Delimiters for separate table value lines from definition
//	private static ArrayList<String> tableDelimiters = (ArrayList<String>) Arrays.asList("Examples:","Scenarios:");
//	// Delimiters for separate table values/headers
//	private static ArrayList<String> valueDelimiters = (ArrayList<String>) Arrays.asList("|");
	

	public CucumberTableExpander(String featureFile) {
		String[] scenarios = featureFile.split(SCENARIO_DELIMITERS);

		for (int i = 1; i < scenarios.length; i++) {
			// Separate Scenario Outlines from regular scenarios
			if (scenarios[i].trim().startsWith(SCENARIO_OUTLINE_DELIMITER+":")) {
				// Remove Outline word
				scenarios[i] = scenarios[i].trim().replace(SCENARIO_OUTLINE_DELIMITER+":", "");
				// Separate Scenario definitions from table with containing
				// values
				String[] parts = scenarios[i].trim().split(
						SCENARIO_TABLE_SEPARATOR);// OTS
				// Instantiate outline class to work with definition and list of
				// values
				CucumberTableOutline outline = new CucumberTableOutline(parts);
				this.result.append(outline.getOutlineOutput());
				this.listOutput.addAll(outline.getSeveralScenariosResult());

			} else {
				StringBuilder noFeatureScenario = new StringBuilder();
				noFeatureScenario.append("\n"+SCENARIO_DELIMITERS)
						.append(scenarios[i].trim()).append("\n");
				this.result.append(noFeatureScenario);
				this.listOutput.add(noFeatureScenario.toString());
			}
		}

		// scenarios outlines n-times with table values.
		this.feature = this.result.toString();

	}

	public String getFeatureExpanded() {
		if (this.feature.isEmpty()) {
			return "No Cucumber feature file provided";
		} else {
			return this.feature;
		}
	}

	public ArrayList<String> getFeatureExpandedAsList() {
		if (this.listOutput.isEmpty()) {
			return null;
		} else {
			return this.listOutput;
		}
	}

	protected class CucumberTableOutline {

		private ArrayList<String> outlineLine = new ArrayList<String>();
		private ArrayList<CucumberTableHeaderElement> headers = new ArrayList<CucumberTableHeaderElement>();
		private ArrayList<String> headersNames = new ArrayList<String>();
		private StringBuilder outlineOutputWork = new StringBuilder();
		private StringBuilder outlineOutput = new StringBuilder();
		private ArrayList<String> severalScenariosResult = new ArrayList<String>();

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

			// Read the Outline Definition and find one variable name on each
			// line.
			for (int j = 0; j < outlineLineEntries.length; j++) {
				String outlineLineValue = outlineLineEntries[j].toString();
				this.outlineOutputWork.append(outlineLineValue).append("\n");
				// Find the variable on the line.
				if (outlineLineValue.contains("<")) {
					String variableName = outlineLineValue
							.substring(outlineLineValue.indexOf("<") + 1);
					variableName = variableName.substring(0,
							variableName.indexOf(">"));
					this.headersNames.add(variableName.trim());
				}
				this.outlineLine.add(outlineLineEntries[j].toString());
			}
		}

		public int getLength() {
			return this.outlineLine.size();
		}

		private void addScenarioValues(String[] scenarioOnTable) {
			// Read variable names from the first line of the table
			String[] variableNamesOnValues = scenarioOnTable[0].trim().split(
					"\\|");// OTS
			for (int i = 0; i < variableNamesOnValues.length; i++) {
				if (!variableNamesOnValues[i].isEmpty()) {
					// Include Header element to the Scenario
					int position = this.headersNames
							.indexOf(variableNamesOnValues[i].trim());
					CucumberTableHeaderElement headerElement = new CucumberTableHeaderElement();
					headerElement.setHeaderPosition(position);
					headerElement
							.setHeaderName(variableNamesOnValues[i].trim());
					headerElement.setHeaderPositionOnTable(i);
					this.headers.add(headerElement);
				}
			}
			// TODO : Remove Empty variable lines (Leading delimiters)

			int indexOfHeader;
			for (int j = 1; j < scenarioOnTable.length; j++) {
				if (!scenarioOnTable[j].isEmpty()) {
					indexOfHeader = 0;
					String ScenarioGenerated = outlineOutputWork.toString();
					// Divide Each Value for variable
					String[] variableValue = scenarioOnTable[j].trim().split(
							"\\|");
					for (int k = 0; k < variableValue.length; k++) {
						if ((!variableValue[k].isEmpty())&&(indexOfHeader < headers.size())) {
							ScenarioGenerated = ScenarioGenerated.replace(
							this.headers.get(indexOfHeader)
									.getHeaderNameWithSymbols().trim(),
							variableValue[k].trim());
							indexOfHeader++;
						}
					}
					this.outlineOutput.append("\n").append(SCENARIO_DELIMITERS+": ")
							.append(ScenarioGenerated);
					// NEW ROW WITH DATA on ScenarioGenerated
					this.severalScenariosResult.add("\n"+SCENARIO_DELIMITERS+": "
							+ ScenarioGenerated);
				}
			}

		}

		private void addFeatureContent(String[] parts) {
			// Divide lines of Scenarios
			this.addOutlineLines(parts[0].trim().split("\n"));// OTS

			// Separate each line on the table
			ArrayList<String> scenarioontableList = new ArrayList<String>(
					Arrays.asList(parts[1].trim().split("\n")));// OTS
			for (Iterator<String> iteration = scenarioontableList
					.listIterator(); iteration.hasNext();) {
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

	protected class CucumberTableHeaderElement {
		private String headerName;
		private int headerPosition;
		private int headerPositionOnTable;

		public CucumberTableHeaderElement() {
		}

		public String getHeaderName() {
			return headerName;
		}

		public String getHeaderNameWithSymbols() {
			return "<" + headerName + ">";
		}

		public void setHeaderName(String headerName) {
			this.headerName = headerName;
		}

		public int getHeaderPosition() {
			return headerPosition;
		}

		public void setHeaderPosition(int headerPosition) {
			this.headerPosition = headerPosition;
		}

		public int getHeaderPositionOnTable() {
			return headerPositionOnTable;
		}

		public void setHeaderPositionOnTable(int headerPositionOnTable) {
			this.headerPositionOnTable = headerPositionOnTable;
		}
	}

}
