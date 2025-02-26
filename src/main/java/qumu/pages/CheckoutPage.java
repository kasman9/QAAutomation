package qumu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CheckoutPage extends BasePage{




    private By cartBadge = By.className("shopping_cart_badge");
    private By cartLink = By.className("shopping_cart_link");
    private By checkoutButton = By.id("checkout");
    private By continueButton = By.id("continue");
    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By zipCodeField = By.id("postal-code");
    private By subtotalLabel = By.className("summary_subtotal_label");
    private By taxLabel = By.className("summary_tax_label");
    private By inventoryPrices = By.className("inventory_item_price");

    /**
     * Add items to the shopping cart.
     */
    public void addItemsToBasket(List<String> items) {
        for (String item : items) {
            driver.findElement(By.xpath("//button[@data-test='add-to-cart-" + item.toLowerCase().replace(" ", "-") + "']")).click();
        }
    }

    /**
     * Get the count of items in the shopping cart.
     */
    public int getCartItemCount() {
        return Integer.parseInt(driver.findElement(cartBadge).getText());
    }

    /**
     * Click the shopping cart icon.
     */
    public void clickShoppingCart() {
        driver.findElement(cartLink).click();
    }

    /**
     * Verify that each item in the cart has the expected quantity.
     */
    public boolean verifyQuantity(int expectedQty) {
        List<WebElement> qtyElements = driver.findElements(By.className("cart_quantity"));
        for (WebElement qty : qtyElements) {
            if (Integer.parseInt(qty.getText()) != expectedQty) {
                return false;
            }
        }
        return true;
    }

    /**
     * Remove specified items from the cart.
     */
    public void removeItemsFromCart(List<String> items) {
        for (String item : items) {
            driver.findElement(By.xpath("//div[text()='" + item + "']/following::button[text()='Remove']")).click();
        }
    }

    /**
     * Click the checkout button.
     */
    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }

    /**
     * Enter the first name during checkout.
     */
    public void enterFirstName(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    /**
     * Enter the last name during checkout.
     */
    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    /**
     * Enter the ZIP/Postal code during checkout.
     */
    public void enterPostalCode(String zipCode) {
        driver.findElement(zipCodeField).sendKeys(zipCode);
    }

    /**
     * Click the Continue button.
     */
    public void clickContinue() {
        driver.findElement(continueButton).click();
    }

    /**
     * Get the subtotal from the summary page.
     */
    public double getSubtotal() {
        return Double.parseDouble(driver.findElement(subtotalLabel).getText().replace("Item total: $", ""));
    }

    /**
     * Get the tax amount from the summary page.
     */
    public double getTax() {
        return Double.parseDouble(driver.findElement(taxLabel).getText().replace("Tax: $", ""));
    }

    /**
     * Verify if the item total is correct based on cart prices.
     */
    public boolean verifyItemTotal() {
        List<WebElement> prices = driver.findElements(inventoryPrices);
        double total = 0;
        for (WebElement price : prices) {
            total += Double.parseDouble(price.getText().replace("$", ""));
        }
        return getSubtotal() == total;
    }

    /**
     * Verify if the applied tax rate is correct.
     */
    public boolean verifyTaxRate(double taxRate) {
        double expectedTax = Math.round((getSubtotal() * (taxRate / 100)) * 100.0) / 100.0;
        return Math.abs(getTax() - expectedTax) < 0.01;
    }
}
