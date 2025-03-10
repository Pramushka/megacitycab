package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DatabaseConfig {
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DRIVER;

    static {
        Properties properties = new Properties();
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Database properties file not found.");
            }
            properties.load(input);

            DRIVER = properties.getProperty("db.driver");
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.username");
            PASSWORD = properties.getProperty("db.password");

            // Load the MySQL driver
            Class.forName(DRIVER);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load database properties.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("✅ Connection Successful!");
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
        }
    }
}
