package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By title = By.cssSelector("span.title");
    private final By addToCartButtonByProduct = By.cssSelector("button.btn_inventory");
    private final By shoppingCartBadge = By.cssSelector("a.shopping_cart_link .shopping_cart_badge");
    private final By cartLink = By.cssSelector("a.shopping_cart_link");
    private final By productNames = By.cssSelector("div.inventory_item_name");
    private final By productImages = By.cssSelector("div.inventory_item img");
    private final By sortSelect = By.cssSelector("select.product_sort_container");


    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isAt() {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(title, "Products"));
    }

    public void addFirstProductToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonByProduct)).click();
    }

    public String getCartBadge() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartBadge)).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }

    public List<String> getAllProductNames() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNames));
        return driver.findElements(productNames).stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }

    public int countVisibleProductImages() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productImages));
        List<WebElement> imgs = driver.findElements(productImages);
        int visible = 0;
        for (WebElement img : imgs) {
            String src = img.getAttribute("src");
            if (src != null && !src.trim().isEmpty()) {
                visible++;
            }
        }
        return visible;
    }

    public void selectSortOption(String optionVisibleText) {
        wait.until(ExpectedConditions.elementToBeClickable(sortSelect));
        WebElement selectEl = driver.findElement(sortSelect);
        Select s = new Select(selectEl);
        s.selectByVisibleText(optionVisibleText);
        wait.until(ExpectedConditions.visibilityOfElementLocated(productNames));
    }
}
