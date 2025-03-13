package controller;

import model.Booking;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookingControllerTest {
    private static BookingController bookingController;
    private static BookingService bookingService;
    private static HttpServletRequest request;
    private static HttpServletResponse response;

    @BeforeAll
    static void setup() {
        bookingService = mock(BookingService.class);
        bookingController = new BookingController();
        bookingController.initService(bookingService); // ✅ Fix: Ensure initService() is properly used

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    void testCreateBooking() throws IOException, ServletException, SQLException { // ✅ Fix: Add SQLException to method signature
        String jsonInput = "{ \"customerId\": 1, \"vehicleId\": 2, \"pickupLocation\": \"Start\", \"dropoffLocation\": \"End\", \"fare\": 100.0, \"status\": \"REQUESTED\" }";
        when(request.getPathInfo()).thenReturn("/create");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonInput)));

        // ✅ Fix: Mock `createBooking()` properly to handle SQLException
        when(bookingService.createBooking(Mockito.any(Booking.class))).thenReturn(10);

        StringWriter responseWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(writer);

        bookingController.doPost(request, response);

        writer.flush();
        assertEquals("{\"message\": \"Booking Created Successfully\", \"bookingId\": 10}", responseWriter.toString());
    }

    @Test
    void testCancelBooking() throws IOException, ServletException, SQLException { // ✅ Fix: Add SQLException
        when(request.getPathInfo()).thenReturn("/cancel");
        when(request.getParameter("bookingId")).thenReturn("5");

        // ✅ Fix: Ensure `cancelBooking()` throws SQLException properly
        when(bookingService.cancelBooking(5)).thenReturn(true);

        bookingController.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testGetBookingById() throws IOException, ServletException, SQLException { // ✅ Fix: Add SQLException
        when(request.getPathInfo()).thenReturn("/get");
        when(request.getParameter("bookingId")).thenReturn("3");

        // ✅ Fix: Ensure `getBookingById()` handles SQLException
        when(bookingService.getBookingById(3)).thenReturn(new Booking(3, 1, 2, 3, "Start", "End", 75.0, "REQUESTED"));

        StringWriter responseWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(writer);

        bookingController.doGet(request, response);

        writer.flush();
        assertEquals("{\"bookingId\": 3, \"customerId\": 1, \"pickupLocation\": \"Start\", \"dropoffLocation\": \"End\", \"fare\": 75.0, \"status\": \"REQUESTED\"}", responseWriter.toString());
    }
}
