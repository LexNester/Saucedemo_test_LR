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
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    chromeOptions.addArguments("--disable-infobars");
                    chromeOptions.addArguments("--disable-extensions");
                    chromeOptions.addArguments("--disable-plugins");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--disable-features=VizDisplayCompositor");
                    chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                    chromeOptions.addArguments("--disable-client-side-phishing-detection");
                    chromeOptions.addArguments("--disable-sync");
                    chromeOptions.addArguments("--disable-background-timer-throttling");
                    chromeOptions.addArguments("--disable-backgrounding-occluded-windows");
                    chromeOptions.addArguments("--disable-renderer-backgrounding");
                    chromeOptions.addArguments("--disable-features=TranslateUI");
                    chromeOptions.addArguments("--disable-ipc-flooding-protection");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-extensions-file-access-check");
                    chromeOptions.addArguments("--disable-extensions-http-throttling");
                    chromeOptions.addArguments("--disable-password-manager");
                    chromeOptions.addArguments("--disable-save-password-bubble");
                    chromeOptions.addArguments("--disable-single-click-autofill");
                    chromeOptions.addArguments("--disable-features=PasswordLeakDetection");
                    chromeOptions.addArguments("--disable-features=PasswordImport");
                    chromeOptions.addArguments("--disable-features=PasswordManagerOnboarding");
                    chromeOptions.addArguments("--disable-features=PasswordCheck");
                    chromeOptions.addArguments("--disable-features=PasswordGeneration");
                    chromeOptions.addArguments("--disable-features=PasswordManager");
                    chromeOptions.addArguments("--disable-features=SafeBrowsingEnhancedProtection");
                    chromeOptions.addArguments("--disable-features=SafeBrowsing");
                    chromeOptions.addArguments("--disable-features=PasswordLeakDetection");
                    chromeOptions.addArguments("--disable-features=AutofillServerCommunication");
                    chromeOptions.addArguments("--disable-features=AutofillShowTypePredictions");
                    chromeOptions.addArguments("--disable-features=AutofillShowManualFallbackForVirtualCard");
                    chromeOptions.addArguments("--user-data-dir=" + System.getProperty("java.io.tmpdir") + "/chrome-test-profile");
                    chromeOptions.addArguments("--profile-directory=TestProfile");
                    chromeOptions.addArguments("--disable-features=Translate");
                    chromeOptions.addArguments("--disable-features=MediaRouter");
                    chromeOptions.addArguments("--disable-features=OptimizationHints");
                    chromeOptions.addArguments("--disable-features=AutofillEnableAccountWalletStorage");
                    chromeOptions.addArguments("--disable-features=AutofillEnableVirtualCardMetadata");
                    chromeOptions.addArguments("--disable-features=AutofillEnableVirtualCards");
                    // Отключаем все уведомления и предупреждения
                    chromeOptions.addArguments("--disable-default-apps");
                    chromeOptions.addArguments("--disable-component-extensions-with-background-pages");
                    chromeOptions.addArguments("--disable-background-networking");
                    chromeOptions.addArguments("--disable-background-timer-throttling");
                    chromeOptions.addArguments("--disable-renderer-backgrounding");
                    chromeOptions.addArguments("--disable-backgrounding-occluded-windows");
                    chromeOptions.addArguments("--disable-logging");
                    chromeOptions.addArguments("--disable-gpu-logging");
                    chromeOptions.addArguments("--silent");
                    chromeOptions.addArguments("--log-level=3");
                    chromeOptions.addArguments("--disable-features=VizDisplayCompositor,VizHitTestSurfaceLayer");
                    chromeOptions.addArguments("--disable-features=TranslateUI,BlinkGenPropertyTrees");
                    chromeOptions.addArguments("--disable-features=MediaRouter,OptimizationHints");
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation", "enable-logging"});
                    chromeOptions.setExperimentalOption("useAutomationExtension", false);
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