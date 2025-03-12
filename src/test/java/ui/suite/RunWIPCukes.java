package ui.suite;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import ui.config.Browsers;
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"ui.steps"},
        plugin = {"pretty",
                "html:reports/cucumber-report.html",
                "summary",
                "json:reports/cucumber-advanced-reports/cucumber.json"},
        monochrome = true,
//        dryRun=true,
        tags = "@p1_test")
public class RunWIPCukes {

    @BeforeClass
    public static void launchDriver() {
        Browsers.setUp();
    }

    @AfterClass
    public static void tearDown() {
        Browsers.tearDown();
    }}
