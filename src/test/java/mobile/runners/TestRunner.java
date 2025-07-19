package mobile.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/mobile/features", glue = { "mobile.stepDefinitions",
                "mobile.hooks" }, plugin = {
                                "pretty",
                                "html:target/cucumber-reports/mobile.html",
                                "json:target/cucumber-reports/mobile.json",
                                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
                }, tags = "@smoke", // optional, remove if not filtering
                monochrome = true)
public class TestRunner extends AbstractTestNGCucumberTests {
}