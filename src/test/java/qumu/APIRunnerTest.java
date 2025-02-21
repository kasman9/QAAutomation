package qumu;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/java/API-Test.feature"},
        glue = {"qumu"},
        plugin = {
                "pretty",
                "html:test-output/cucumber-reports/cucumber-pretty-api",
                "json:test-output/cucumber-reports/CucumberTestReport.json",
                "rerun:test-output/cucumber-reports/rerun.txt",
                "testng:test-output/cucumber-reports/testng.xml"
        }
)
public class APIRunnerTest extends AbstractTestNGCucumberTests {


}
