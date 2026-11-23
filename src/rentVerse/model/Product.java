package rentVerse.model;

// Stores product details and inventory quantities.
public class Product {

    private int productId;
    private String productName;
    private String category;
    private double dailyRent;

    private int totalQuantity;
    private int availableQuantity;

    // Initializes product details and stock quantity.
    public Product(int productId, String productName, String category,
                   double dailyRent, int totalQuantity) {

        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.dailyRent = dailyRent;

        this.totalQuantity = totalQuantity;
        this.availableQuantity = totalQuantity;
    }

    // Returns product ID.
    public int getProductId() {
        return productId;
    }

    // Returns product name.
    public String getProductName() {
        return productName;
    }

    // Returns product category.
    public String getCategory() {
        return category;
    }

    // Returns daily rental price.
    public double getDailyRent() {
        return dailyRent;
    }

    // Returns total inventory quantity.
    public int getTotalQuantity() {
        return totalQuantity;
    }

    // Returns available quantity.
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    // Calculates currently rented quantity.
    public int getRentedQuantity() {
        return totalQuantity - availableQuantity;
    }

    // Checks if at least one unit is available.
    public boolean isAvailable() {
        return availableQuantity > 0;
    }

    // Reduces available stock when products are rented.
    public boolean rentQuantity(int quantity) {

        if (quantity <= 0 || quantity > availableQuantity) {
            return false;
        }

        availableQuantity = availableQuantity - quantity;
        return true;
    }

    // Increases available stock when products are returned.
    public void returnQuantity(int quantity) {

        if (quantity > 0 && quantity <= getRentedQuantity()) {
            availableQuantity = availableQuantity + quantity;
        }
    }

    // Adds new inventory units.
    public void addStock(int quantity) {

        if (quantity > 0) {
            totalQuantity = totalQuantity + quantity;
            availableQuantity = availableQuantity + quantity;
        }
    }

    // Displays product details.
    @Override
    public String toString() {

        return "ID: " + productId
                + " | Name: " + productName
                + " | Category: " + category
                + " | Daily Rent: Rs. " + dailyRent
                + " | Stock: " + availableQuantity + "/" + totalQuantity;
    }
}