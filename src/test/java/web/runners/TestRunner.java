package web.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/web/features", glue = { "web.stepDefinitions", "web.hooks" }, plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        "json:target/allure-results/results.json" }, tags = "@smoke", monochrome = true)
public class TestRunner extends AbstractTestNGCucumberTests {

}
