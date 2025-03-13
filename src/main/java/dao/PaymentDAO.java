package dao;

import model.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private Connection connection;

    public PaymentDAO(Connection connection) {
        this.connection = connection;
    }

    // ✅ Add Payment
    public int addPayment(Payment payment) throws SQLException {
        String query = "INSERT INTO payment (booking_id, customer_id, amount, payment_method, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, payment.getBookingId());
            stmt.setInt(2, payment.getCustomerId());
            stmt.setDouble(3, payment.getAmount());
            stmt.setString(4, payment.getPaymentMethod());
            stmt.setString(5, payment.getStatus());
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

    // ✅ Get Payment by Booking ID
    public Payment getPaymentByBookingId(int bookingId) throws SQLException {
        String query = "SELECT * FROM payment WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Payment(
                        rs.getInt("payment_id"),
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getDouble("amount"),
                        rs.getString("payment_method"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                );
            }
        }
        return null;
    }

    // ✅ Get Earnings for a Driver
    public double getDriverEarnings(int driverId) throws SQLException {
        String query = "SELECT SUM(p.amount) AS total_earnings FROM payment p " +
                "JOIN booking b ON p.booking_id = b.booking_id " +
                "WHERE b.driver_id = ? AND p.status = 'COMPLETED'";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_earnings");
            }
        }
        return 0.0;
    }

}
