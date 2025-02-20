package qumu;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {


    public void openHomePage() {
    driver.get(LoadProp.getproperty("url"));
    }
}
