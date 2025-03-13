package controller;

import model.Booking;
import service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;

@WebServlet("/api/bookings/*")
public class BookingController extends HttpServlet {
    private BookingService bookingService;
    private Gson gson = new Gson();

    public void init() {
        try {
            bookingService = new BookingService();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize BookingService", e);
        }
    }

    // ✅ Method for injecting service in unit tests
    public void initService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path.equals("/customer")) {
            getCustomerBookings(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid API Endpoint");
        }
    }

    // 🔹 Get Customer's Bookings
    private void getCustomerBookings(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int customerId = (int) request.getSession().getAttribute("customerId");
        try {
            List<Booking> bookings = bookingService.getBookingsByCustomerId(customerId);
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(bookings));
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving bookings");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path.equals("/cancel")) {
            cancelBooking(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid API Endpoint");
        }
    }

    // 🔹 Cancel Booking
    private void cancelBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));

        try {
            boolean success = bookingService.cancelBooking(bookingId);

            if (success) {
                response.getWriter().write("{\"message\": \"Booking Cancelled Successfully\"}");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to Cancel Booking");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error cancelling booking");
        }
    }
}
