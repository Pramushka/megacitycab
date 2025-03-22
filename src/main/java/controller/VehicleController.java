package controller;

import com.google.gson.Gson;
import service.ManagementService;
import model.Vehicle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/vehicles")  // ✅ This API fetches vehicle data
public class VehicleController extends HttpServlet {
    private ManagementService managementService;

    @Override
    public void init() throws ServletException {
        try {
            managementService = new ManagementService();
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Vehicle> vehicles = managementService.getAllVehicles();
            String json = new Gson().toJson(vehicles);
            response.getWriter().write(json);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to fetch vehicles\"}");
            e.printStackTrace();
        }
    }
}
