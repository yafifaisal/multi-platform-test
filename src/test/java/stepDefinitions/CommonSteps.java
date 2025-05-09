package stepDefinitions;

import io.cucumber.java.en.Given;
import utilities.PageManager;

public class CommonSteps {

    private PageManager pages = new PageManager();

    @Given("I click element with text {string}")
    public void i_click_button(String textButton) {
        pages.getBasePage().clickElementWithText(textButton);
    }
}
