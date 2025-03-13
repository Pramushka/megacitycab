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

    // BookingDAO.java
    public int addBooking(Booking booking) throws SQLException {
        String query = "INSERT INTO booking (customer_id, driver_id, vehicle_id, pickup_location, dropoff_location, fare, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getDriverId());
            stmt.setInt(3, booking.getVehicleId());
            stmt.setString(4, booking.getPickupLocation());
            stmt.setString(5, booking.getDropoffLocation());
            stmt.setDouble(6, booking.getFare());
            stmt.setString(7, booking.getStatus());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);  // Return generated booking ID
                }
            }
        }
        return -1;  // Return -1 if failed
    }


    // ✅ Fetch all bookings of a specific customer (Including Driver & Vehicle Details)
    public List<Booking> getBookingsByCustomerId(int customerId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT b.*, d.first_name AS driver_name, d.license_number, v.model AS vehicle_model, v.vehicle_number " +
                "FROM booking b " +
                "LEFT JOIN driver d ON b.driver_id = d.driver_id " +
                "LEFT JOIN vehicle v ON b.vehicle_id = v.vehicle_id " +
                "WHERE b.customer_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("driver_id"),
                        rs.getInt("vehicle_id"),
                        rs.getString("pickup_location"),
                        rs.getString("dropoff_location"),
                        rs.getDouble("fare"),
                        rs.getString("status"),
                        rs.getString("driver_name"),
                        rs.getString("license_number"),
                        rs.getString("vehicle_model"),
                        rs.getString("vehicle_number")
                );
                bookings.add(booking);
            }
        }
        return bookings;
    }

    // ✅ Fetch a single booking by ID (Including Driver & Vehicle Details)
    public Booking getBookingById(int bookingId) throws SQLException {
        String query = "SELECT b.*, d.first_name AS driver_name, d.license_number, v.model AS vehicle_model, v.vehicle_number " +
                "FROM booking b " +
                "LEFT JOIN driver d ON b.driver_id = d.driver_id " +
                "LEFT JOIN vehicle v ON b.vehicle_id = v.vehicle_id " +
                "WHERE b.booking_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("driver_id"),
                        rs.getInt("vehicle_id"),
                        rs.getString("pickup_location"),
                        rs.getString("dropoff_location"),
                        rs.getDouble("fare"),
                        rs.getString("status"),
                        rs.getString("driver_name"),
                        rs.getString("license_number"),
                        rs.getString("vehicle_model"),
                        rs.getString("vehicle_number")
                );
            }
        }
        return null;
    }

    // ✅ Cancel Booking (Updates status to CANCELLED)
    public boolean cancelBooking(int bookingId) throws SQLException {
        String query = "UPDATE booking SET status = 'CANCELLED' WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ✅ Fetch available bookings (for drivers to accept)
    public List<Booking> getAvailableBookings() throws SQLException {
        List<Booking> availableBookings = new ArrayList<>();
        String query = "SELECT * FROM booking WHERE status = 'REQUESTED'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                availableBookings.add(new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("driver_id"),
                        rs.getInt("vehicle_id"),
                        rs.getString("pickup_location"),
                        rs.getString("dropoff_location"),
                        rs.getDouble("fare"),
                        rs.getString("status")
                ));
            }
        }
        return availableBookings;
    }

    // ✅ Assign a driver to a booking
    public boolean assignDriverToBooking(int bookingId, int driverId) throws SQLException {
        String query = "UPDATE booking SET driver_id = ?, status = 'ACCEPTED' WHERE booking_id = ? AND status = 'REQUESTED'";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, driverId);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ✅ Update Booking Status
    public boolean updateBookingStatus(int bookingId, String status) throws SQLException {
        String query = "UPDATE booking SET status = ? WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
        }
    }
}
