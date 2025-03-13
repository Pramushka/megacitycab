package controller;

import model.Booking;
import service.ManagementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;

@WebServlet("/api/admin/management/*")
public class ManagementController extends HttpServlet {
    private ManagementService managementService;
    private Gson gson = new Gson();

    public void init() {
        try {
            managementService = new ManagementService();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize ManagementService", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path.equals("/assign-driver")) {
            assignDriver(request, response);
        } else if (path.equals("/update-booking-status")) {
            updateBookingStatus(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid API Endpoint");
        }
    }

    private void assignDriver(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        int driverId = Integer.parseInt(request.getParameter("driverId"));

        try {
            boolean success = managementService.assignDriverToBooking(bookingId, driverId);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/views/admin/ViewBookings.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to assign driver");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error assigning driver");
        }
    }

    private void updateBookingStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String status = request.getParameter("status");

        try {
            boolean success = managementService.updateBookingStatus(bookingId, status);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/views/admin/ViewBookings.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update booking status");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating booking status");
        }
    }
}
