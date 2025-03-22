// --- BookingController.java ---

package controller;

import com.google.gson.Gson;
import config.DatabaseConfig;
import dao.VehicleDAO;
import model.Booking;
import model.Vehicle;
import service.BookingService;
import service.ManagementService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class BookingController extends HttpServlet {
    private BookingService bookingService;
    private ManagementService managementService;
    private Gson gson = new Gson();

    public void init() {
        try {
            bookingService = new BookingService();
            managementService = new ManagementService();
        } catch (SQLException e) {
            throw new RuntimeException("Initialization error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();  // e.g., "/book-ride"
        String pathInfo = request.getPathInfo();        // e.g., "/customer"

        if ("/book-ride".equals(servletPath)) {
            showBookingForm(request, response);
        } else if ("/api/bookings".equals(servletPath) && "/customer".equals(pathInfo)) {
            getCustomerBookings(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path == null || path.equals("/create")) {
            createBooking(request, response);
        } else if (path.equals("/check")) {
            calculateFare(request, response);
        } else if (path.equals("/cancel")) {
            cancelBooking(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void createBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false);
            int customerId = (int) session.getAttribute("customerId");
            Booking preBooking = (Booking) session.getAttribute("pendingBooking");

            if (preBooking == null) {
                response.sendRedirect(request.getContextPath() + "/book-ride");
                return;
            }

            preBooking.setCustomerId(customerId);
            preBooking.setDriverId(0);
            preBooking.setStatus("REQUESTED");
            preBooking.setBookingDate(new java.sql.Date(System.currentTimeMillis()));

            int bookingId = bookingService.createBooking(preBooking);
            session.removeAttribute("pendingBooking");

            if (bookingId > 0) {
                response.sendRedirect(request.getContextPath() + "/views/customer/bookingConfirmation.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Booking creation failed.");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating booking.");
        }
    }

    private void calculateFare(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
            String pickup = request.getParameter("pickupLocation");
            String dropoff = request.getParameter("dropoffLocation");
            String bookingDateStr = request.getParameter("bookingDate");

            double distance = estimateDistance(pickup, dropoff);
            double fare = distance * 120;

            Booking preBooking = new Booking();
            preBooking.setVehicleId(vehicleId);
            preBooking.setPickupLocation(pickup);
            preBooking.setDropoffLocation(dropoff);
            preBooking.setFare(fare);
            preBooking.setBookingDate(Date.valueOf(bookingDateStr));

            request.getSession().setAttribute("pendingBooking", preBooking);
            response.sendRedirect(request.getContextPath() + "/book-ride");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error calculating fare.");
        }
    }

    private double estimateDistance(String pickup, String dropoff) {
        return 8.0; // Replace with real calculation later
    }



    private void showBookingForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection conn = DatabaseConfig.getConnection();
            VehicleDAO vehicleDAO = new VehicleDAO(conn);
            List<Vehicle> vehicles = managementService.getAllVehicles();

            // ✅ Debugging: Print to console
            System.out.println("Vehicles fetched: " + vehicles.size());
            for (Vehicle v : vehicles) {
                System.out.println("Vehicle ID: " + v.getVehicleId() + ", Model: " + v.getModel());
            }

            request.setAttribute("vehicles", vehicles);
            request.getRequestDispatcher("/views/customer/book-ride.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    private void getCustomerBookings(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int customerId = (int) request.getSession().getAttribute("customerId");
            List<Booking> bookings = bookingService.getBookingsByCustomerId(customerId);
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(bookings));
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void cancelBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            boolean success = bookingService.cancelBooking(bookingId);
            if (success) {
                response.getWriter().write("{\"message\": \"Booking Cancelled Successfully\"}");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}