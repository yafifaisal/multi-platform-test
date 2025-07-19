package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        String platform = System.getProperty("platform", "mobile").toLowerCase(); // default: web
        String configPath;

        switch (platform) {
            case "mobile":
                configPath = "src/test/resources/mobile/config.properties";
                break;
            case "api":
                configPath = "src/test/resources/api/config.properties";
                break;
            case "web":
            default:
                configPath = "src/test/resources/web/config.properties";
                break;
        }

        try (FileInputStream fis = new FileInputStream(configPath)) {
            properties.load(fis);
            System.out.println("Loaded config for platform: " + platform);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file for platform: " + platform + " -> " + e.getMessage(),
                    e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
