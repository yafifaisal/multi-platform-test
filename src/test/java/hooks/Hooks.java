package hooks;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import drivers.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import utilities.AllureEnvironmentWriter;

public class Hooks {

    @BeforeAll
    public static void cleanScreenshotsFolder() {
        File folder = new File("src/test/reports/screenshots/");
        if (folder.exists()) {
            try {
                FileUtils.cleanDirectory(folder); // removes all files but keeps the folder
                System.out.println("Screenshots folder cleaned.");
            } catch (IOException e) {
                System.err.println("Failed to clean screenshots folder: " + e.getMessage());
            }
        } else {
            folder.mkdirs(); // create folder if it doesn't exist
            System.out.println("Screenshots folder created.");
        }
    }

    @Before
    public void setUp() {
        DriverManager.initDriver();
        AllureEnvironmentWriter.setAllureEnvironmentInformation();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // Take screenshot in memory
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);

            // Attach to report
            scenario.attach(screenshot, "image/png", "Failure Screenshot");

            // Save screenshot to file with timestamp
            File screenshotFile = ts.getScreenshotAs(OutputType.FILE);

            // Create a timestamped filename
            String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
            String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_"); // sanitize name
            String screenshotPath = "src/test/reports/screenshots/" + scenarioName + "-" + timestamp + ".png";

            try {
                File destinationFile = new File(screenshotPath);
                FileUtils.copyFile(screenshotFile, destinationFile);
                System.out.println("Screenshot saved to: " + screenshotPath);
            } catch (IOException e) {
                System.err.println("Failed to save screenshot to file: " + e.getMessage());
            }
        }
        DriverManager.quitDriver();
    }

}
