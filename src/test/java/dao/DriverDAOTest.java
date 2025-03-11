package dao;

import model.Driver;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DriverDAOTest {
    private static Connection connection;
    private static DriverDAO driverDAO;

    @BeforeAll
    static void setup() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/megacitycab", "root", "");
        driverDAO = new DriverDAO(connection);
    }

    @Test
    void testAddDriver() throws SQLException {
        Driver driver = new Driver(0, 2, "DL-12345", "982345678V", "AVAILABLE", 4.5);
        driverDAO.addDriver(driver);

        Driver retrieved = driverDAO.getDriverById(2);
        assertNotNull(retrieved);
        assertEquals("DL-12345", retrieved.getLicenseNumber());
    }

    @Test
    void testGetDriverById() throws SQLException {
        Driver driver = driverDAO.getDriverById(2);
        assertNotNull(driver);
        assertEquals(2, driver.getUserId());
    }

    @AfterAll
    static void tearDown() throws SQLException {
        connection.close();
    }
}
