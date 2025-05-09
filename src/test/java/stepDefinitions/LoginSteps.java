package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.LoginPage;
import utilities.ConfigReader;
import utilities.PageManager;
import utilities.UserDataReader;

import java.util.Map;

import drivers.DriverManager;

public class LoginSteps {
    LoginPage loginPage = new LoginPage();
    private PageManager pages = new PageManager();

    @Given("I am on the login page")
    public void i_am_on_login_page() {
        String baseUrl = ConfigReader.getProperty("url");
        DriverManager.getDriver().get(baseUrl);
    }

    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @Then("I click the login button")
    public void i_click_login_button() {
        loginPage.clickLogin();
    }

    @Given("I am successfully logged in with user {string}")
    public void i_am_sufccessfully_logged_in_with_user(String userAlias) {
        String baseUrl = ConfigReader.getProperty("url");
        DriverManager.getDriver().get(baseUrl);

        Map<String, String> creds = UserDataReader.getCredentialsByAlias(userAlias);
        String username = creds.get("username");
        String password = creds.get("password");

        pages.getLoginPage().enterUsername(username);
        pages.getLoginPage().enterPassword(password);
        pages.getLoginPage().clickLogin();
        pages.getDashboardPage().isDashboardTitleDisplayed();
    }
}
