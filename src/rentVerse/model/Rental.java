package rentVerse.model;

// Stores the details of a rental transaction item.
public class Rental {

    private int rentalId;
    private Customer customer;
    private Product product;
    private int rentalDays;
    private int quantity;
    private double totalRent;
    private boolean returned;

    // Creates a rental record and calculates total rent.
    public Rental(int rentalId, Customer customer, Product product,
                  int rentalDays, int quantity) {

        this.rentalId = rentalId;
        this.customer = customer;
        this.product = product;
        this.rentalDays = rentalDays;
        this.quantity = quantity;

        this.totalRent = product.getDailyRent() * rentalDays * quantity;
        this.returned = false;
    }

    // Returns rental ID.
    public int getRentalId() {
        return rentalId;
    }

    // Returns customer.
    public Customer getCustomer() {
        return customer;
    }

    // Returns product.
    public Product getProduct() {
        return product;
    }

    // Returns rented quantity.
    public int getQuantity() {
        return quantity;
    }

    // Returns rental days.
    public int getRentalDays() {
        return rentalDays;
    }

    // Returns total rent.
    public double getTotalRent() {
        return totalRent;
    }

    // Checks whether rental has been returned.
    public boolean isReturned() {
        return returned;
    }

    // Updates return status.
    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    // Displays rental details.
    @Override
    public String toString() {

        String status;

        if (returned) {
            status = "Returned";
        } else {
            status = "Active";
        }

        return "Rental ID: " + rentalId
                + " | Customer: " + customer.getCustomerName()
                + " | Product: " + product.getProductName()
                + " | Quantity: " + quantity
                + " | Days: " + rentalDays
                + " | Total Rent: Rs. " + totalRent
                + " | Status: " + status;
    }
}