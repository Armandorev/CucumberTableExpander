# CucumberTableExpander
Class that takes a cucumber feature file and returns the feature with all the tables expanded

this project includes classes and main class for test purposes that reads the feature from a file.

For including in other project it could be as simple as including 
CucumberTableExpander.java
HeaderElement.java
Outline.java 

and instantiate CucumberTableExpander Passing a String containing a feature file
String cucumberTableExpanded = new CucumberTableExpander(file).getFeatureExpanded();

EXAMPLES (based on include test.feature):
original------------------------> : 
				Scenario Outline: Create Simple Test
				  Given I open the application
				  When I enter username as <username>
				  And I enter password as <password>
				  And I enter title as <title>
				  And I press submit
				  Then I am logged
				
				
				Examples:
				
				| username | password | title |
				| Rob      | xyz1      | title1 |
				| Bob      | xyz1      | title2 | invalid value
				| Ben      | xyz2      | title3 |
				
				Scenario: Test without table
				  Given I open the application again
				  When I enter username as Tiger
				  And I enter password as Shot
				  Then I enter title as Mr
				  And press submit
				
				Scenario Outline: Create New Simple Test Table different order
				  Given I open the application
				  When I enter username as <username>
				  And I enter password as <password>
				  And I enter title as <title>
				  And I press submit
				  Then I am logged
				
				
				Examples:
				
				| username | title | password
				| Rob      | title1 | password1
				| Bob      | title2 | password2

expanded------------------------> : 

			Scenario: Create Simple Test
			  Given I open the application
			  When I enter username as Rob
			  And I enter password as xyz1
			  And I enter title as title1
			  And I press submit
			  Then I am logged
			
			Scenario: Create Simple Test
			  Given I open the application
			  When I enter username as Bob
			  And I enter password as xyz1
			  And I enter title as title2
			  And I press submit
			  Then I am logged
			
			Scenario: Create Simple Test
			  Given I open the application
			  When I enter username as Ben
			  And I enter password as xyz2
			  And I enter title as title3
			  And I press submit
			  Then I am logged
			
			Scenario: : Test without table
			  Given I open the application again
			  When I enter username as Tiger
			  And I enter password as Shot
			  Then I enter title as Mr
			  And press submit
			
			Scenario: Create New Simple Test Table different order
			  Given I open the application
			  When I enter username as Rob
			  And I enter password as password1
			  And I enter title as title1
			  And I press submit
			  Then I am logged
			
			Scenario: Create New Simple Test Table different order
			  Given I open the application
			  When I enter username as Bob
			  And I enter password as password2
			  And I enter title as title2
			  And I press submit
			  Then I am logged
