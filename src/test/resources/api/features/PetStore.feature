Feature: Pet Store API

    Scenario: Add new Pet
        Given set request body using "addPetRequest.json"
        When I send a "POST" request to "/pet"
        Then response status code should be 200
        And response should match schema "addPetSchema.json"

    Scenario Outline: Find Pet by Status
        Given set query parameters
            | key    | value    |
            | status | <status> |
        When I send a "GET" request to "/pet/findByStatus"
        Then response status code should be 200
        And all values of "status" in response array should equal "<status>"
        And response should match schema "findPetByStatusSchema.json"

        Examples:
            | status    |
            | available |
            | pending   |
            | sold      |