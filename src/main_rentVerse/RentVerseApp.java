package main_rentVerse;

import java.util.ArrayList;

import rentVerse.model.CartItem;
import rentVerse.model.Customer;
import rentVerse.model.Product;
import rentVerse.service.RentalService;
import rentVerse.util.InputHelper;

// Main class of the RentVerse application.
public class RentVerseApp {

    public static void main(String[] args) {

        RentalService service = new RentalService();

        // Adding sample inventory.
        service.addProduct(new Product(1, "Laptop", "Electronics", 800, 3));
        service.addProduct(new Product(2, "Camera", "Electronics", 500, 4));
        service.addProduct(new Product(3, "Drill Machine", "Tools & Hardware", 300, 2));
        service.addProduct(new Product(4, "Office Chair", "Furniture", 200, 5));

        boolean running = true;

        while (running) {

            System.out.println("\n========== RENTVERSE ==========");
            System.out.println("1. Owner Dashboard");
            System.out.println("2. Add Product");
            System.out.println("3. View Inventory");
            System.out.println("4. Search Product");
            System.out.println("5. Rent Products");
            System.out.println("6. Return Product");
            System.out.println("7. View Rentals");
            System.out.println("8. Delete Product");
            System.out.println("9. Exit");
            System.out.println("==============================");

            int choice = InputHelper.readInt("Enter your choice: ");

            switch (choice) {

                // Displays dashboard.
                case 1:

                    service.viewDashboard();

                    break;

                // Displays inventory and directly starts adding a product.
                case 2:

                    System.out.println("\n================ EXISTING INVENTORY ================");

                    if (service.getProducts().isEmpty()) {

                        System.out.println("No products currently registered.");

                    } else {

                        System.out.printf("%-4s %-7s %-24s %-25s %-12s %-11s%n",
                                "No.", "ID", "Product", "Category", "Daily Rent", "Stock");

                        System.out.println("--------------------------------------------------------------------------------");

                        for (int i = 0; i < service.getProducts().size(); i++) {

                            Product existingProduct = service.getProducts().get(i);

                            String displayName = existingProduct.getProductName();
                            String displayCategory = existingProduct.getCategory();
                            if (displayName.length() > 24) displayName = displayName.substring(0, 24);
                            if (displayCategory.length() > 25) displayCategory = displayCategory.substring(0, 25);

                            System.out.printf("%-4d %-7d %-24s %-25s Rs. %-8.2f %-11s%n",
                                    i + 1, existingProduct.getProductId(), displayName, displayCategory,
                                    existingProduct.getDailyRent(),
                                    existingProduct.getAvailableQuantity() + "/" + existingProduct.getTotalQuantity());
                        }
                    }

                    System.out.println("================================================================================");

                    int id = InputHelper.readPositiveInt("Enter Product ID: ");

                    if (service.searchProduct(id) != null) {

                        System.out.println("Product ID already exists.");

                        break;
                    }

                    String name = InputHelper.readString("Enter Product Name: ");

                    // Displays expanded category list.
                    System.out.println("\n========== SELECT PRODUCT CATEGORY ==========");
                    System.out.println("1. Electronics");
                    System.out.println("2. Furniture");
                    System.out.println("3. Tools & Hardware");
                    System.out.println("4. Sports & Fitness");
                    System.out.println("5. Vehicles");
                    System.out.println("6. Musical Instruments");
                    System.out.println("7. Cameras & Photography");
                    System.out.println("8. Home Appliances");
                    System.out.println("9. Gaming");
                    System.out.println("10. Books & Educational");
                    System.out.println("11. Party & Event Equipment");
                    System.out.println("12. Outdoor & Camping");
                    System.out.println("13. Fashion & Accessories");
                    System.out.println("14. Other");
                    System.out.println("============================================");

                    int categoryChoice;

                    while (true) {

                        categoryChoice = InputHelper.readInt("Enter category number: ");

                        if (categoryChoice >= 1 && categoryChoice <= 14) {
                            break;
                        }

                        System.out.println("Invalid category! Select between 1 and 14.");
                    }

                    String category = "";

                    switch (categoryChoice) {

                        case 1:
                            category = "Electronics";
                            break;

                        case 2:
                            category = "Furniture";
                            break;

                        case 3:
                            category = "Tools & Hardware";
                            break;

                        case 4:
                            category = "Sports & Fitness";
                            break;

                        case 5:
                            category = "Vehicles";
                            break;

                        case 6:
                            category = "Musical Instruments";
                            break;

                        case 7:
                            category = "Cameras & Photography";
                            break;

                        case 8:
                            category = "Home Appliances";
                            break;

                        case 9:
                            category = "Gaming";
                            break;

                        case 10:
                            category = "Books & Educational";
                            break;

                        case 11:
                            category = "Party & Event Equipment";
                            break;

                        case 12:
                            category = "Outdoor & Camping";
                            break;

                        case 13:
                            category = "Fashion & Accessories";
                            break;

                        case 14:

                            category = InputHelper.readString("Enter Custom Category Name: ");

                            break;
                    }

                    double price = InputHelper.readPositiveDouble("Enter Daily Rent: ");

                    int quantity = InputHelper.readPositiveInt("Enter Total Quantity: ");

                    service.addProduct(new Product(id, name, category, price, quantity));

                    System.out.println("\nProduct added successfully!");
                    System.out.println("Category: " + category);

                    break;

                // Displays inventory.
                case 3:

                    service.viewProducts();

                    break;

                // Searches product.
                case 4:

                    int searchId = InputHelper.readPositiveInt("Enter Product ID: ");

                    Product foundProduct = service.searchProduct(searchId);

                    if (foundProduct != null) {
                        System.out.println(foundProduct);
                    } else {
                        System.out.println("Product not found.");
                    }

                    break;

                // Handles cart-based rental checkout.
                case 5:

                    ArrayList<CartItem> cart = new ArrayList<>();

                    String customerName = InputHelper.readString("Enter Customer Name: ");

                    String phone = InputHelper.readPhoneNumber("Enter 10-digit Phone Number: ");

                    Customer customer = new Customer(1, customerName, phone);

                    boolean shopping = true;
                    boolean checkoutCompleted = false;

                    while (shopping) {

                        ArrayList<Product> availableProducts = new ArrayList<>();

                        // Collects products with available stock.
                        for (Product product : service.getProducts()) {

                            if (product.isAvailable()) {
                                availableProducts.add(product);
                            }
                        }

                        if (availableProducts.isEmpty()) {

                            System.out.println("No products currently available.");

                            break;
                        }

                        // Displays available products.
                        System.out.println("\n================ AVAILABLE PRODUCTS ================");

                        System.out.printf("%-4s %-7s %-24s %-25s %-12s %-11s%n",
                                "No.", "ID", "Product", "Category", "Daily Rent", "Stock");

                        System.out.println("--------------------------------------------------------------------------------");

                        for (int i = 0; i < availableProducts.size(); i++) {

                            Product product = availableProducts.get(i);

                            String displayName = product.getProductName();
                            String displayCategory = product.getCategory();
                            if (displayName.length() > 24) displayName = displayName.substring(0, 24);
                            if (displayCategory.length() > 25) displayCategory = displayCategory.substring(0, 25);

                            System.out.printf("%-4d %-7d %-24s %-25s Rs. %-8.2f %-11d%n",
                                    i + 1, product.getProductId(), displayName, displayCategory,
                                    product.getDailyRent(), product.getAvailableQuantity());
                        }

                        System.out.println("--------------------------------------------------------------------------------");
                        System.out.println("0. Finish Adding Products");
                        System.out.println("================================================================================");

                        int productChoice;

                        while (true) {

                            productChoice = InputHelper.readInt("Select product number: ");

                            if (productChoice == 0) {
                                break;
                            }

                            if (productChoice >= 1 && productChoice <= availableProducts.size()) {
                                break;
                            }

                            System.out.println("Invalid selection!");
                        }

                        if (productChoice == 0) {
                            break;
                        }

                        Product selectedProduct = availableProducts.get(productChoice - 1);

                        int quantityToRent = InputHelper.readPositiveInt("Enter quantity: ");

                        if (quantityToRent > selectedProduct.getAvailableQuantity()) {

                            System.out.println("Only "
                                    + selectedProduct.getAvailableQuantity()
                                    + " units available.");

                            continue;
                        }

                        int rentalDays = InputHelper.readPositiveInt("Enter rental days: ");

                        // Prevents duplicate products in cart.
                        boolean alreadyAdded = false;

                        for (CartItem item : cart) {

                            if (item.getProduct().getProductId()
                                    == selectedProduct.getProductId()) {

                                alreadyAdded = true;
                                break;
                            }
                        }

                        if (alreadyAdded) {

                            System.out.println("This product is already in your cart.");

                            System.out.println("Please finish the current order first.");

                            continue;
                        }

                        CartItem cartItem = new CartItem(
                                selectedProduct,
                                quantityToRent,
                                rentalDays
                        );

                        cart.add(cartItem);

                        System.out.println(selectedProduct.getProductName()
                                + " added to cart successfully!");

                        // Displays current cart.
                        System.out.println("\n========== CURRENT CART ==========");

                        double cartTotal = 0;

                        for (CartItem item : cart) {

                            System.out.println(item.getProduct().getProductName()
                                    + " | Quantity: " + item.getQuantity()
                                    + " | Days: " + item.getRentalDays()
                                    + " | Amount: Rs. " + item.getTotalAmount());

                            cartTotal += item.getTotalAmount();
                        }

                        System.out.println("----------------------------------");
                        System.out.println("Cart Total: Rs. " + cartTotal);
                        System.out.println("==================================");

                        System.out.println("\n1. Add Another Product");
                        System.out.println("2. Checkout");
                        System.out.println("3. Cancel Order");

                        int cartChoice;

                        while (true) {

                            cartChoice = InputHelper.readInt("Enter choice: ");

                            if (cartChoice >= 1 && cartChoice <= 3) {
                                break;
                            }

                            System.out.println("Invalid choice!");
                        }

                        if (cartChoice == 2) {

                            shopping = false;

                            checkoutCompleted = service.checkout(customer, cart);

                        } else if (cartChoice == 3) {

                            shopping = false;

                            cart.clear();

                            System.out.println("Order cancelled.");

                        }
                    }

                    // Handles checkout when the user finishes selecting products.
                    if (!cart.isEmpty() && !checkoutCompleted) {

                        System.out.println("\n========== FINAL CART ==========");

                        double finalTotal = 0;

                        for (CartItem item : cart) {

                            System.out.println(item.getProduct().getProductName()
                                    + " | Quantity: " + item.getQuantity()
                                    + " | Days: " + item.getRentalDays()
                                    + " | Amount: Rs. " + item.getTotalAmount());

                            finalTotal += item.getTotalAmount();
                        }

                        System.out.println("--------------------------------");
                        System.out.println("Total Amount: Rs. " + finalTotal);
                        System.out.println("================================");

                        System.out.println("1. Checkout");
                        System.out.println("2. Cancel Order");

                        int finalChoice;

                        while (true) {

                            finalChoice = InputHelper.readInt("Enter choice: ");

                            if (finalChoice == 1 || finalChoice == 2) {
                                break;
                            }

                            System.out.println("Invalid choice!");
                        }

                        if (finalChoice == 1) {

                            checkoutCompleted = service.checkout(customer, cart);

                        } else {

                            System.out.println("Order cancelled.");
                        }
                    }

                    break;

                // Returns a rental.
                case 6:

                    // Displays active rentals and lets the user select by list number.
                    ArrayList<rentVerse.model.Rental> activeRentals = new ArrayList<>();

                    for (rentVerse.model.Rental rental : service.getRentals()) {
                        if (!rental.isReturned()) {
                            activeRentals.add(rental);
                        }
                    }

                    if (activeRentals.isEmpty()) {
                        System.out.println("\nNo active rentals to return.");
                        break;
                    }

                    System.out.println("\n======================= ACTIVE RENTALS =======================");
                    System.out.printf("%-5s %-10s %-20s %-22s %-5s %-5s%n",
                            "No.", "Rental ID", "Customer", "Product", "Qty", "Days");
                    System.out.println("------------------------------------------------------------");

                    for (int i = 0; i < activeRentals.size(); i++) {
                        rentVerse.model.Rental rental = activeRentals.get(i);
                        String returnCustomerName = rental.getCustomer().getCustomerName();
                        String returnProductName = rental.getProduct().getProductName();

                        if (returnCustomerName.length() > 20) {
                            returnCustomerName = returnCustomerName.substring(0, 20);
                        }
                        if (returnProductName.length() > 22) {
                            returnProductName = returnProductName.substring(0, 22);
                        }

                        System.out.printf("%-5d %-10d %-20s %-22s %-5d %-5d%n",
                                i + 1, rental.getRentalId(), returnCustomerName, returnProductName,
                                rental.getQuantity(), rental.getRentalDays());
                    }

                    System.out.println("------------------------------------------------------------");
                    System.out.println("0. Back");

                    int returnChoice;
                    while (true) {
                        returnChoice = InputHelper.readInt("Select rental number to return: ");
                        if (returnChoice == 0 || (returnChoice >= 1 && returnChoice <= activeRentals.size())) {
                            break;
                        }
                        System.out.println("Invalid selection. Please select a listed number.");
                    }

                    if (returnChoice != 0) {
                        int selectedRentalId = activeRentals.get(returnChoice - 1).getRentalId();
                        service.returnProduct(selectedRentalId);
                    }

                    break;

                // Displays rentals.
                case 7:

                    service.viewRentals();

                    break;

                // Deletes product.
                case 8:

                    int deleteId = InputHelper.readPositiveInt("Enter Product ID to delete: ");

                    service.deleteProduct(deleteId);

                    break;

                // Exits application.
                case 9:

                    running = false;

                    System.out.println("Thank you for using RentVerse!");

                    break;

                default:

                    System.out.println("Invalid choice! Select between 1 and 9.");
            }
        }
    }
}