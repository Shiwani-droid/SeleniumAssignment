Feature: Test API endpoint

  Scenario Outline: Predicting the gender of single name
    Given I have access to the gender predict API
    And   I set the query parameter "name" to "<firstname>"
    When  I send a GET request
    Then  The response status code should be "<code>"
    And   the response should contain correct name "<firstname>"
    And   the response should contain valid gender "<gender>"

    Examples: 
      | firstname      | gender | code  |
      | peter          | male   | 200   |
      | Rob            | male   | 200   |
      | %2$%5          | null   | 200   |
      |                | null   | 200   |
      | A-B-C-D        | null   | 200   |
      | JIM            | male   | 200   |
      | JiLl           | female | 200   |
      
  Scenario Outline: Validate API endpoint for specific country
    Given I have access to the gender predict API
    And   I set the query parameter "name" to "<firstname>" and "country_id" to "<country>"
    When  I send a GET request
    Then  The response status code should be "<code>"
    And   the response should contain correct name "<firstname>"
    And   the response should contain valid countryname "<country>"
    And   the response should contain valid gender "<gender>"

    Examples: 
      | firstname      |country| gender | code|
      | kim            | US    | female | 200 |
      | tim            | US    | male   | 200 |
      | %2$%5          | %2$%5 | null   | 200 |
      | Nidhi          | IN    | female | 200 |
      | Shelly         | IN    | female | 200 |
      | james          | AAA   | null   | 200 |
      | Sam            |       | null   | 200 |
  
  Scenario Outline: Predicting the gender with multiple names 
    Given I have access to the gender predict API
    And   I set multiple names query parameter "<name1>", "<name2>", "<name3>"
    When  I send a GET request
    Then  The response status code should be "<code>"
    And   the response should be a JSON array of <expectedSize> objects
    And   the response for "<name1>" should have gender "<gender1>"
    And   the response for "<name2>" should have gender "<gender2>"
    And   the response for "<name3>" should have gender "<gender3>"

    Examples: 
      | name1 |name2 |name3 |gender1| gender2| gender3|code|expectedSize|
      | kim   | tim  |rim   |female |male    |female  |200 |3           |
      | Lily  | Simon|Mili  |female |male    |female  |200 |3           |
      
  
