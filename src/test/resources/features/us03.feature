Feature: As a librarian, I want to create a new book

  Scenario: Create a new book API
    Given I logged Library api as a "librarian" with "application/json" header
    And Request Content Type header is "application/x-www-form-urlencoded"
    When I send POST request to "/add_book" endpoint
    Then status code should be 200
    And Response Content type is "application/json; charset=utf-8"
    And the field value for "message" path should be equal to "The book has been created."
    And "book_id" field should not be null
    And Database and API created book information must match
