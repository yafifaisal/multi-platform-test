package stepDefinitions;

import io.cucumber.java.en.Given;
import pages.DashboardPage;

public class DashboardSteps {

    DashboardPage dashboardPage = new DashboardPage();

    @Given("I successfully landed to dashboard page")
    public void i_successfully_landed_to_dashboard_page() {
        dashboardPage.isDashboardTitleDisplayed();
    }

    @Given("I click New Document button at Dashboard Page")
    public void i_click_button_at_dashboard_page() {
        dashboardPage.clickNewDocument();
    }

    @Given("I click Paper Document button at New Document list")
    public void i_click_button_at_document_list() {
        dashboardPage.clickNewPaperDocument();
    }

    @Given("I click Electronic Document button at New Document list")
    public void i_click_electronic_button_at_document_list() {
        dashboardPage.clickElectronicDocument();
    }

}
