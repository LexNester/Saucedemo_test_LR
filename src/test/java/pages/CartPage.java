package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartTitle = By.cssSelector("span.title");
    private final By checkoutButton = By.id("checkout");
    private final By removeButton = By.cssSelector("button.cart_button");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isAt() {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(cartTitle, "Your Cart"));
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }

    public void removeFirstItem() {
        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
    }
}