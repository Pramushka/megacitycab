package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConfig {
    private static String url;

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            url = prop.getProperty("db.url");

            // Load the JDBC driver
            Class.forName(prop.getProperty("db.driver"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database properties.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}