package controller;

import model.Booking;
import service.DriverService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;

@WebServlet("/api/driver/*")
public class DriverController extends HttpServlet {
    private DriverService driverService;
    private Gson gson = new Gson();

    public void init() {
        try {
            driverService = new DriverService();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize DriverService", e);
        }
    }

    // ✅ Handle GET requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        switch (path) {
            case "/assigned-rides":
                getAssignedBookings(request, response);
                break;
            case "/ride-history":
                getRideHistory(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid API Endpoint");
        }
    }

    // ✅ Handle POST requests (Merged to avoid duplicates)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        switch (path) {
            case "/accept-ride":
                acceptRide(request, response);
                break;
            case "/complete":
                completeRide(request, response);
                break;
            case "/update-status":
                updateRideStatus(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid API Endpoint");
        }
    }

    // ✅ Fetch Assigned Rides
    public void getAssignedBookings(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int driverId = (int) request.getSession().getAttribute("driverId");
        try {
            List<Booking> assignedBookings = driverService.getAssignedBookings(driverId);
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(assignedBookings));
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving assigned rides");
        }
    }

    // ✅ Fetch Ride History
    private void getRideHistory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int driverId = (int) request.getSession().getAttribute("driverId");
        try {
            List<Booking> rideHistory = driverService.getRideHistory(driverId);
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(rideHistory));
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving ride history");
        }
    }

    // ✅ Accept Ride
    private void acceptRide(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int driverId = (int) request.getSession().getAttribute("driverId");
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));

        try {
            boolean success = driverService.acceptRide(driverId, bookingId);
            if (success) {
                response.getWriter().write("{\"message\": \"Ride Accepted Successfully\"}");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to accept ride");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error accepting ride");
        }
    }

    // ✅ Complete Ride
    private void completeRide(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));

        try {
            boolean success = driverService.completeRide(bookingId);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/views/driver/DriverDashboard.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to complete ride");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error completing ride");
        }
    }

    // ✅ Update Ride Status
    private void updateRideStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String status = request.getParameter("status");

        try {
            boolean success = driverService.updateRideStatus(bookingId, status);
            if (success) {
                response.getWriter().write("{\"message\": \"Ride status updated successfully\"}");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update ride status");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating ride status");
        }
    }
}
