package qumu;

import org.kohsuke.rngom.parse.host.Base;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected  WebDriver driver;  // ✅ WebDriver instance

    public BasePage()
    {
        this.driver=DriverManager.getDriver();

    }

}
