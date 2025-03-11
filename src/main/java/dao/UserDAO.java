package dao;

import model.Customer;
import model.Driver;
import model.User;
import java.sql.*;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addUser(User user) throws SQLException {
        String query = "INSERT INTO user (first_name, last_name, email, phone, password, user_type) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getUserType());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean emailExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM user LEFT JOIN customer ON user.user_id = customer.user_id LEFT JOIN driver ON user.user_id = driver.user_id WHERE user.email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String userType = rs.getString("user_type");

                if (userType.equalsIgnoreCase("CUSTOMER")) {
                    return new Customer(
                            rs.getInt("customer_id"), // customerId
                            rs.getInt("user_id"), // userId
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("password")
                    );
                } else if (userType.equalsIgnoreCase("DRIVER")) {
                    return new Driver(
                            rs.getInt("driver_id"), // driverId
                            rs.getInt("user_id"), // userId
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("password"),
                            rs.getString("license_number"),
                            rs.getString("status"),
                            rs.getString("nic"),
                            rs.getDouble("rating")
                    );
                }
            }
        }
        return null;
    }
}
