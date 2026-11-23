package rentVerse.model;

// Represents one item added to a rental cart.
public class CartItem {

    private Product product;
    private int quantity;
    private int rentalDays;

    // Initializes a cart item.
    public CartItem(Product product, int quantity, int rentalDays) {
        this.product = product;
        this.quantity = quantity;
        this.rentalDays = rentalDays;
    }

    // Returns the product.
    public Product getProduct() {
        return product;
    }

    // Returns quantity.
    public int getQuantity() {
        return quantity;
    }

    // Returns rental days.
    public int getRentalDays() {
        return rentalDays;
    }

    // Calculates item total.
    public double getTotalAmount() {
        return product.getDailyRent() * quantity * rentalDays;
    }
}