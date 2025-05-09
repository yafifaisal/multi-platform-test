@smoke
Feature: File Electronic Record

    @negative
    Scenario Outline: Filing with Valid PDF Record and Clicking Action
        # Precondition: Reset File and Folder so we can repeatedly upload same file in same folder
        Given I am successfully logged in with user "file electronic record user"
        And I click New Document button at Dashboard Page
        And I click Electronic Document button at New Document list
        And I select "<template>" document template at File Electronic Record page
        And I select folder "<folder>"
        And I successfully landed to File Electronic Record page
        And I upload "<uploadMode>" document "<fileName>"
        And I fill keywords with "<keywords>"
        And I click element with text "<action>"
        Then Assert that dialog message equal with "<expectedMessage>"

        Examples:
            | template | folder                              | uploadMode | fileName   | keywords         | action        | expectedMessage                              |
            | Default  | QA TECHNICAL TEST-ELECTRONIC FOLDER | new        | sample.pdf | Details Keywords | Save          | The record is registered successfully.       |
            | Default  | QA TECHNICAL TEST-ELECTRONIC FOLDER | new        | sample.pdf | Details Keywords | Save As Draft | The draft record is registered successfully. |

    @positive
    Scenario: Filing an Existing Record to the Same Folder
        Given I am successfully logged in with user "file electronic record user"
        And I click New Document button at Dashboard Page
        And I click Electronic Document button at New Document list
        And I select "Default" document template at File Electronic Record page
        And I select folder "QA TECHNICAL TEST-ELECTRONIC FOLDER"
        And I successfully landed to File Electronic Record page
        And I upload "existing" document "sample.pdf"
        And I fill keywords with "Details Keywords"
        And I click element with text "Save"
        Then Assert that dialog message equal with "Record upload failure. The record already exists in the same file reference."
