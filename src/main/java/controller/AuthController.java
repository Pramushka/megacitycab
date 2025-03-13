package controller;

import model.Customer;
import model.Driver;
import model.User;
import service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.Gson;

@WebServlet("/api/auth/*")
public class AuthController extends HttpServlet {
    private AuthService authService;
    private Gson gson = new Gson();

    public void init() {
        authService = new AuthService();
    }

    // Handles Login, Customer Registration, Driver Registration, and Logout Requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path.equals("/login")) {
            loginUser(request, response);
        } else if (path.equals("/register")) {
            registerCustomer(request, response);
        } else if (path.equals("/register-driver")) {
            registerDriver(request, response);
        } else if (path.equals("/logout")) {
            logoutUser(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid API Endpoint");
        }
    }

    // 🔹 Login Handling
    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        User user = authService.authenticate(email, password, role);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getUserId());

            // ✅ Redirect Customers
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                session.setAttribute("customerId", customer.getCustomerId());
                response.sendRedirect(request.getContextPath() + "/views/customer/landing.jsp");

                // ✅ Redirect Drivers
            } else if (user instanceof Driver) {
                Driver driver = (Driver) user;
                session.setAttribute("driverId", driver.getDriverId());
                response.sendRedirect(request.getContextPath() + "/views/driver/DriverDashboard.jsp");

                // ✅ Redirect Other Users (Admins, etc.)
            } else {
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials");
        }
    }

    // 🔹 Register Customer
    private void registerCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        Customer customer = gson.fromJson(sb.toString(), Customer.class);

        if (authService.emailExists(customer.getEmail())) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "Email is already registered.");
            return;
        }

        User newUser = new User(0, customer.getFirstName(), customer.getLastName(),
                customer.getEmail(), customer.getPhone(),
                customer.getPassword(), "CUSTOMER");

        int userId = authService.registerUser(newUser);

        if (userId == -1) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User Registration Failed");
            return;
        }

        boolean success = authService.registerCustomer(userId);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Customer Registration Failed");
        }
    }

    // 🔹 Register Driver
    private void registerDriver(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        Driver driver = gson.fromJson(sb.toString(), Driver.class);

        if (authService.emailExists(driver.getEmail())) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "Email is already registered.");
            return;
        }

        User newUser = new User(0, driver.getFirstName(), driver.getLastName(),
                driver.getEmail(), driver.getPhone(),
                driver.getPassword(), "DRIVER");

        int userId = authService.registerUser(newUser);

        if (userId == -1) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User Registration Failed");
            return;
        }

        driver.setUserId(userId);
        boolean success = authService.registerDriver(driver);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Driver Registration Failed");
        }
    }

    // 🔹 Logout User
    private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Destroy the session
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
