package service;

import dao.CustomerDAO;
import dao.UserDAO;
import model.Customer;
import model.User;

import java.sql.*;

public class AuthService {
    private UserDAO userDAO;
    private CustomerDAO customerDAO;
    private Connection connection;

    public AuthService() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/megacitycab", "root", "");
            userDAO = new UserDAO(connection);
            customerDAO = new CustomerDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User authenticate(String email, String password, String role) {
        try {
            User user = userDAO.getUserByEmail(email);
            if (user != null && user.getPassword().equals(password) && user.getUserType().equalsIgnoreCase(role)) {
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean emailExists(String email) {
        try {
            return userDAO.emailExists(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerCustomer(Customer customer) {
        try {
            return customerDAO.addCustomer(customer);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
