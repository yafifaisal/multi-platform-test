package pages;

import drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    private WebDriver driver;

    public LoginPage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "userId")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "submitButton")
    private WebElement loginButton;

    public void enterUsername(String username) {
        type(usernameField, username);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void clickLogin() {
        click(loginButton);
    }
}
