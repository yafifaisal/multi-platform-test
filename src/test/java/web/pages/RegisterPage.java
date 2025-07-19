package web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import io.qameta.allure.Allure;
import web.drivers.DriverManager;

public class RegisterPage extends BasePage {

    private WebDriver driver;

    public RegisterPage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "name")
    private WebElement fullNameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "phone")
    private WebElement phoneField;

    @FindBy(id = "textarea")
    private WebElement addressField;

    @FindBy(id = "female")
    private WebElement femaleRadio;

    @FindBy(id = "male")
    private WebElement maleRadio;

    @FindBy(id = "country")
    private WebElement countryDropdown;

    @FindBy(id = "days")
    private WebElement daysDropdown;

    @FindBy(id = "sunday")
    private WebElement sundayCheckbox;

    @FindBy(id = "monday")
    private WebElement mondayCheckbox;

    @FindBy(id = "tuesday")
    private WebElement tuesdayCheckbox;

    @FindBy(id = "wednesday")
    private WebElement wednesdayCheckbox;

    @FindBy(id = "thursday")
    private WebElement thursdayCheckbox;

    @FindBy(id = "friday")
    private WebElement fridayCheckbox;

    @FindBy(id = "saturday")
    private WebElement saturdayCheckbox;

    @FindBy(id = "colors")
    private WebElement colorDropdown;

    @FindBy(id = "animals")
    private WebElement animalsDropdown;

    @FindBy(id = "datepicker")
    private WebElement datePickerInput;

    @FindBy(id = "txtDate")
    private WebElement datePicker2Input;

    @FindBy(id = "start-date")
    private WebElement startDateInput;

    @FindBy(id = "end-date")
    private WebElement endDateInput;

    @FindBy(css = "button.submit-btn")
    private WebElement submitDateButton;

    @FindBy(id = "Frame1")
    private WebElement dashboardIFrame;

    @FindBy(css = "div.titlewrapper > h1.title")
    private WebElement registerTitle;

    @FindBy(id = "singleFileInput")
    private WebElement singleFileUploadInput;

    @FindBy(css = "#singleFileForm button[type='submit']")
    private WebElement uploadSingleFileButton;

    @FindBy(id = "multipleFilesInput")
    private WebElement multiFileUploadInput;

    @FindBy(css = "#multipleFilesForm button[type='submit']")
    private WebElement uploadMultipleFileButton;

    @FindBy(id = "result")
    private WebElement resultMessage;

    @FindBy(id = "singleFileStatus")
    private WebElement singleFileStatusText;

    @FindBy(id = "multipleFilesStatus")
    private WebElement multipleFileStatusText;

    public void enterFullName(String name) {
        type(fullNameField, name);
    }

    public void enterEmail(String email) {
        type(emailField, email);
    }

    public void enterPhone(String phone) {
        type(phoneField, phone);
    }

    public void enterAddress(String address) {
        type(addressField, address);
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("female")) {
            click(femaleRadio);
        } else if (gender.equalsIgnoreCase("male")) {
            click(maleRadio);
        }
    }

    public void checkDay(String day) {
        switch (day.toLowerCase()) {
            case "sunday":
                click(sundayCheckbox);
                break;
            case "monday":
                click(mondayCheckbox);
                break;
            case "tuesday":
                click(tuesdayCheckbox);
                break;
            case "wednesday":
                click(wednesdayCheckbox);
                break;
            case "thursday":
                click(thursdayCheckbox);
                break;
            case "friday":
                click(fridayCheckbox);
                break;
            case "saturday":
                click(saturdayCheckbox);
                break;
            default:
                throw new IllegalArgumentException("❌ Invalid day: " + day);
        }
    }

    public void selectCountry(String country) {
        selectDropdownByValue(countryDropdown, country);
    }

    public void selectColor(String color) {
        selectDropdownByValue(colorDropdown, color);
    }

    public void selectAnimal(String animal) {
        selectDropdownByValue(animalsDropdown, animal);
    }

    public void setDate1(String days) {
        typeDateByJS(datePickerInput, days);
    }

    public void setDate2(String days) {
        typeDateByJS(datePicker2Input, days);
    }

    public void setDateRange(String startDate, String endDate) {
        type(startDateInput, startDate);
        type(endDateInput, endDate);
        clickSubmit();
    }

    public void clickSubmit() {
        click(submitDateButton);
    }

    public void verifySuccessDateSubmittedMessage(String message) {
        verifyMessage(resultMessage, message);
    }

    public void isRegisterTitleDisplayed() {
        isDisplayed(registerTitle);
    }

    private void submitSingleFile() {
        click(uploadSingleFileButton);
    }

    private void submitMultipleFiles() {
        click(uploadMultipleFileButton);
    }

    public void uploadSingleFile(String fileName) {
        uploadFile(singleFileUploadInput, fileName);
        submitSingleFile();
    }

    public void verifySuccessUploadSingleFileMessage() {
        verifyMessageContains(singleFileStatusText, "Single file selected:");
    }

    public void uploadMultipleFiles(String... fileNames) {
        uploadFiles(multiFileUploadInput, fileNames);
        submitMultipleFiles();
    }

    public void verifySuccessUploadMultiFileMessage() {
        verifyMessageContains(multipleFileStatusText, "Multiple files selected:");
    }

    public void verifyExactNumberOfFilesUploaded(int expectedFileCount) {
        wait.until(ExpectedConditions.visibilityOf(multipleFileStatusText));
        String content = multipleFileStatusText.getAttribute("innerHTML");
        int actualFileCount = content.split("<br>").length - 1;

        Allure.step("Files listed: " + actualFileCount);
        Assert.assertEquals(actualFileCount, expectedFileCount);
    }

}