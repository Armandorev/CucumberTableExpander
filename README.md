# CucumberTableExpander
Class that takes a cucumber feature file and returns the feature with all the tables expanded

this project includes classes and main class for test purposes that reads the feature from a file.

For including in other project it could be as simple as including 
CucumberTableExpander.java
HeaderElement.java
Outline.java 

and instantiate CucumberTableExpander Passing a String containing a feature file
String cucumberTableExpanded = new CucumberTableExpander(file).getFeatureExpanded();
