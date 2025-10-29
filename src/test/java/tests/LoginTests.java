package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

public class LoginTests extends BaseTest {

    @Test(description = "Позитивный сценарий: успешный вход стандартного пользователя")
    public void testSuccessfulLogin() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("standard_user", "secret_sauce");
        ProductsPage products = new ProductsPage(driver);
        Assert.assertTrue(products.isAt(), "Ожидаем страницу Products после логина");
    }

    @Test(description = "Негативный сценарий: неверные креды")
    public void testInvalidLogin() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("invalid_user", "invalid_pass");
        String error = login.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("username and password do not match" ) ||
                error.toLowerCase().contains("username is required") ||
                error.toLowerCase().contains("epic sadface"), "Ожидаем сообщение об ошибке авторизации");
    }

    @Test(description = "Негативный сценарий: locked_out_user")
    public void testLockedOutUser() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("locked_out_user", "secret_sauce");
        String error = login.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("locked out") || error.toLowerCase().contains("sorry"),
                "Ожидаем сообщение о заблокированном пользователе");
    }

    @Test(description = "Performance glitch user: страница товаров загружается (увеличенный таймаут)")
    public void testPerformanceGlitchUserLoadsProducts() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("performance_glitch_user", "secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        Assert.assertTrue(products.isAt(), "Ожидаем, что Products page загрузится для performance_glitch_user");
    }

    @Test(description = "Problem user: проверка наличия ссылок/изображений у товаров (проверка наличия src у img)")
    public void testProblemUserProductImagesVisible() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("problem_user", "secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        Assert.assertTrue(products.isAt(), "Ожидаем страницу Products для problem_user");

        int images = products.countVisibleProductImages();
        Assert.assertTrue(images > 0, "Ожидаем, что у товаров есть src у изображений (images count > 0)");
    }
}
