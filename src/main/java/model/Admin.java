package model;

public class Admin {
    private int adminId;
    private int userId;
    private String role; // SUPER_ADMIN, MODERATOR

    // Constructors
    public Admin() {}

    public Admin(int adminId, int userId, String role) {
        this.adminId = adminId;
        this.userId = userId;
        this.role = role;
    }

    // Getters and Setters
    public int getAdminId() { return adminId; }
    public void setAdminId(int adminId) { this.adminId = adminId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
