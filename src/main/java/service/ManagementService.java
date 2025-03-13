package service;

import dao.BookingDAO;
import dao.DriverDAO;
import dao.VehicleDAO;
import model.Booking;
import model.Driver;
import model.Vehicle;
import java.sql.SQLException;
import java.util.List;
import config.DatabaseConfig;

public class ManagementService {
    private BookingDAO bookingDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;

    public ManagementService() throws SQLException {
        this.bookingDAO = new BookingDAO(DatabaseConfig.getConnection());
        this.driverDAO = new DriverDAO(DatabaseConfig.getConnection());
        this.vehicleDAO = new VehicleDAO(DatabaseConfig.getConnection());
    }

    // ✅ Fetch all bookings
    public List<Booking> getAllBookings() throws SQLException {
        return bookingDAO.getAllBookings();
    }

    // ✅ Assign driver to booking
    public boolean assignDriverToBooking(int bookingId, int driverId) throws SQLException {
        return bookingDAO.assignDriverToBooking(bookingId, driverId);
    }

    // ✅ Update booking status
    public boolean updateBookingStatus(int bookingId, String status) throws SQLException {
        return bookingDAO.updateBookingStatus(bookingId, status);
    }

    // ✅ Fetch available drivers
    public List<Driver> getAvailableDrivers() throws SQLException {
        return driverDAO.getAvailableDrivers();
    }

    // ✅ Fetch all vehicles
    public List<Vehicle> getAllVehicles() throws SQLException {
        return vehicleDAO.getAllVehicles();
    }
}
