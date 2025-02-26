package qumu.pages;

import org.openqa.selenium.WebDriver;
import drivers.DriverManager;

public class BasePage {
    protected  WebDriver driver;

    public BasePage()
    {
        if (DriverManager.getDriver() != null) {
            this.driver = DriverManager.getDriver();
        }

    }

}
