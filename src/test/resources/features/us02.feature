Feature: As a user, I want to search for a specific user by their id
  so that I can quickly find the information I need.

  Scenario: Retrieve single user
    Given I logged Library api as a "librarian" with "application/json" header
    When I send GET request to "/get_user_by_id/" + id endpoint
    Then Response Content type is "application/json; charset=utf-8", status code 200
    And id field should be same with path param
    And following fields should not be null
      | full_name |
      | email     |
      | password  |