package model;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String userType; // CUSTOMER, DRIVER, ADMIN

    // ✅ Default Constructor
    public User() {}

    // ✅ Full Constructor for User Creation
    public User(int userId, String firstName, String lastName, String email, String phone, String password, String userType) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.userType = userType;
    }

    // ✅ Constructor for `userId` Only (Used in Driver)
    public User(int userId) {
        this.userId = userId;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
}
