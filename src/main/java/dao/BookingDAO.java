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

    // ✅ Add Booking and Return Booking ID
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
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }

    // ✅ Fetch all bookings of a specific customer
    public List<Booking> getBookingsByCustomerId(int customerId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM booking WHERE customer_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(
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
        return bookings;
    }

    // ✅ Fetch all bookings assigned to a specific driver
    public List<Booking> getBookingsByDriverId(int driverId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM booking WHERE driver_id = ? AND (status = 'ACCEPTED' OR status = 'IN_PROGRESS')";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(
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
        return bookings;
    }

    // ✅ Fetch a single booking by ID
    public Booking getBookingById(int bookingId) throws SQLException {
        String query = "SELECT * FROM booking WHERE booking_id = ?";

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
                        rs.getString("status")
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

    // ✅ Assign a driver to a booking
    public boolean assignDriverToBooking(int bookingId, int driverId) throws SQLException {
        String query = "UPDATE booking SET driver_id = ?, status = 'ACCEPTED' WHERE booking_id = ? AND status = 'REQUESTED'";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, driverId);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ✅ Update Booking Status (STARTED, COMPLETED, CANCELLED)
    public boolean updateBookingStatus(int bookingId, String status) throws SQLException {
        String query = "UPDATE booking SET status = ? WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ✅ Fetch ride history for a driver (Completed Rides)
    public List<Booking> getRideHistory(int driverId) throws SQLException {
        List<Booking> rideHistory = new ArrayList<>();
        String query = "SELECT * FROM booking WHERE driver_id = ? AND status = 'COMPLETED'";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rideHistory.add(new Booking(
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
        return rideHistory;
    }

    // ✅ Mark Payment as Completed when ride is done
    public boolean completePayment(int bookingId) throws SQLException {
        String query = "UPDATE payment SET status = 'COMPLETED' WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        }
    }

}
