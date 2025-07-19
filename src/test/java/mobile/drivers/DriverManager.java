package mobile.drivers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import utilities.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverManager {

    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    @SuppressWarnings("deprecation")
    public static void initDriver() {
        if (driver.get() == null) {
            String platform = ConfigReader.getProperty("platformName").toLowerCase();
            String appiumServer = ConfigReader.getProperty("appiumServer");

            try {
                if (platform.equals("android")) {
                    UiAutomator2Options options = new UiAutomator2Options();
                    options.setPlatformName("Android");
                    options.setDeviceName(ConfigReader.getProperty("deviceName"));
                    options.setApp(System.getProperty("user.dir") + "/src/test/resources/mobile/app/latest.apk");
                    options.setNoReset(false); // false = app will be reinstalled, true = keep state
                    options.setFullReset(false);
                    options.setUdid(ConfigReader.getProperty("udid"));
                    options.setAutomationName(ConfigReader.getProperty("automationName"));

                    driver.set(new AppiumDriver(new URL(appiumServer), options));

                } else if (platform.equals("ios")) {
                    XCUITestOptions options = new XCUITestOptions();
                    options.setPlatformName("iOS");
                    options.setDeviceName(ConfigReader.getProperty("deviceName"));
                    options.setApp("src/test/resources/mobile/app/latest.apk");
                    options.setUdid(ConfigReader.getProperty("udid"));
                    options.setAutomationName(ConfigReader.getProperty("automationName"));

                    driver.set(new AppiumDriver(new URL(appiumServer), options));
                } else {
                    throw new IllegalArgumentException("Unsupported platform: " + platform);
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Appium server URL: " + appiumServer, e);
            }
        }
    }

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

}
