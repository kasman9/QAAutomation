package qumu;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/java/API-Test.feature"},  // ✅ Ensure feature files are in the correct directory
        glue = {"qumu"},  // ✅ Update this to match the package where step definitions exist
        plugin = {  // ✅ Using `plugin` instead of deprecated `format`
                "pretty",
                "html:test-output/cucumber-reports/cucumber-pretty-api",
                "json:test-output/cucumber-reports/CucumberTestReport.json",
                "rerun:test-output/cucumber-reports/rerun.txt",
                "testng:test-output/cucumber-reports/testng.xml"
        }
)
public class APIRunnerTest extends AbstractTestNGCucumberTests {  // ✅ Use AbstractTestNGCucumberTests


}
