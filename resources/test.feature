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
