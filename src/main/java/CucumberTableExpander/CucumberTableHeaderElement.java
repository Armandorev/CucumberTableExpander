package main.java.CucumberTableExpander;

public class CucumberTableHeaderElement {
	private String headerName;
	private int headerPosition;
	private int headerPositionOnTable;

	public CucumberTableHeaderElement() {
	}
	
	public String getHeaderName() {
		return headerName;
	}

	
	public String getHeaderNameWithSymbols() {
		return "<"+headerName+">";
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
