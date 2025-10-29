package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PurchaseTests extends BaseTest {

    @Test(description = "Покупка: добавить товар, оформить заказ и завершить")
    public void testCompletePurchaseFlow() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("standard_user", "secret_sauce");
        ProductsPage products = new ProductsPage(driver);
        Assert.assertTrue(products.isAt(), "Ожидаем страницу Products");
        products.addFirstProductToCart();
        Assert.assertEquals(products.getCartBadge(), "1", "В корзине должен быть 1 товар");
        products.openCart();
        CartPage cart = new CartPage(driver);
        Assert.assertTrue(cart.isAt(), "Ожидаем страницу Your Cart");
        cart.clickCheckout();
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillInfoAndContinue("Fgmslemv", "fbgdngvjb", "516542154");
        checkout.finishCheckout();
        Assert.assertTrue(checkout.isComplete(), "Ожидаем страницу с подтверждением заказа");
    }

    @Test(description = "Cart: добавить и удалить товар из корзины")
    public void testAddAndRemoveFromCart() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("standard_user", "secret_sauce");
        ProductsPage products = new ProductsPage(driver);
        products.addFirstProductToCart();
        Assert.assertEquals(products.getCartBadge(), "1", "В корзине должен быть 1 товар");
        products.openCart();
        CartPage cart = new CartPage(driver);
        Assert.assertTrue(cart.isAt(), "Ожидаем страницу Your Cart");
        cart.removeFirstItem();
    }

    @Test(description = "Checkout: валидация - пропущено поле First Name (негативный кейс)")
    public void testCheckoutValidationMissingFirstName() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("standard_user", "secret_sauce");
        ProductsPage products = new ProductsPage(driver);
        products.addFirstProductToCart();
        products.openCart();
        CartPage cart = new CartPage(driver);
        Assert.assertTrue(cart.isAt(), "Ожидаем страницу корзины");
        cart.clickCheckout();
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillInfoAndContinue("", "fbgdngvjb", "5484246147");
        String err = checkout.getErrorMessage();
        Assert.assertTrue(err.toLowerCase().contains("first") || err.toLowerCase().contains("is required"),
                "Ожидаем сообщение об ошибке для пропущенного имени");
    }

    @Test(description = "Products: сортировка Name (Z to A) — проверка порядка")
    public void testProductSortZToA() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("standard_user", "secret_sauce");
        ProductsPage products = new ProductsPage(driver);
        Assert.assertTrue(products.isAt(), "Ожидаем страницу Products");
        List<String> before = products.getAllProductNames();
        Assert.assertFalse(before.isEmpty(), "Список товаров не должен быть пустым");
        products.selectSortOption("Name (Z to A)");
        List<String> after = products.getAllProductNames();
        List<String> sorted = new ArrayList<>(after);
        Collections.sort(sorted, Comparator.reverseOrder());
        Assert.assertEquals(after, sorted, "Ожидаем, что после сортировки Name (Z to A) порядок будет убывающим");
    }
}
