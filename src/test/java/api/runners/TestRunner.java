package api.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/api/features", glue = { "api.stepDefinitions", "common" }, plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber-reports.json"
}, monochrome = true)
public class TestRunner extends AbstractTestNGCucumberTests {
}
