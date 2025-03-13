package controller;

import model.Admin;
import service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/admin/*")
public class AdminController extends HttpServlet {
    private AdminService adminService;

    public void init() {
        try {
            adminService = new AdminService();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize AdminService", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path.equals("/login")) {
            adminLogin(request, response);
        } else if (path.equals("/logout")) {
            adminLogout(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid API Endpoint");
        }
    }

    private void adminLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Admin admin = adminService.authenticate(email, password);

            if (admin != null) {
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);
                response.sendRedirect(request.getContextPath() + "/views/admin/AdminDashboard.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error during authentication");
        }
    }

    private void adminLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/views/admin/login.jsp");
    }
}
