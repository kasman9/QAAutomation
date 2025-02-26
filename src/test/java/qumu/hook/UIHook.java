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
import utils.BrowserSetup;
import utils.LoadProp;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UIHook {

    private BrowserSetup browsersetup;
    private static final int WAIT_SEC = 20;
    private String lastStepName = "";
    WebDriver driver;
    List<String> tags= null;



    public void clearOldScreenshots() {
        try {
            File folder = new File(LoadProp.getproperty("ScreenshotLocation"));
            if (folder.exists()) {
                FileUtils.cleanDirectory(folder);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before(order = 1)
    public void initializeTest(Scenario scenario) {
         tags = new ArrayList<>(scenario.getSourceTagNames());
        // Start browser only for UI tests
        if (tags.contains("@UI")) {
            clearOldScreenshots();
            driver = new BrowserSetup().selectBrowser();
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(WAIT_SEC, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(WAIT_SEC, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(WAIT_SEC, TimeUnit.SECONDS);
        }
    }



    @AfterStep
    public void takeScreenshotAfterStep(Scenario scenario) {
        if (tags.contains("@UI")) {
            String currentStep = scenario.getName();


            if (currentStep.equals(lastStepName)) {
                return; // Skip taking duplicate screenshots
            }
            lastStepName = currentStep;

            if (driver instanceof TakesScreenshot) {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                String screenShotFilename = scenario.getName().replace(" ", "")
                        + "_Step_"
                        + new Timestamp(new Date().getTime()).toString().replaceAll("[^a-zA-Z0-9]", "")
                        + "_" + LoadProp.getproperty("Browser") + ".jpg";

                try {
                    FileUtils.copyFile(scrFile, new File(LoadProp.getproperty("ScreenshotLocation") + screenShotFilename));
                    System.out.println("📸 Screenshot Taken: " + screenShotFilename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @After()
    public void screenshot(Scenario scenario) {
        if (tags.contains("@UI")) {
            String screenShotFilename = scenario.getName().replace(" ", "")
                    + new Timestamp(new Date().getTime()).toString().replaceAll("[^a-zA-Z0-9]", "")
                    + "_" + LoadProp.getproperty("Browser") + ".jpg";

            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(scrFile, new File(LoadProp.getproperty("ScreenshotLocation") + screenShotFilename));
            } catch (IOException e) {
                e.printStackTrace();
            }

            driver.close();

            try {
                driver.quit();
            } catch (NoSuchSessionException ex) {
                // Handle exception gracefully
            }
        }
    }
}
