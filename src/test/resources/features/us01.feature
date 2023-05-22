Feature: As a librarian, I want to retrieve all users


  Scenario: Retrieve all users from the API endpoint
    Given I logged Library api as a "librarian" with "application/json" header
    When I send GET request to "/get_all_users" endpoint
    Then Response Content type is "application/json; charset=utf-8", status code 200,"id" and "name" not null



