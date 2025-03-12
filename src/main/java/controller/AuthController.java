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

    // Handles Login, Customer Registration, and Driver Registration Requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path.equals("/login")) {
            loginUser(request, response);
        } else if (path.equals("/register")) {
            registerCustomer(request, response);
        } else if (path.equals("/register-driver")) {
            registerDriver(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid API Endpoint");
        }
    }


    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        User user = authService.authenticate(email, password, role);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("userId", user.getUserId());

            // If user is a customer, store customerId & redirect to landing page
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                request.getSession().setAttribute("customerId", customer.getCustomerId());
                response.sendRedirect(request.getContextPath() + "/views/customer/landing.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp"); // Redirect other users
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials");
        }
    }


    private void registerCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // ✅ Read JSON body correctly
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        Customer customer = gson.fromJson(sb.toString(), Customer.class);

        // ✅ Check if email already exists
        if (authService.emailExists(customer.getEmail())) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "Email is already registered.");
            return;
        }

        // ✅ First, create a user
        User newUser = new User(0, customer.getFirstName(), customer.getLastName(),
                customer.getEmail(), customer.getPhone(),
                customer.getPassword(), "CUSTOMER");

        int userId = authService.registerUser(newUser);

        if (userId == -1) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User Registration Failed");
            return;
        }

        // ✅ Now, insert the user into the customer table
        boolean success = authService.registerCustomer(userId);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp"); // ✅ Redirect to login
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Customer Registration Failed");
        }
    }

    // 🔹 Register Driver
    private void registerDriver(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // ✅ Read JSON body correctly
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        Driver driver = gson.fromJson(sb.toString(), Driver.class);

        // ✅ Check if email already exists
        if (authService.emailExists(driver.getEmail())) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "Email is already registered.");
            return;
        }

        // ✅ First, create a user
        User newUser = new User(0, driver.getFirstName(), driver.getLastName(),
                driver.getEmail(), driver.getPhone(),
                driver.getPassword(), "DRIVER");

        int userId = authService.registerUser(newUser);

        if (userId == -1) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User Registration Failed");
            return;
        }

        // ✅ Now, insert into driver table
        driver.setUserId(userId);  // Set userId from created user
        boolean success = authService.registerDriver(driver);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp"); // ✅ Redirect to login
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Driver Registration Failed");
        }
    }

}