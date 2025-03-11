package dao;

import model.Vehicle;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleDAOTest {
    private static Connection connection;
    private static VehicleDAO vehicleDAO;

    @BeforeAll
    static void setup() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/megacitycab", "root", "");
        vehicleDAO = new VehicleDAO(connection);
    }

    @Test
    void testAddVehicle() throws SQLException {
        Vehicle vehicle = new Vehicle(0, 1, "ABC-1234", "Toyota Corolla", "SEDAN", 4);
        vehicleDAO.addVehicle(vehicle);

        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
        assertTrue(vehicles.stream().anyMatch(v -> v.getVehicleNumber().equals("ABC-1234")));
    }

    @Test
    void testGetVehicleById() throws SQLException {
        Vehicle vehicle = vehicleDAO.getVehicleById(1);
        assertNotNull(vehicle);
        assertEquals(1, vehicle.getVehicleId());
    }

    @Test
    void testUpdateVehicle() throws SQLException {
        Vehicle vehicle = new Vehicle(1, 1, "XYZ-9999", "Honda Civic", "SEDAN", 4);
        vehicleDAO.updateVehicle(vehicle);

        Vehicle updatedVehicle = vehicleDAO.getVehicleById(1);
        assertEquals("XYZ-9999", updatedVehicle.getVehicleNumber());
    }

    @Test
    void testDeleteVehicle() throws SQLException {
        vehicleDAO.deleteVehicle(1);
        assertNull(vehicleDAO.getVehicleById(1));
    }

    @AfterAll
    static void tearDown() throws SQLException {
        connection.close();
    }
}
