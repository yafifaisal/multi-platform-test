package utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvironmentWriter {

    public static void setAllureEnvironmentInformation() {
        Properties props = new Properties();

        props.setProperty("Browser", System.getProperty("browser", ConfigReader.getProperty("browser")));
        props.setProperty("Headless", System.getProperty("headless", ConfigReader.getProperty("headless")));
        props.setProperty("Environment", System.getProperty("environment", ConfigReader.getProperty("environment")));

        try {
            File directory = new File("target/allure-results");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            FileWriter writer = new FileWriter("target/allure-results/environment.properties");
            props.store(writer, "Allure Environment Properties");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to write Allure environment properties", e);
        }
    }
}
