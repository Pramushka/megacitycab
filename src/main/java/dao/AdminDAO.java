package dao;

import model.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    private Connection connection;

    public AdminDAO(Connection connection) {
        this.connection = connection;
    }

    // ✅ Authenticate Admin (Returns Admin Object if login is valid)
    public Admin authenticate(String email, String password) throws SQLException {
        String query = "SELECT a.admin_id, a.user_id, a.role FROM admin a " +
                "JOIN user u ON a.user_id = u.user_id " +
                "WHERE u.email = ? AND u.password = SHA2(?, 256)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getInt("admin_id"),
                        rs.getInt("user_id"),
                        rs.getString("role")
                );
            }
        }
        return null; // If login fails
    }

    // ✅ Insert a New Admin
    public boolean insertAdmin(int userId, String role) throws SQLException {
        String query = "INSERT INTO admin (user_id, role) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, role);
            return stmt.executeUpdate() > 0;
        }
    }
}
