package web.stepDefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utilities.ConfigReader;
import web.drivers.DriverManager;
import web.helpers.RandomDataGenerator;
import web.pages.RegisterPage;

import com.github.javafaker.Faker;

public class RegisterSteps {

    RegisterPage registerPage = new RegisterPage(); // Will fetch driver from DriverManager

    @Given("I am on the registration page")
    public void i_am_on_the_registration_page() {
        String baseUrl = ConfigReader.getProperty("url");
        DriverManager.getDriver().get(baseUrl);
        registerPage.isRegisterTitleDisplayed();
    }

    @When("I submit the registration form with dummy data")
    public void i_submit_the_registration_form_with_dummy_data() {
        Faker faker = new Faker();
        registerPage.enterFullName(faker.name().fullName());
        registerPage.enterEmail(faker.internet().emailAddress());
        registerPage.enterPhone(faker.phoneNumber().subscriberNumber(10));
        registerPage.enterAddress(faker.address().fullAddress());
        registerPage.selectGender(RandomDataGenerator.getRandomGender());
        List<String> randomDays = RandomDataGenerator.getRandomWeekdays(3); // Get 3 unique weekdays
        for (String day : randomDays) {
            registerPage.checkDay(day);
        }
        registerPage.selectCountry(RandomDataGenerator.getRandomCountry());
        registerPage.selectColor(RandomDataGenerator.getRandomColor());
        registerPage.selectAnimal(RandomDataGenerator.getRandomAnimal());

        Map<String, String> datePair = RandomDataGenerator.getRandomDatePair();
        registerPage.setDate1(datePair.get("date1_mmddyyyy"));
        registerPage.setDate2(datePair.get("date2_mmddyyyy"));
        registerPage.setDateRange(datePair.get("date1_ddmmyyyy"), datePair.get("date2_ddmmyyyy"));
        String expectedMessage = "You selected a range of " + datePair.get("range_days") + " days.";
        registerPage.verifySuccessDateSubmittedMessage(expectedMessage);

    }

    @When("I upload a single file named {string}")
    public void i_upload_a_single_file_named(String fileName) {
        registerPage.uploadSingleFile(fileName);
    }

    @When("I upload multiple files")
    public void i_upload_multiple_files(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        List<String> fileNames = new ArrayList<>();

        for (Map<String, String> row : rows) {
            fileNames.add(row.get("file")); // "file" is the header name
        }

        registerPage.uploadMultipleFiles(fileNames.toArray(new String[0]));
    }

    @Then("I verify exactly {int} files are uploaded")
    public void i_verify_exactly_files_are_uploaded(int expectedFileCount) {
        registerPage.verifyExactNumberOfFilesUploaded(expectedFileCount);
    }
}
