package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utilities.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverManager {

    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        if (driver.get() == null) {
            String browser = System.getProperty("browser", ConfigReader.getProperty("browser")).toLowerCase();
            boolean headless = Boolean
                    .parseBoolean(System.getProperty("headless", ConfigReader.getProperty("headless")));

            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) {
                        chromeOptions.addArguments("--headless=new");
                        chromeOptions.addArguments("--window-size=1920,1080");
                    }
                    driver.set(new ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) {
                        firefoxOptions.addArguments("-headless");
                    }
                    driver.set(new FirefoxDriver(firefoxOptions));
                    break;

                default:
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }

            logger.info("Launching browser: {}, headless: {}", browser, headless);
            getDriver().manage().window().maximize();
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            logger.info("Quitting browser...");
            driver.get().quit();
            driver.remove();
        }
    }
}
