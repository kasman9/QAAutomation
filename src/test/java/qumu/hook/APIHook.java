package qumu.hook;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.util.ArrayList;
import java.util.List;

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
