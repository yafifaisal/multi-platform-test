package utilities;

import pages.BasePage;
import pages.DashboardPage;
import pages.LoginPage;

public class PageManager {

    public BasePage getBasePage() {
        return new BasePage();
    }

    public LoginPage getLoginPage() {
        return new LoginPage();
    }

    public DashboardPage getDashboardPage() {
        return new DashboardPage();
    }
}