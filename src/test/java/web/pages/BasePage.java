package web.pages;

import io.qameta.allure.Allure;
import web.drivers.DriverManager;

import java.io.File;
import java.time.Duration;
import java.util.Set;
import java.util.StringJoiner;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int MAX_ATTEMPTS = 3;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected void click(WebElement locator) {
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
                return;
            } catch (Exception e) {
                System.out.println(
                        "Attempt " + (attempts + 1) + " failed on WebElement: " + locator + " -> " + e.getMessage());
                attempts++;
                if (attempts == 3) {
                    throw new RuntimeException("Failed to click WebElement after 3 attempts: " + locator, e);
                }
            }
        }
    }

    protected void click(By locator) {
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            try {
                driver.findElement(locator).click();
                return;
            } catch (Exception e) {
                System.out.println(
                        "Attempt " + (attempts + 1) + " failed on By locator: " + locator + " -> " + e.getMessage());
                attempts++;
                if (attempts == 3) {
                    throw new RuntimeException("Failed to click By locator after 3 attempts: " + locator, e);
                }
            }
        }
    }

    public void clickElementWithText(String text) {
        By locator = By.xpath(
                "//*[normalize-space(text())='" + text + "' " +
                        "or @value='" + text + "' " +
                        "or @aria-label='" + text + "' " +
                        "or @title='" + text + "']");
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            return;

        } catch (TimeoutException | ElementClickInterceptedException e1) {
            try {
                WebElement element = driver.findElement(locator);
                element.click();
                return;

            } catch (Exception e2) {
                System.out.println("Direct click failed. Trying JS click...");
                try {
                    WebElement element = driver.findElement(locator);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    return;
                } catch (Exception jsEx) {
                    System.out.println("JavaScript click also failed: " + jsEx.getMessage());
                }
            }
        }
    }

    protected void type(WebElement locator, String text) {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOf(locator));
            field.clear();
            field.sendKeys(text);
        } catch (Exception e) {
            System.out.println("Typing failed on WebElement: " + locator + " -> " + e.getMessage());
            throw e;
        }
    }

    protected void type(By locator, String text) {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            field.clear();
            field.sendKeys(text);
        } catch (Exception e) {
            System.out.println("Typing failed on By locator: " + locator.toString() + " -> " + e.getMessage());
            throw e;
        }
    }

    public void typeDateByJS(WebElement dateInput, String date) {
        try {
            switchToDefault();
            wait.until(ExpectedConditions.visibilityOf(dateInput));
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].removeAttribute('readonly')",
                    dateInput);
            dateInput.clear(); // optional
            dateInput.sendKeys(date);
        } catch (Exception e) {
            System.out.println("Failed to type date using JS: " + e.getMessage());
            throw e;
        }
    }

    protected String getText(WebElement locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(locator)).getText();
        } catch (Exception e) {
            System.out.println("Failed to get text from WebElement: " + locator + " -> " + e.getMessage());
            throw e;
        }
    }

    protected String getText(By locator) {
        try {
            return driver.findElement(locator).getText();
        } catch (Exception e) {
            System.out.println("Failed to get text from WebElement: " + locator + " -> " + e.getMessage());
            throw e;
        }
    }

    protected void isDisplayed(WebElement locator) {
        try {
            wait.until(ExpectedConditions.visibilityOf(locator));
        } catch (Exception e) {
            System.out.println("Element not visible: " + locator + " -> " + e.getMessage());
            throw e;
        }
    }

    protected void isDisplayed(By locator) {
        try {
            driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            System.out.println("Element not visible (By): " + locator + " -> " + e.getMessage());
            throw e;
        }
    }

    protected void switchFrame(WebElement frameElement) {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
        } catch (Exception e) {
            System.out.println("Switch frame failed: " + e.getMessage());
            throw e;
        }
    }

    protected void uploadFile(WebElement uploadButtonElement, String fileName) {
        try {
            String fullPath;
            File file = new File(fileName);

            // Jika fileName sudah absolute path
            if (file.isAbsolute()) {
                fullPath = file.getAbsolutePath();
            } else {
                fullPath = System.getProperty("user.dir") + "/src/test/resources/web/test-data/files/" + fileName;
            }

            if (!new File(fullPath).exists()) {
                throw new RuntimeException("Upload file not found: " + fullPath);
            }

            uploadButtonElement.sendKeys(fullPath);
            System.out.println("Uploaded file: " + fullPath);
        } catch (Exception e) {
            System.out.println("Upload failed for file: " + fileName + " -> " + e.getMessage());
            throw e;
        }
    }

    protected void uploadFiles(WebElement uploadElement, String... fileNames) {
        try {
            StringJoiner filesPathJoiner = new StringJoiner(System.lineSeparator());

            for (String fileName : fileNames) {
                File file = new File(fileName);
                String fullPath;

                if (file.isAbsolute()) {
                    fullPath = file.getAbsolutePath();
                } else {
                    fullPath = System.getProperty("user.dir") + "/src/test/resources/web/test-data/files/" + fileName;
                }

                if (!new File(fullPath).exists()) {
                    throw new RuntimeException("Upload file not found: " + fullPath);
                }

                filesPathJoiner.add(fullPath);
            }

            String allFiles = filesPathJoiner.toString();
            uploadElement.sendKeys(allFiles);
            System.out.println("Uploaded multiple files:\n" + allFiles);
        } catch (Exception e) {
            System.out.println("Upload multiple files failed -> " + e.getMessage());
            throw e;
        }
    }

    public void verifyMessage(WebElement messageLocator, String expectedMessage) {
        try {
            switchToDefault();
            wait.until(ExpectedConditions.visibilityOf(messageLocator));
            String actualMessage = messageLocator.getText().trim();

            Allure.step("Validation message displayed: " + actualMessage);

            Assert.assertEquals(
                    actualMessage,
                    expectedMessage,
                    "Validation message does not match. Expected: '" + expectedMessage + "', but found: '"
                            + actualMessage + "'");
        } catch (Exception e) {
            System.out.println("Validation message check failed -> " + e.getMessage());
            throw e;
        }
    }

    public void verifyMessageContains(WebElement messageLocator, String expectedPartialMessage) {
        try {
            switchToDefault();
            wait.until(ExpectedConditions.visibilityOf(messageLocator));
            String actualMessage = messageLocator.getText().trim();

            Allure.step("Validation message displayed: " + actualMessage);

            Assert.assertTrue(
                    actualMessage.contains(expectedPartialMessage),
                    "Validation message does not contain expected text. Expected to contain: '" + expectedPartialMessage
                            + "', but found: '" + actualMessage + "'");
        } catch (Exception e) {
            System.out.println("Validation partial message check failed -> " + e.getMessage());
            throw e;
        }
    }

    public void switchToNewWindow(String originalWindow) {
        try {
            wait.until(driver -> driver.getWindowHandles().size() > 1);
            Set<String> allWindows = driver.getWindowHandles();
            for (String window : allWindows) {
                if (!window.equals(originalWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Switch to new window failed -> " + e.getMessage());
            throw e;
        }
    }

    public void selectDropdownByValue(WebElement locatorElement, String value) {
        try {
            Select select = new Select(locatorElement);
            // select.deselectAll();
            select.selectByValue(value);
        } catch (Exception e) {
            System.out.println("Select dropdown failed -> " + e.getMessage());
            throw e;
        }
    }

    protected void switchToDefault() {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            System.out.println("Switch to default content failed -> " + e.getMessage());
            throw e;
        }
    }
}
