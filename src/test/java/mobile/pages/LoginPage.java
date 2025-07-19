package mobile.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class LoginPage {

    private final AppiumDriver driver;

    public LoginPage(AppiumDriver driver, String platform) {
        this.driver = driver;
    }

    // Locators
    private final By createNewWalletButton = By
            .xpath("//*[@resource-id='CreateNewWalletButton']//android.widget.Button");

    // Actions
    public void isCreateNewWalletButtonDisplayed() {
        driver.findElement(createNewWalletButton).isDisplayed();
    }

    public void clickCreateNewWalletButton() {
        driver.findElement(createNewWalletButton).click();
    }

    public void enterPasscode(String passcode) {
        for (char digit : passcode.toCharArray()) {
            String xpath = "//android.widget.TextView[@text='" + digit + "']";
            driver.findElement(By.xpath(xpath)).click();
        }
    }

}
