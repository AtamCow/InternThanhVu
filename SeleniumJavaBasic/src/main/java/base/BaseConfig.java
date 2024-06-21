package base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseConfig {
    private static Properties properties;

    static {
        setProperties();
    }

    private static void setProperties() {
        properties = new Properties();
        try (InputStream input = BaseConfig.class.getClassLoader().getResourceAsStream("configs.properties")) {
            if (input == null) {
                throw new IOException("config.properties file not found.");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties file.");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}