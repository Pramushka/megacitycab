package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.DriverService;
import model.Booking;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DriverControllerTest {
    @InjectMocks
    private DriverController driverController; // Auto-injects mock services

    @Mock
    private DriverService driverService;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    void testGetAssignedBookings() throws Exception {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking(1, 2, 1, 3, "Pickup", "Dropoff", 20.0, "ASSIGNED"));

        when(driverService.getAssignedBookings(1)).thenReturn(bookings);

        List<Booking> result = driverService.getAssignedBookings(1);

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
