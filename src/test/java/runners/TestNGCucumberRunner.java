package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features", glue = { "stepDefinitions", "hooks" }, plugin = { "pretty",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }, tags = "@smoke", monochrome = true)
public class TestNGCucumberRunner extends AbstractTestNGCucumberTests {
    // No extra code needed. TestNG will pick up the scenarios.
}
