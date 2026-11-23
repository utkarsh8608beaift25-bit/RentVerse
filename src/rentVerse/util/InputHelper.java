package rentVerse.util;

import java.util.Scanner;

// Handles and validates all user input.
public class InputHelper {

    private static Scanner scanner = new Scanner(System.in);

    // Reads a valid integer.
    public static int readInt(String message) {

        while (true) {
            System.out.print(message);
            System.out.flush();

            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid whole number.");
            }
        }
    }

    // Reads a valid positive integer.
    public static int readPositiveInt(String message) {

        while (true) {
            int value = readInt(message);

            if (value > 0) {
                return value;
            }

            System.out.println("Value must be greater than zero.");
        }
    }

    // Reads a non-empty string.
    public static String readString(String message) {

        while (true) {
            System.out.print(message);
            System.out.flush();

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    // Reads a valid positive double, allowing currency symbols.
    public static double readPositiveDouble(String message) {

        while (true) {
            System.out.print(message);
            System.out.flush();

            String input = scanner.nextLine().trim();

            // Removes common currency symbols and commas.
            input = input.replace("$", "")
                         .replace("Rs.", "")
                         .replace("Rs", "")
                         .replace(",", "")
                         .trim();

            try {
                double value = Double.parseDouble(input);

                if (Double.isFinite(value) && value > 0) {
                    return value;
                }

                System.out.println("Value must be a positive number.");

            } catch (NumberFormatException e) {
                System.out.println("Invalid amount! Please enter a valid number.");
            }
        }
    }

    // Reads and validates a phone number.
    public static String readPhoneNumber(String message) {

        while (true) {
            System.out.print(message);
            System.out.flush();

            String phone = scanner.nextLine().trim();

            if (phone.matches("[0-9]{10}")) {
                return phone;
            }

            System.out.println("Invalid phone number! Enter exactly 10 digits.");
        }
    }
}