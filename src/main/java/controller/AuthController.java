package controller;

import model.Customer;
import model.User;
import service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/auth/*")
public class AuthController extends HttpServlet {
    private AuthService authService;

    public void init() {
        authService = new AuthService();
    }

    // Handles Login and Registration Requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path.equals("/login")) {
            loginUser(request, response);
        } else if (path.equals("/register")) {
            registerCustomer(request, response);
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
            request.getSession().setAttribute("user", user);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Login successful\"}");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials");
        }
    }

    // 🔹 Register Customer
    private void registerCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        // Check if email already exists
        if (authService.emailExists(email)) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "Email is already registered.");
            return;
        }

        // Creating Customer Object
        Customer customer = new Customer(0, 0, firstName, lastName, email, phone, password);
        boolean success = authService.registerCustomer(customer);

        // Response
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"" + (success ? "Registration Successful" : "Registration Failed") + "\"}");
    }
}
