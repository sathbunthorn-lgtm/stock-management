package org.example.Model;

public class Validate {
    public static boolean validate(String name, double price, int stock_qty) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return false;
        }
        if (price <= 0) {
            System.out.println("Price must be greater than 0.");
            return false;
        }
        if (stock_qty < 0) {
            System.out.println("Quantity cannot be negative.");
            return false;
        }
        return true;
    }
}