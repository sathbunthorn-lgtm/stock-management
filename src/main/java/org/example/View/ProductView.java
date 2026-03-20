package org.example.View;

import org.example.Model.ProductDAOImpl;
import org.example.Control.Controller;
import org.example.Control.PaginationController;
import java.util.Scanner;

public class ProductView {
    private final Controller controller = new Controller(new ProductDAOImpl(), this);
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        controller.displayProductslist(); // show table on startup
        while (true) {
            System.out.println("════════════════════════════════════════════════════════════");
            System.out.println("  N. Next    P. Previous    F. First    L. Last    G. Go to");
            System.out.println("  W. Write   R. Read        U. Update   D. Delete          ");
            System.out.println("  S. Search  Se. Set Rows   E. Exit     Ba. Backup               ");
            System.out.println("════════════════════════════════════════════════════════════");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "w" -> {
                    try {
                        System.out.print("Enter product ID: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Enter product name: ");
                        String name = scanner.nextLine().trim();

                        System.out.print("Enter product price: ");
                        double price = Double.parseDouble(scanner.nextLine().trim());

                        System.out.print("Enter product quantity: ");
                        int qty = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Enter 'Sa' to save or 'Un' to cancel: ");
                        String confirm = scanner.nextLine().trim().toLowerCase();

                        if (confirm.equals("sa")) {
                            controller.addProduct(id, name, price, qty);
                            controller.displayProductslist();
                        } else if (confirm.equals("un")) {
                            showMessage("Cancelled. Product not saved.");
                        } else {
                            showMessage("Invalid choice. Product not saved.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }
                case "r" -> {
                    try {
                        System.out.print("Enter product ID to view: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        controller.viewProduct(id);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }
                case "u" -> {
                    try {
                        System.out.print("Enter product ID to update: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Enter new name: ");
                        String name = scanner.nextLine().trim();

                        System.out.print("Enter new price: ");
                        double price = Double.parseDouble(scanner.nextLine().trim());

                        System.out.print("Enter new quantity: ");
                        int qty = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Enter 'Uu' to save update or 'Un' to cancel: ");
                        String confirm = scanner.nextLine().trim().toLowerCase();

                        if (confirm.equals("Sa")) {
                            controller.updateProduct(id, name, price, qty);
                            controller.displayProductslist();
                        } else if (confirm.equals("un")) {
                            showMessage("Cancelled. Product not updated.");
                        } else {
                            showMessage("Invalid choice. Product not updated.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }
                case "d" -> {
                    try {
                        System.out.print("Enter product ID to delete: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        controller.deleteProduct(id);
                        controller.displayProductslist();
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }
                case "s" -> {
                    System.out.print("Enter product name to search: ");
                    String name = scanner.nextLine().trim();
                    controller.searchProduct(name);
                }
                case "n" -> controller.pagination("next");
                case "p" -> controller.pagination("previous");
                case "f" -> controller.pagination("first");
                case "l" -> controller.pagination("last");
                case "g" -> {
                    try {
                        System.out.print("Enter page number: ");
                        int page = Integer.parseInt(scanner.nextLine().trim());
                        controller.goToPage(page);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }
                case "se" -> {
                    try {
                        System.out.print("Enter number of rows to display: ");
                        int rows = Integer.parseInt(scanner.nextLine().trim());
                        controller.setNumberofdisplayedProducts(rows);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }
                case "e" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                case "ba" -> {
                    controller.backup();
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}