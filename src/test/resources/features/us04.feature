Feature: As a librarian, I want to create a new user

  Scenario: Create a new user API
    Given I logged Library api as a "librarian" with "application/json" header
    And Request Content Type header is "application/x-www-form-urlencoded"
    When I send POST request to "/add_user" endpoint
    Then status code should be 200
    And Response Content type is "application/json; charset=utf-8"
    And the field value for "message" path should be equal to "The user has been created."
    And "user_id" field should not be null
    And created user information should match with Database
    Then newly created user can login into system



