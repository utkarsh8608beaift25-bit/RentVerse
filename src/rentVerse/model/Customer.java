package rentVerse.model;

// Stores the basic details of a registered customer.
public class Customer {

    private int customerId;
    private String customerName;
    private String phoneNumber;

    // Initializes a customer with their basic information.
    public Customer(int customerId, String customerName, String phoneNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
    }

    // Returns the customer ID.
    public int getCustomerId() {
        return customerId;
    }

    // Returns the customer's name.
    public String getCustomerName() {
        return customerName;
    }

    // Returns the customer's phone number.
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Displays customer information in a readable format.
    @Override
    public String toString() {
        return "Customer ID: " + customerId + " | Name: " + customerName + " | Phone: " + phoneNumber;
    }
}