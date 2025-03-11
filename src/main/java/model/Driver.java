package model;

public class Driver extends User {
    private int driverId;
    private String licenseNumber;
    private String status;
    private String nic;
    private double rating;

    // ✅ Default Constructor
    public Driver() {}

    // ✅ Matching Constructor with `userId`
    public Driver(int driverId, int userId, String firstName, String lastName, String email, String phone, String password, String licenseNumber, String status, String nic, double rating) {
        super(userId, firstName, lastName, email, phone, password, "DRIVER"); // Calls User constructor
        this.driverId = driverId;
        this.licenseNumber = licenseNumber;
        this.status = status;
        this.nic = nic;
        this.rating = rating;
    }

    // ✅ Another constructor for queries without user details
    public Driver(int driverId, int userId, String licenseNumber, String status, String nic, double rating) {
        super(userId);  // Calls User constructor with only userId
        this.driverId = driverId;
        this.licenseNumber = licenseNumber;
        this.status = status;
        this.nic = nic;
        this.rating = rating;
    }

    // Getters and Setters
    public int getDriverId() { return driverId; }
    public void setDriverId(int driverId) { this.driverId = driverId; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNic() { return nic; }
    public void setNic(String nic) { this.nic = nic; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
