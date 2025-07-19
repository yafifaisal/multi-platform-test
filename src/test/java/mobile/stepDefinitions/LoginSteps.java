package mobile.stepDefinitions;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import mobile.drivers.DriverManager;
import mobile.pages.LoginPage;

public class LoginSteps {

    private final AppiumDriver driver;
    private final LoginPage loginPage;

    public LoginSteps() {
        this.driver = DriverManager.getDriver();
        String platform = System.getProperty("platform", "android");
        this.loginPage = new LoginPage(driver, platform);
    }

    @Given("I am on the registration page")
    public void i_am_on_the_registration_page() {
        loginPage.isCreateNewWalletButtonDisplayed();
    }

    @Given("I tap the create new wallet button")
    public void i_tap_the_create_new_wallet_button() {
        loginPage.clickCreateNewWalletButton();
    }

    @And("I submit passcode with {string}")
    public void i_submit_passcode_with(String passcode) {
        loginPage.enterPasscode(passcode);
    }

}
