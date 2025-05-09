package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.FileElectronicRecordPage;
import utilities.FileHelper;

public class FileElectronicRecordSteps {

    FileElectronicRecordPage fileElectronicRecordPage = new FileElectronicRecordPage();

    @Given("I successfully landed to File Electronic Record page")
    public void i_successfully_landed_to_file_electronic_record_page() {
        fileElectronicRecordPage.isFileElectronicRecordPageDisplayed();
    }

    @Given("I select {string} document template at File Electronic Record page")
    public void i_select_document_template(String decoumentTemplate) {
        fileElectronicRecordPage.selectDocumentTemplate(decoumentTemplate);
    }

    @Given("I upload {string} document {string}")
    public void i_upload_new_document(String documentType, String fileName) {
        if (documentType.equalsIgnoreCase("new")) {
            String uniqueFileName = FileHelper.generateTimestampedPdf(FileHelper.removePdfExtension(fileName));
            fileElectronicRecordPage.uploadDocument(uniqueFileName);
        } else {
            fileElectronicRecordPage.uploadDocument(fileName);
        }
    }

    @Given("I select folder {string}")
    public void i_select_folder(String folderName) {
        fileElectronicRecordPage.selectFolder(folderName);
    }

    @Given("I fill keywords with {string}")
    public void i_fill_keywords_with_text(String keywords) {
        fileElectronicRecordPage.fillKeywords(keywords);
    }

    @When("I click {word} button")
    public void i_click_button(String buttonType) {
        if (buttonType.equalsIgnoreCase("save")) {
            fileElectronicRecordPage.clickSaveButton();
        } else if (buttonType.equalsIgnoreCase("save as draft")) {
            fileElectronicRecordPage.clickSaveAsDraftButton();
        } else {
            throw new IllegalArgumentException("Unsupported button type: " + buttonType);
        }
    }

    @Then("Assert that dialog message equal with {string}")
    public void assert_dialog_message(String message) {
        fileElectronicRecordPage.validateDialogMessage(message);
    }

}
