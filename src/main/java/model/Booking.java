package model;

public class Booking {
    private int bookingId;
    private int customerId;
    private int driverId;
    private int vehicleId;
    private String pickupLocation;
    private String dropoffLocation;
    private double fare;
    private String status; // REQUESTED, ACCEPTED, IN_PROGRESS, COMPLETED, CANCELLED

    // 🔹 Additional fields for driver & vehicle info
    private String driverName;
    private String licenseNumber;
    private String vehicleModel;
    private String vehicleNumber;

    // ✅ Default Constructor
    public Booking() {}

    // ✅ Constructor for Regular Bookings
    public Booking(int bookingId, int customerId, int driverId, int vehicleId, String pickupLocation, String dropoffLocation, double fare, String status) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.fare = fare;
        this.status = status;
    }

    // ✅ Constructor for Detailed Bookings (Including Driver & Vehicle Details)
    public Booking(int bookingId, int customerId, int driverId, int vehicleId, String pickupLocation, String dropoffLocation, double fare, String status,
                   String driverName, String licenseNumber, String vehicleModel, String vehicleNumber) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.fare = fare;
        this.status = status;
        this.driverName = driverName;
        this.licenseNumber = licenseNumber;
        this.vehicleModel = vehicleModel;
        this.vehicleNumber = vehicleNumber;
    }

    // ✅ Getters and Setters for new fields
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getVehicleModel() { return vehicleModel; }
    public void setVehicleModel(String vehicleModel) { this.vehicleModel = vehicleModel; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    // ✅ Getters & Setters for existing fields
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getDriverId() { return driverId; }
    public void setDriverId(int driverId) { this.driverId = driverId; }

    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public String getDropoffLocation() { return dropoffLocation; }
    public void setDropoffLocation(String dropoffLocation) { this.dropoffLocation = dropoffLocation; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
