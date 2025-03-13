package dao;

import model.Driver;
import java.sql.*;

public class DriverDAO {
    private Connection connection;

    public DriverDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void addDriver(Driver driver) throws SQLException {
        String query = "INSERT INTO driver (user_id, license_number, status, nic, rating) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, driver.getUserId());
            stmt.setString(2, driver.getLicenseNumber());
            stmt.setString(3, driver.getStatus());
            stmt.setString(4, driver.getNic());
            stmt.setDouble(5, driver.getRating());
            stmt.executeUpdate();
        }
    }

    // READ
    public Driver getDriverById(int id) throws SQLException {
        String query = "SELECT * FROM driver WHERE driver_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Driver(
                        rs.getInt("driver_id"),
                        rs.getInt("user_id"),
                        rs.getString("license_number"),
                        rs.getString("status"),
                        rs.getString("nic"),
                        rs.getDouble("rating")
                );
            }
        }
        return null;
    }

    public boolean submitVerification(int driverId, String contact, String referral) throws SQLException {
        String query = "UPDATE driver SET additional_contact = ?, referral_code = ?, is_verified = 'PENDING' WHERE driver_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, contact);
            stmt.setString(2, referral);
            stmt.setInt(3, driverId);
            return stmt.executeUpdate() > 0;
        }
    }

}
