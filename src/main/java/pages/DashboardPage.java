package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import drivers.DriverManager;

public class DashboardPage extends BasePage {

    private WebDriver driver;

    public DashboardPage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id='headerId']//*[contains(text(), 'Record Manager')]")
    private WebElement recordManagerTitle;

    @FindBy(xpath = "//*[@id='newDocument']")
    private WebElement newDocumentButton;

    @FindBy(xpath = "//*[@id='qtip-0-content']//*[contains(text(),'Paper Document')]")
    private WebElement newPaperDocumentButton;

    @FindBy(xpath = "//*[@id='qtip-0-content']//*[contains(text(),'Electronic Document')]")
    private WebElement newElectronicDocumentButton;

    @FindBy(xpath = "//iframe[@id='mainFrame']")
    private WebElement dashboardIFrame;

    public void isDashboardTitleDisplayed() {
        isDisplayed(recordManagerTitle);
        switchFrame(dashboardIFrame);
    }

    public void clickNewDocument() {
        click(newDocumentButton);
    }

    public void clickNewPaperDocument() {
        click(newPaperDocumentButton);
    }

    public void clickElectronicDocument() {
        click(newElectronicDocumentButton);
    }

}
