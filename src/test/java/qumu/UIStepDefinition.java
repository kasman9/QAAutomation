package qumu;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;

public class UIStepDefinition {


    private Scenario scenario;
    private WebDriver driver;
    private IniClass ini;
    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;

        // ✅ Initialize pages
         ini = new IniClass();

    }

    @Given("I am on the home page")
    public void i_am_on_the_home_page() {
       ini.getHomePage().openHomePage();
    }

    @Given("I login in with the following details")
    public void i_login_with_the_following_details(DataTable dataTable) {
        List<List<String>> credentials = dataTable.asLists();
        String username = credentials.get(1).get(0);
        String password = credentials.get(1).get(1);
        ini.getLoginPage().login(username, password);
        scenario.log("User Name: " + username);
    }

    @Given("I add the following items to the basket")
    public void i_add_the_following_items_to_the_basket(DataTable dataTable) {
        ini.getCheckoutPage().addItemsToBasket(dataTable.asList());
    }

    @Given("I should see {int} items added to the shopping cart")
    public void i_should_see_items_added_to_the_shopping_cart(int expectedCount) {
        Assert.assertEquals(ini.getCheckoutPage().getCartItemCount(), expectedCount, "Cart count mismatch");
    }

    @Given("I click on the shopping cart")
    public void i_click_on_the_shopping_cart() {
        ini.getCheckoutPage().clickShoppingCart();
    }

    @Given("I verify that the QTY count for each item should be {int}")
    public void i_verify_that_the_qty_count_for_each_item_should_be(int expectedQty) {
        Assert.assertTrue(ini.getCheckoutPage().verifyQuantity(expectedQty), "Quantity mismatch");
    }

    @Given("I remove the following item:")
    public void i_remove_the_following_item(DataTable dataTable) {
        ini.getCheckoutPage().removeItemsFromCart(dataTable.asList());
    }

    @Given("I click on the CHECKOUT button")
    public void i_click_on_the_checkout_button() {
        ini.getCheckoutPage().clickCheckout();
    }

    @Given("I type {string} for First Name")
    public void i_type_for_first_name(String firstName) {
        ini.getCheckoutPage().enterFirstName(firstName);
    }

    @Given("I type {string} for Last Name")
    public void i_type_for_last_name(String lastName) {
        ini.getCheckoutPage().enterLastName(lastName);
    }

    @Given("I type {string} for ZIP\\/Postal Code")
    public void i_type_for_zip_postal_code(String zip) {
        ini.getCheckoutPage().enterPostalCode(zip);
    }

    @When("I click on the CONTINUE button")
    public void i_click_on_the_continue_button() {
        ini.getCheckoutPage().clickContinue();
    }

    @Then("Item total will be equal to the total of items on the list")
    public void item_total_will_be_equal_to_the_total_of_items_on_the_list() {
        Assert.assertTrue(ini.getCheckoutPage().verifyItemTotal(), "Item total mismatch");
    }

    @Then("a Tax rate of {double} % is applied to the total")
    public void a_tax_rate_is_applied_to_the_total(double taxRate) {
        Assert.assertTrue(ini.getCheckoutPage().verifyTaxRate(taxRate), "Tax mismatch");
    }

}
