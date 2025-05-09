package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import drivers.DriverManager;

public class FileElectronicRecordPage extends BasePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public FileElectronicRecordPage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @FindBy(xpath = "//*[contains(text(),'File Electronic Record')]")
    private WebElement fileElectronicRecordTitle;

    @FindBy(id = "select2-docHtmlTemplateId-container")
    private WebElement documentTemplateDropdown;

    @FindBy(id = "docName")
    private WebElement documentSubjectField;

    @FindBy(id = "fileRefNo")
    private WebElement folderField;

    @FindBy(xpath = "//*[@id='frmProfileFrm']//*[@title='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//*[@id='frmProfileFrm']//*[@title='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//*[@id='frmProfileFrm']//*[@title='Save As Draft']")
    private WebElement saveAsDraftButton;

    @FindBy(xpath = "//*[@aria-labelledby='ui-id-2']//*[@id='dialog-message']")
    private WebElement dialogMessage;

    @FindBy(id = "uploadDocu")
    private WebElement addDocumentButton;

    @FindBy(id = "mainFrame")
    private WebElement mainFrame;

    @FindBy(id = "idBtnFileRef")
    private WebElement addFolderButton;

    @FindBy(xpath = "//*[@name='searchCriteria']")
    private WebElement searchCriteriaField;

    @FindBy(xpath = "//*[@id='frmSearchFileRefsFrm']//input[@value='Search']")
    private WebElement searchCriteriaButton;

    @FindBy(xpath = "//*[@id='frmSearchFileRefsFrm']//input[@value='OK']")
    private WebElement submitOkButton;

    @FindBy(id = "keywords1")
    private WebElement keywordsField;

    @FindBy(id = "mainBodyId")
    private WebElement mainBodyFileElectronicRecordPage;

    private By firstListRadioButton = By.xpath("//input[@type='radio' and contains(@id,'fileRefSysId')][1]");

    public void isFileElectronicRecordPageDisplayed() {
        switchFrame(mainFrame);
    }

    public void selectDocumentTemplate(String templateText) {
        click(documentTemplateDropdown);
        WebElement option = generateXpathDocumentTemplate(templateText);
        click(option);
    }

    private WebElement generateXpathDocumentTemplate(String templateText) {
        By optionLocator = By.xpath(
                "//li[contains(@class,'select2-results__option') and normalize-space(text())='" + templateText + "']");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        return option;
    }

    public void uploadDocument(String fileName) {
        uploadFile(addDocumentButton, fileName);
    }

    public void clickAddFolderButton() {
        click(addFolderButton);
    }

    private void searchFolder(String folderName) {
        type(searchCriteriaField, folderName);
        click(searchCriteriaButton);
    }

    private void selectFirstFolderAndConfirm() {
        click(firstListRadioButton);
        click(submitOkButton);
    }

    public void selectFolder(String folderName) {
        String originalWindow = driver.getWindowHandle();
        clickAddFolderButton();
        switchToNewWindow(originalWindow);
        searchFolder(folderName);
        selectFirstFolderAndConfirm();
        driver.switchTo().window(originalWindow);
    }

    public void fillKeywords(String keywords) {
        type(keywordsField, keywords);
    }

    public void clickSaveButton() {
        click(saveButton);
    }

    public void clickSaveAsDraftButton() {
        click(saveAsDraftButton);
    }

    public void validateDialogMessage(String message) {
        verifyMessage(dialogMessage, message);
    }
}