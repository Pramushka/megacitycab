package service;

import dao.BookingDAO;
import dao.DriverDAO;
import dao.VehicleDAO;
import model.Booking;
import model.Driver;
import model.Vehicle;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import config.DatabaseConfig;

public class ManagementService {
    private BookingDAO bookingDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private Connection connection;

    public ManagementService() throws SQLException {
        this.connection = DatabaseConfig.getConnection();  // ✅ Store connection
        this.bookingDAO = new BookingDAO(connection);
        this.driverDAO = new DriverDAO(connection);
        this.vehicleDAO = new VehicleDAO(connection);
    }

    //  Fetch all vehicles (USE DAO, NO RAW SQL)
    public  List<Vehicle> getAllVehicles() throws SQLException {
        return vehicleDAO.getAllVehicles();
    }

    //  Fetch all bookings
    public List<Booking> getAllBookings() throws SQLException {
        return bookingDAO.getAllBookings();
    }

    //  Assign driver to booking
    public boolean assignDriverToBooking(int bookingId, int driverId) throws SQLException {
        return bookingDAO.assignDriverToBooking(bookingId, driverId);
    }

    //  Update booking status
    public boolean updateBookingStatus(int bookingId, String status) throws SQLException {
        return bookingDAO.updateBookingStatus(bookingId, status);
    }

    //  Fetch available drivers
    public List<Driver> getAvailableDrivers() throws SQLException {
        return driverDAO.getAvailableDrivers();
    }
}
