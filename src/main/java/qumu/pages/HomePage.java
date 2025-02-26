package qumu.pages;

import utils.LoadProp;

public class HomePage extends BasePage {


    public void openHomePage() {
    driver.get(LoadProp.getproperty("url"));
    }
}
