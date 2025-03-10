package config;

import config.DatabaseConfig;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseConfigTest {

    @Test
    void testDatabaseConnection() {
        try (Connection connection = DatabaseConfig.getConnection()) {
            assertNotNull(connection, "Database connection should not be null.");
            System.out.println(" Database connection successful!");
        } catch (SQLException e) {
            fail(" Database connection failed: " + e.getMessage());
        }
    }
}
