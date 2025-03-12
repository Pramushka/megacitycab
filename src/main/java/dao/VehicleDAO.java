package dao;

import model.Vehicle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {
    private Connection connection;

    public VehicleDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void addVehicle(Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO vehicle (vehicle_number, model, type, capacity, driver_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicle.getVehicleNumber()); // Fix column name
            stmt.setString(2, vehicle.getModel());
            stmt.setString(3, vehicle.getType());
            stmt.setInt(4, vehicle.getCapacity());
            stmt.setInt(5, vehicle.getDriverId());
            stmt.executeUpdate();
        }
    }

    // READ (Fetch a vehicle by ID)
    public Vehicle getVehicleById(int id) throws SQLException {
        String query = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getInt("driver_id"),
                        rs.getString("vehicle_number"), // Fix column name
                        rs.getString("model"),
                        rs.getString("type"),
                        rs.getInt("capacity")
                );
            }
        }
        return null;
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicle";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getInt("driver_id"),
                        rs.getString("license_plate"),
                        rs.getString("model"),
                        rs.getString("type"),
                        rs.getInt("capacity")
                ));
            }
        }
        return vehicles;
    }

    // UPDATE
    public void updateVehicle(Vehicle vehicle) throws SQLException {
        String query = "UPDATE vehicle SET vehicle_number=?, model=?, type=?, capacity=?, driver_id=? WHERE vehicle_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicle.getVehicleNumber()); // Fix column name
            stmt.setString(2, vehicle.getModel());
            stmt.setString(3, vehicle.getType());
            stmt.setInt(4, vehicle.getCapacity());
            stmt.setInt(5, vehicle.getDriverId());
            stmt.setInt(6, vehicle.getVehicleId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteVehicle(int vehicleId) throws SQLException {
        String query = "DELETE FROM vehicle WHERE vehicle_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, vehicleId);
            stmt.executeUpdate();
        }
    }
}