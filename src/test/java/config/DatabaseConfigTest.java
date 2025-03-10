package config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConfigTest {

    @Test
    public void testDatabaseConnection() {
        try (Connection connection = DatabaseConfig.getConnection()) {
            assertNotNull(connection, "Database connection should not be null");
            System.out.println("✅ Database Connection Successful");
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }
}
