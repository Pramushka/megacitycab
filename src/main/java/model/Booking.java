package model;

import java.sql.Date;
import java.sql.Time;

public class Booking {
    private int bookingId;
    private int customerId;
    private int driverId;
    private int vehicleId;
    private String pickupLocation;
    private String dropoffLocation;
    private double fare;
    private String status;
    private Date bookingDate;
    private Time bookingTime;

    public Booking() {}

    public Booking(int bookingId, int customerId, int driverId, int vehicleId, String pickupLocation, String dropoffLocation, double fare, String status, Date bookingDate, Time bookingTime) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.fare = fare;
        this.status = status;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
    }

    // Getters & Setters
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

    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }

    public Time getBookingTime() { return bookingTime; }
    public void setBookingTime(Time bookingTime) { this.bookingTime = bookingTime; }
}
