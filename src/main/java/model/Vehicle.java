package model;

public class Vehicle {
    private int vehicleId;
    private int driverId;
    private String vehicleNumber;
    private String model;
    private String type; // SEDAN, SUV, MINIVAN, BIKE
    private int capacity;

    // Constructors
    public Vehicle() {}

    public Vehicle(int vehicleId, int driverId, String vehicleNumber, String model, String type, int capacity) {
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.vehicleNumber = vehicleNumber;
        this.model = model;
        this.type = type;
        this.capacity = capacity;
    }

    // Getters and Setters
    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }

    public int getDriverId() { return driverId; }
    public void setDriverId(int driverId) { this.driverId = driverId; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}
