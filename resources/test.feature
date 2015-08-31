Scenario Outline: Create ABC
  Given I open the application
  When I enter username as <username>
  And I enter password as <password>
  Then I enter title as <title>
  And press submit


Examples:

| username | password | title |
| Rob      | xyz1      | title1 |
| Bob      | xyz1      | title2 | value
| Ben      | xyz2      | title3 |

Scenario: Create OthertestCs
  Given I open the application again
  When I enter username as Tiger
  And I enter password as Shot
  Then I enter title as Mr
  And press submit
