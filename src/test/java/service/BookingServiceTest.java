package service;

import dao.BookingDAO;
import model.Booking;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookingServiceTest {
    private BookingService bookingService;
    private BookingDAO bookingDAO;
    private int testCustomerId = 1;
    private int testDriverId = 2;
    private int testVehicleId = 3;

    @BeforeAll
    void setup() {
        bookingDAO = mock(BookingDAO.class);  // ✅ Mock DAO
        bookingService = new BookingService(bookingDAO);  // ✅ Inject mock

        // Mock a booking
        Booking mockBooking = new Booking(1, testCustomerId, testDriverId, testVehicleId, "Pickup", "Dropoff", 50.0, "REQUESTED");
        try {
            when(bookingDAO.getBookingById(1)).thenReturn(mockBooking);
        } catch (SQLException e) {
            fail("Setup failed: " + e.getMessage());
        }
    }

    @Test
    void testCreateBooking() {
        Booking newBooking = new Booking(0, testCustomerId, 0, testVehicleId, "New Pickup", "New Dropoff", 75.0, "REQUESTED");

        try {
            // ✅ Ensure `createBooking()` returns an int
            when(bookingDAO.addBooking(newBooking)).thenReturn(10);

            int bookingId = bookingService.createBooking(newBooking);
            assertNotEquals(-1, bookingId, "Booking should be created successfully");
        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    @Test
    void testGetBookingById() {
        try {
            Booking booking = bookingService.getBookingById(1);
            assertNotNull(booking, "Booking should not be null");
            assertEquals("Pickup", booking.getPickupLocation());
        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    @Test
    void testCancelBooking() {
        try {
            when(bookingDAO.cancelBooking(1)).thenReturn(true);
            boolean success = bookingService.cancelBooking(1);
            assertTrue(success, "Booking should be cancelled successfully");
        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    @AfterAll
    void tearDown() {
        // Cleanup resources
    }
}
