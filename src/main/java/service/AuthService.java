package service;

import config.DatabaseConfig;
import dao.DriverDAO;
import dao.UserDAO;
import dao.CustomerDAO;
import model.Driver;
import model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthService {
    private UserDAO userDAO;
    private CustomerDAO customerDAO;
    private Connection connection;
    private DriverDAO driverDAO;

    public AuthService() {
        try {
            connection = DatabaseConfig.getConnection();
            userDAO = new UserDAO(connection);
            customerDAO = new CustomerDAO(connection);
            driverDAO = new DriverDAO(connection);

        } catch (SQLException e) {
            System.err.println("❌ Database Connection Failed!");
            e.printStackTrace();
        }
    }

    // ✅ Check if email exists
    public boolean emailExists(String email) {
        try {
            return userDAO.emailExists(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Register User and return userId
    public int registerUser(User user) {
        try {
            return userDAO.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // ✅ Register Customer
    public boolean registerCustomer(int userId) {
        try {
            return customerDAO.addCustomer(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Register Driver (after User is created)
    public boolean registerDriver(Driver driver) {
        try {
            driverDAO.addDriver(driver);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Authenticate User
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
}
