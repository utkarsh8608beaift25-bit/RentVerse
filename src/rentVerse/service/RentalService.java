package rentVerse.service;

import java.util.ArrayList;

import rentVerse.model.Customer;
import rentVerse.model.Product;
import rentVerse.model.Rental;
import rentVerse.model.CartItem;

// Handles inventory, rentals and dashboard statistics.
public class RentalService {

    private ArrayList<Product> products;
    private ArrayList<Rental> rentals;

    private int nextRentalId = 1;

    // Initializes collections.
    public RentalService() {
        products = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    // Adds product to inventory.
    public void addProduct(Product product) {
        products.add(product);
    }

    // Returns all products.
    public ArrayList<Product> getProducts() {
        return products;
    }

    // Returns all rental records.
    public ArrayList<Rental> getRentals() {
        return rentals;
    }

    // Searches product by ID.
    public Product searchProduct(int productId) {

        for (Product product : products) {

            if (product.getProductId() == productId) {
                return product;
            }
        }

        return null;
    }

    // Displays inventory in a consistently aligned table.
    public void viewProducts() {

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        System.out.println("\n=============================== INVENTORY ===============================");
        System.out.printf("%-7s %-24s %-25s %-12s %-11s%n",
                "ID", "Product", "Category", "Daily Rent", "Stock");
        System.out.println("-----------------------------------------------------------------------");

        for (Product product : products) {
            String name = product.getProductName();
            String category = product.getCategory();
            if (name.length() > 24) name = name.substring(0, 24);
            if (category.length() > 25) category = category.substring(0, 25);

            System.out.printf("%-7d %-24s %-25s Rs. %-8.2f %-11s%n",
                    product.getProductId(), name, category, product.getDailyRent(),
                    product.getAvailableQuantity() + "/" + product.getTotalQuantity());
        }

        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Stock format: Available / Total");
        System.out.println("========================================================================");
    }

    // Processes all cart items as one checkout.
    public boolean checkout(Customer customer, ArrayList<CartItem> cart) {

        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return false;
        }

        // Validates stock for every cart item before checkout.
        for (CartItem item : cart) {

            Product product = item.getProduct();

            if (item.getQuantity() <= 0) {
                System.out.println("Invalid quantity for " + product.getProductName());
                return false;
            }

            if (item.getQuantity() > product.getAvailableQuantity()) {

                System.out.println("Insufficient stock for " + product.getProductName());
                System.out.println("Available quantity: " + product.getAvailableQuantity());

                return false;
            }
        }

        // Reduces inventory only after all items pass validation.
        for (CartItem item : cart) {
            item.getProduct().rentQuantity(item.getQuantity());
        }

        // Creates rental records.
        for (CartItem item : cart) {

            Rental rental = new Rental(
                    nextRentalId,
                    customer,
                    item.getProduct(),
                    item.getRentalDays(),
                    item.getQuantity()
            );

            rentals.add(rental);

            nextRentalId++;
        }

        // Generates the final invoice.
        printBill(customer, cart);

        return true;
    }

    // Generates a formatted rental invoice.
    private void printBill(Customer customer, ArrayList<CartItem> cart) {

        double grandTotal = 0;

        System.out.println();
        System.out.println("============================================================");
        System.out.println("                         RENTVERSE");
        System.out.println("                  RENTAL INVOICE / RECEIPT");
        System.out.println("============================================================");

        System.out.printf("%-15s : %s%n",
                "Customer Name", customer.getCustomerName());

        System.out.printf("%-15s : %s%n",
                "Phone Number", customer.getPhoneNumber());

        System.out.println("------------------------------------------------------------");

        System.out.printf("%-20s %5s %6s %12s %12s%n",
                "Product", "Qty", "Days", "Rate/Day", "Amount");

        System.out.println("------------------------------------------------------------");

        for (CartItem item : cart) {

            double amount = item.getTotalAmount();

            grandTotal += amount;

            String productName = item.getProduct().getProductName();

            if (productName.length() > 20) {
                productName = productName.substring(0, 20);
            }

            System.out.printf("%-20s %5d %6d %12.2f %12.2f%n",
                    productName,
                    item.getQuantity(),
                    item.getRentalDays(),
                    item.getProduct().getDailyRent(),
                    amount);
        }

        System.out.println("------------------------------------------------------------");

        System.out.printf("%47s Rs. %10.2f%n",
                "GRAND TOTAL:", grandTotal);

        System.out.println("============================================================");
        System.out.println("             Thank you for choosing RentVerse!");
        System.out.println("============================================================");
        System.out.println();
    }

    // Returns a rental using rental ID.
    public void returnProduct(int rentalId) {

        for (Rental rental : rentals) {

            if (rental.getRentalId() == rentalId) {

                if (rental.isReturned()) {
                    System.out.println("This rental has already been returned.");
                    return;
                }

                rental.getProduct().returnQuantity(rental.getQuantity());

                rental.setReturned(true);

                System.out.println("Product returned successfully!");

                return;
            }
        }

        System.out.println("Rental ID not found.");
    }

    // Displays rental records in a consistently aligned table.
    public void viewRentals() {

        if (rentals.isEmpty()) {
            System.out.println("\nNo rental records found.");
            return;
        }

        System.out.println("\n============================================== RENTAL RECORDS ==============================================");
        System.out.printf("%-10s %-20s %-22s %-5s %-5s %-14s %-10s%n",
                "Rental ID", "Customer", "Product", "Qty", "Days", "Amount", "Status");
        System.out.println("---------------------------------------------------------------------------------------------------------");

        for (Rental rental : rentals) {
            String status = "Active";
            if (rental.isReturned()) status = "Returned";

            String customerName = rental.getCustomer().getCustomerName();
            String productName = rental.getProduct().getProductName();
            if (customerName.length() > 20) customerName = customerName.substring(0, 20);
            if (productName.length() > 22) productName = productName.substring(0, 22);

            System.out.printf("%-10d %-20s %-22s %-5d %-5d Rs. %-9.2f %-10s%n",
                    rental.getRentalId(), customerName, productName, rental.getQuantity(),
                    rental.getRentalDays(), rental.getTotalRent(), status);
        }

        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.println();
    }

    // Deletes a product only if none of its units are rented.
    public void deleteProduct(int productId) {

        Product product = searchProduct(productId);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        if (product.getRentedQuantity() > 0) {
            System.out.println("Cannot delete product while units are rented.");
            return;
        }

        products.remove(product);

        System.out.println("Product deleted successfully.");
    }

    // Displays owner dashboard.
    public void viewDashboard() {

        int totalProductTypes = products.size();

        int totalUnits = 0;
        int availableUnits = 0;
        int rentedUnits = 0;

        int totalRentals = rentals.size();
        int activeRentals = 0;
        int completedRentals = 0;

        double totalRevenue = 0;

        // Calculates inventory statistics.
        for (Product product : products) {

            totalUnits += product.getTotalQuantity();
            availableUnits += product.getAvailableQuantity();
            rentedUnits += product.getRentedQuantity();
        }

        // Calculates rental statistics.
        for (Rental rental : rentals) {

            totalRevenue += rental.getTotalRent();

            if (rental.isReturned()) {
                completedRentals++;
            } else {
                activeRentals++;
            }
        }

        System.out.println("\n================ OWNER DASHBOARD ================");

        System.out.println("Total Product Types       : " + totalProductTypes);
        System.out.println("Total Inventory Units     : " + totalUnits);
        System.out.println("Available Units           : " + availableUnits);
        System.out.println("Currently Rented Units    : " + rentedUnits);

        System.out.println("-----------------------------------------------");

        System.out.println("Total Rental Transactions : " + totalRentals);
        System.out.println("Active Rentals            : " + activeRentals);
        System.out.println("Completed Rentals         : " + completedRentals);

        System.out.println("-----------------------------------------------");

        System.out.println("Total Rental Revenue      : Rs. " + totalRevenue);

        System.out.println("================================================");
    }
}