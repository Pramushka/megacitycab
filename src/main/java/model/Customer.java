package model;

public class Customer extends User {
    private int customerId;

    // Constructors
    public Customer() {}

    public Customer(int customerId, int userId, String firstName, String lastName, String email, String phone, String password) {
        super(userId, firstName, lastName, email, phone, password, "CUSTOMER");
        this.customerId = customerId;
    }

    // Getters and Setters
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
}
