package qumu.hook;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import qumu.BrowserSetup;
import qumu.LoadProp;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class APIHook {
    List<String> tags = null;

    @Before(order = 1)
    public void beforeTest(Scenario scenario) {

        tags = new ArrayList<>(scenario.getSourceTagNames());
        // Start browser only for UI tests
        if (tags.contains("@API")) {
            scenario.log("API test run begins");
        }
    }


    @After()
    public void afterTest(Scenario scenario) {
        if (tags.contains("@API")) {
            scenario.log("API test run completed");

        }
    }
}
