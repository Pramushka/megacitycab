package service;

import dao.BookingDAO;
import dao.DriverDAO;
import model.Booking;
import model.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DriverServiceTest {
    private DriverService driverService;
    private DriverDAO driverDAO;
    private BookingDAO bookingDAO;

    @BeforeEach
    void setUp() throws SQLException {
        driverDAO = mock(DriverDAO.class);
        bookingDAO = mock(BookingDAO.class);

        // Inject mock DAO into the DriverService constructor
        driverService = new DriverService(driverDAO, bookingDAO);
    }

    @Test
    void testGetAssignedBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking(1, 2, 1, 3, "Pickup", "Dropoff", 20.0, "ASSIGNED"));

        // Mock the DAO call
        when(bookingDAO.getBookingsByDriverId(1)).thenReturn(bookings);

        // Call the method
        List<Booking> result = driverService.getAssignedBookings(1);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ASSIGNED", result.get(0).getStatus());

        // Verify that the DAO method was actually called
        verify(bookingDAO, times(1)).getBookingsByDriverId(1);
    }

}
