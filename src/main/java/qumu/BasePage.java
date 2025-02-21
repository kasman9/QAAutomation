package qumu;

import org.kohsuke.rngom.parse.host.Base;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected  WebDriver driver;

    public BasePage()
    {
        if (DriverManager.getDriver() != null) {
            this.driver = DriverManager.getDriver();
        }

    }

}
