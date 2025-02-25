package qumu.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/java/qumu/features/UI-Test.feature"},
        glue = {"qumu.stepDefinitions", "qumu.hook"},
        plugin = {
                "pretty",
                "html:test-output/cucumber-reports/cucumber-pretty-ui",
                "json:test-output/cucumber-reports/CucumberTestReport.json",
                "rerun:test-output/cucumber-reports/rerun.txt",
                "testng:test-output/cucumber-reports/testng.xml"
        }
)
public class UIRunner extends AbstractTestNGCucumberTests {


}
