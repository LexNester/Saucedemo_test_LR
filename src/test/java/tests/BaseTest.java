package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        if (browser == null || browser.isEmpty()) {
            browser = System.getProperty("browser", "chrome");
        }
        
        driver = createDriver(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().window().maximize();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                try {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless");
                    return new ChromeDriver(chromeOptions);
                } catch (Exception e) {
                    System.err.println("Ошибка при запуске Chrome: " + e.getMessage());
                    throw new RuntimeException("Не удалось запустить Chrome", e);
                }
                
            case "firefox":
                try {
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                    firefoxOptions.setBinary("C:\\Program Files\\Firefox Developer Edition\\firefox.exe");
                    firefoxOptions.addPreference("profile.default_content_settings.popups", 0);
                    firefoxOptions.addPreference("profile.default_content_setting_values.notifications", 2);
                    firefoxOptions.addPreference("app.update.auto", false);
                    firefoxOptions.addPreference("app.update.enabled", false);
                    return new FirefoxDriver(firefoxOptions);
                } catch (Exception e) {
                    System.err.println("Ошибка при запуске Firefox: " + e.getMessage());
                    System.err.println("Firefox не установлен или недоступен. Переключаемся на Chrome.");
                    return createDriver("chrome");
                }
                
            case "edge":
                try {
                    String edgeDriverPath = "C:\\Users\\79291\\.cache\\selenium\\msedgedriver\\win64\\141.0.3537.99\\msedgedriver.exe";
                    System.setProperty("webdriver.edge.driver", edgeDriverPath);
                    return new EdgeDriver();
                } catch (Exception e) {
                    System.err.println("Ошибка при запуске Edge: " + e.getMessage());
                    System.err.println("Edge драйвер недоступен. Переключаемся на Chrome.");
                    return createDriver("chrome");
                }

            default:
                System.err.println("Неподдерживаемый браузер: " + browser + ". Используем Chrome по умолчанию.");
                return createDriver("chrome");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}