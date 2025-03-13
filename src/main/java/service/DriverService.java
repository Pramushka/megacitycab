package service;

import config.DatabaseConfig;
import dao.BookingDAO;
import dao.DriverDAO;
import model.Booking;

import java.sql.SQLException;
import java.util.List;

public class DriverService {
    private final DriverDAO driverDAO;
    private final BookingDAO bookingDAO;

    // Constructor Injection
    public DriverService(DriverDAO driverDAO, BookingDAO bookingDAO) {
        this.driverDAO = driverDAO;
        this.bookingDAO = bookingDAO;
    }

    public DriverService() throws SQLException {
        this.driverDAO = new DriverDAO(DatabaseConfig.getConnection());
        this.bookingDAO = new BookingDAO(DatabaseConfig.getConnection());
    }

    // ✅ Get assigned bookings for a driver
    public List<Booking> getAssignedBookings(int driverId) throws SQLException {
        return bookingDAO.getBookingsByDriverId(driverId);
    }

    // ✅ Fetch ride history for a driver
    public List<Booking> getRideHistory(int driverId) throws SQLException {
        return bookingDAO.getRideHistory(driverId);
    }

    // ✅ Accept a ride
    public boolean acceptRide(int driverId, int bookingId) throws SQLException {
        return bookingDAO.assignDriverToBooking(bookingId, driverId);
    }

    // ✅ Update Ride Status (Started, Completed, Cancelled)
    public boolean updateRideStatus(int bookingId, String status) throws SQLException {
        return bookingDAO.updateBookingStatus(bookingId, status);
    }

    // ✅ Mark Ride as Completed
    public boolean completeRide(int bookingId) throws SQLException {
        boolean bookingUpdated = bookingDAO.updateBookingStatus(bookingId, "COMPLETED");
        boolean paymentUpdated = bookingDAO.completePayment(bookingId);
        return bookingUpdated && paymentUpdated;
    }


}
