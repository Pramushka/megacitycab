package dao;

import model.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private Connection connection;

    public BookingDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void addBooking(Booking booking) throws SQLException {
        String query = "INSERT INTO Booking (customerId, driverId, vehicleId, pickupLocation, dropoffLocation, fare, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getDriverId());
            stmt.setInt(3, booking.getVehicleId());
            stmt.setString(4, booking.getPickupLocation());
            stmt.setString(5, booking.getDropoffLocation());
            stmt.setDouble(6, booking.getFare());
            stmt.setString(7, booking.getStatus());
            stmt.executeUpdate();
        }
    }

    // READ
    public Booking getBookingById(int id) throws SQLException {
        String query = "SELECT * FROM Booking WHERE bookingId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Booking(
                        rs.getInt("bookingId"),
                        rs.getInt("customerId"),
                        rs.getInt("driverId"),
                        rs.getInt("vehicleId"),
                        rs.getString("pickupLocation"),
                        rs.getString("dropoffLocation"),
                        rs.getDouble("fare"),
                        rs.getString("status")
                );
            }
        }
        return null;
    }
}
