package service;

import dao.AdminDAO;
import model.Admin;
import config.DatabaseConfig;
import java.sql.SQLException;

public class AdminService {
    private AdminDAO adminDAO;

    public AdminService() throws SQLException {
        this.adminDAO = new AdminDAO(DatabaseConfig.getConnection());
    }

    // ✅ Authenticate Admin
    public Admin authenticate(String email, String password) throws SQLException {
        return adminDAO.authenticate(email, password);
    }
}
