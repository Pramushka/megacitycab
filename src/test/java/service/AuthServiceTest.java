package service;

import config.DatabaseConfig;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import dao.UserDAO;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        try {
            Connection connection = DatabaseConfig.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            authService = new AuthService();
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }

//    @Test
//    public void testRegisterCustomer() {
//        try (Connection connection = DatabaseConfig.getConnection()) {
//            AuthService authService = new AuthService();
//
//            User testUser = new User(0, "Test", "User", "testuser@example.com",
//                    "1234567890", "password123", "CUSTOMER");
//
//            int userId = authService.registerUser(testUser);
//            assertNotEquals(-1, userId, "User registration should return a valid user ID");
//
//            boolean customerCreated = authService.registerCustomer(userId);
//            assertTrue(customerCreated, "Customer should be successfully created in the database");
//
//            System.out.println("✅ Customer Registration Test Passed");
//        } catch (SQLException e) {
//            fail("Database connection failed: " + e.getMessage());
//        }
//    }

    @Test
    public void testLoginWithValidCredentials() {
        String testEmail = "testuser@example.com";
        String testPassword = "password123";
        String userType = "CUSTOMER";

        User user = authService.authenticate(testEmail, testPassword, userType);

        assertNotNull(user, "User should not be null");
        assertEquals(testEmail, user.getEmail(), "Email should match");
        assertEquals(userType, user.getUserType(), "User type should match");
        System.out.println("✅ Login Test Passed: User authenticated successfully.");
    }

    @Test
    public void testLoginWithInvalidPassword() {
        String testEmail = "testuser@example.com";
        String wrongPassword = "wrongpassword";
        String userType = "CUSTOMER";

        User user = authService.authenticate(testEmail, wrongPassword, userType);

        assertNull(user, "User should be null for invalid password");
        System.out.println("✅ Login Test Passed: Invalid password rejected.");
    }

    @Test
    public void testLoginWithNonExistentUser() {
        String nonExistentEmail = "nonexistent@example.com";
        String password = "password123";
        String userType = "CUSTOMER";

        User user = authService.authenticate(nonExistentEmail, password, userType);

        assertNull(user, "User should be null for non-existent account");
        System.out.println("✅ Login Test Passed: Non-existent user rejected.");
    }
}
