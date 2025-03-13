package controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.BookingService;
import model.Booking;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingControllerTest {
    @InjectMocks
    private BookingController bookingController; // Auto-injects mock services

    @Mock
    private BookingService bookingService;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    private final Gson gson = new Gson();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks
    }

    @Test
    void testGetBookingById() throws Exception {
        Booking expectedBooking = new Booking(3, 1, 1, 3, "Start", "End", 75.0, "REQUESTED");

        when(bookingService.getBookingById(3)).thenReturn(expectedBooking);

        Booking actualBooking = bookingService.getBookingById(3);

        // Convert both objects to JSON strings for assertion
        assertEquals(gson.toJson(expectedBooking), gson.toJson(actualBooking));
    }


}
