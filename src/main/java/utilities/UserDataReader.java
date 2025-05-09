package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class UserDataReader {

    private static final String CSV_PATH = "src/test/resources/test-data/users.csv";

    public static Map<String, String> getCredentialsByAlias(String alias) {
        Map<String, String> credentials = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // skip header
                }
                String[] values = line.split(",");
                if (values[0].equalsIgnoreCase(alias)) {
                    credentials.put("username", values[1].trim());
                    credentials.put("password", values[2].trim());
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read user data from CSV: " + e.getMessage());
        }

        if (credentials.isEmpty()) {
            throw new RuntimeException("Alias not found in CSV: " + alias);
        }

        return credentials;
    }
}
