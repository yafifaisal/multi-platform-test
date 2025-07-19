@smoke
Feature: Test select date picker and upload image

    Scenario: Test select date picker and upload image
        Given I am on the registration page
        And I submit the registration form with dummy data
        When I upload a single file named "sample.pdf"
        And I upload multiple files
            | file            |
            | sample.pdf      |
            | sample copy.pdf |
        Then I verify exactly 2 files are uploaded
