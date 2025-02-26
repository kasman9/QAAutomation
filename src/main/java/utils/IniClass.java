package utils;

import qumu.pages.CheckoutPage;
import qumu.pages.HomePage;
import qumu.pages.LoginPage;

public class IniClass {
    private HomePage homePage;
    private CheckoutPage checkoutPage;
    private LoginPage loginPage;


    public IniClass() {
        homePage = new HomePage();
        checkoutPage = new CheckoutPage();
        loginPage = new LoginPage();
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public CheckoutPage getCheckoutPage() {
        return checkoutPage;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }
}
