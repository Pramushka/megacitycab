package dao;

import model.Customer;
import java.sql.*;

public class CustomerDAO {
    private Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE: Adds a new customer
    public boolean addCustomer(Customer customer) throws SQLException {
        String userQuery = "INSERT INTO user (first_name, last_name, email, phone, password, user_type) VALUES (?, ?, ?, ?, ?, ?)";
        String customerQuery = "INSERT INTO customer (user_id) VALUES (?)";

        try (PreparedStatement userStmt = connection.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement customerStmt = connection.prepareStatement(customerQuery)) {

            // Insert into User table
            userStmt.setString(1, customer.getFirstName());
            userStmt.setString(2, customer.getLastName());
            userStmt.setString(3, customer.getEmail());
            userStmt.setString(4, customer.getPhone());
            userStmt.setString(5, customer.getPassword());
            userStmt.setString(6, "CUSTOMER");

            int affectedRows = userStmt.executeUpdate();
            if (affectedRows == 0) {
                return false; // User insertion failed
            }

            // Get the generated user ID
            ResultSet generatedKeys = userStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);

                // Insert into Customer table
                customerStmt.setInt(1, userId);
                int customerRows = customerStmt.executeUpdate();
                return customerRows > 0; // Return true if customer was added successfully
            }
        }
        return false;
    }
}
