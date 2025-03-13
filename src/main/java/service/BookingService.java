package service;

import config.DatabaseConfig;
import dao.BookingDAO;
import model.Booking;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookingService {
    private final BookingDAO bookingDAO;

    // ✅ Default Constructor (for controllers)
    public BookingService() throws SQLException {
        Connection connection = DatabaseConfig.getConnection();
        this.bookingDAO = new BookingDAO(connection);
    }

    // ✅ Constructor for Dependency Injection (for tests)
    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    // ✅ Create Booking and return booking ID
    public int createBooking(Booking booking) throws SQLException {
        return bookingDAO.addBooking(booking);
    }

    // ✅ Get Booking by ID
    public Booking getBookingById(int bookingId) throws SQLException {
        return bookingDAO.getBookingById(bookingId);
    }

    // ✅ Get Bookings for a specific customer
    public List<Booking> getBookingsByCustomerId(int customerId) throws SQLException {
        return bookingDAO.getBookingsByCustomerId(customerId);
    }

    // ✅ Cancel a booking
    public boolean cancelBooking(int bookingId) throws SQLException {
        return bookingDAO.cancelBooking(bookingId);
    }

    // ✅ Assign a driver to a booking
    public boolean assignDriverToBooking(int bookingId, int driverId) throws SQLException {
        return bookingDAO.assignDriverToBooking(bookingId, driverId);
    }

    // ✅ Update Booking Status
    public boolean updateBookingStatus(int bookingId, String status) throws SQLException {
        return bookingDAO.updateBookingStatus(bookingId, status);
    }
}
