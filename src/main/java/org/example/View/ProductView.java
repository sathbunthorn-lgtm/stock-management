package org.example.View;

import org.example.Model.ProductDAOImpl;
import org.example.Control.Controller;
import java.util.Scanner;

public class ProductView {
    private final Controller controller = new Controller(new ProductDAOImpl(), this);
    private final Scanner scanner = new Scanner(System.in);

    // ─── ANSI Color Codes ─────────────────────────────────────────────────────
    private static final String RESET   = "\u001B[0m";
    private static final String RED     = "\u001B[31m";
    private static final String GREEN   = "\u001B[32m";
    private static final String YELLOW  = "\u001B[33m";
    private static final String BLUE    = "\u001B[34m";
    private static final String CYAN    = "\u001B[36m";
    private static final String WHITE   = "\u001B[37m";
    private static final String BOLD    = "\u001B[1m";

    public void start() {
        controller.displayProductslist();
        while (true) {
            System.out.println(CYAN + "════════════════════════════════════════════════════════════" + RESET);
            System.out.println(YELLOW + "  N." + WHITE + " Next    " +
                    YELLOW + "P." + WHITE + " Previous    " +
                    YELLOW + "F." + WHITE + " First    " +
                    YELLOW + "L." + WHITE + " Last    " +
                    YELLOW + "G." + WHITE + " Go to" + RESET);
            System.out.println(YELLOW + "  W." + WHITE + " Write   " +
                    YELLOW + "R." + WHITE + " Read        " +
                    YELLOW + "U." + WHITE + " Update   " +
                    YELLOW + "D." + WHITE + " Delete" + RESET);
            System.out.println(YELLOW + "  S." + WHITE + " Search  " +
                    YELLOW + "Se." + WHITE + " Set Rows   " +
                    YELLOW + "E." + WHITE + " Exit     " +
                    YELLOW + "Ba." + WHITE + " Backup" + RESET);
            System.out.println(CYAN + "════════════════════════════════════════════════════════════" + RESET);
            System.out.print(BOLD + GREEN + "Enter choice: " + RESET);

            String choice = readInput();

            switch (choice) {
                case "w" -> {
                    try {
                        int nextId = controller.getNextId();
                        System.out.println(CYAN + "Product ID: " + YELLOW + nextId + RESET);

                        System.out.print(BLUE + "Enter product name: " + RESET);
                        String name = readInput();

                        System.out.print(BLUE + "Enter product price: " + RESET);
                        double price = Double.parseDouble(readInput());

                        System.out.print(BLUE + "Enter product quantity: " + RESET);
                        int qty = Integer.parseInt(readInput());

                        System.out.print(YELLOW + "Enter 'sa' to save or 'un' to cancel: " + RESET);
                        String confirm = readInput();

                        if (confirm.equals("sa")) {
                            controller.addProduct(name, price, qty);
                            controller.displayProductslist();
                        } else if (confirm.equals("un")) {
                            showMessage(RED + "Cancelled. Product not saved." + RESET);
                        } else {
                            showMessage(RED + "Invalid choice. Product not saved." + RESET);
                        }
                    } catch (NumberFormatException e) {
                        showMessage(RED + "Invalid input. Please enter a valid number." + RESET);
                    }
                }

                case "r" -> {
                    try {
                        System.out.print(BLUE + "Enter product ID to view: " + RESET);
                        int id = Integer.parseInt(readInput());
                        controller.viewProduct(id);
                    } catch (NumberFormatException e) {
                        showMessage(RED + "Invalid input. Please enter a valid number." + RESET);
                    }
                }

                case "u" -> {
                    try {
                        System.out.print(BLUE + "Enter product ID to update: " + RESET);
                        int id = Integer.parseInt(readInput());

                        System.out.print(BLUE + "Enter new name: " + RESET);
                        String name = readInput();

                        System.out.print(BLUE + "Enter new price: " + RESET);
                        double price = Double.parseDouble(readInput());

                        System.out.print(BLUE + "Enter new quantity: " + RESET);
                        int qty = Integer.parseInt(readInput());

                        System.out.print(YELLOW + "Enter 'sa' to save or 'un' to cancel: " + RESET);
                        String confirm = readInput();

                        if (confirm.equals("sa")) {
                            controller.updateProduct(id, name, price, qty);
                            controller.displayProductslist();
                        } else if (confirm.equals("un")) {
                            showMessage(RED + "Cancelled. Product not updated." + RESET);
                        } else {
                            showMessage(RED + "Invalid choice. Product not updated." + RESET);
                        }
                    } catch (NumberFormatException e) {
                        showMessage(RED + "Invalid input. Please enter a valid number." + RESET);
                    }
                }

                case "d" -> {
                    try {
                        System.out.print(BLUE + "Enter product ID to delete: " + RESET);
                        int id = Integer.parseInt(readInput());
                        controller.deleteProduct(id);
                        controller.displayProductslist();
                    } catch (NumberFormatException e) {
                        showMessage(RED + "Invalid input. Please enter a valid number." + RESET);
                    }
                }

                case "s" -> {
                    System.out.print(BLUE + "Enter product name to search: " + RESET);
                    String name = readInput();
                    controller.searchProduct(name);
                }

                case "n" -> controller.pagination("next");
                case "p" -> controller.pagination("previous");
                case "f" -> controller.pagination("first");
                case "l" -> controller.pagination("last");

                case "g" -> {
                    try {
                        System.out.print(BLUE + "Enter page number: " + RESET);
                        int page = Integer.parseInt(readInput());
                        controller.goToPage(page);
                    } catch (NumberFormatException e) {
                        showMessage(RED + "Invalid input. Please enter a valid number." + RESET);
                    }
                }

                case "se" -> {
                    try {
                        System.out.print(BLUE + "Enter number of rows to display: " + RESET);
                        int rows = Integer.parseInt(readInput());
                        controller.setNumberofdisplayedProducts(rows);
                    } catch (NumberFormatException e) {
                        showMessage(RED + "Invalid input. Please enter a valid number." + RESET);
                    }
                }

                case "ba" -> controller.backup();

                case "e" -> {
                    showMessage(GREEN + "Goodbye!" + RESET);
                    return;
                }

                default -> showMessage(RED + "Invalid choice. Please try again." + RESET);
            }
        }
    }

    // ─── Central input reader ─────────────────────────────────────────────────
    private String readInput() {
        String input = scanner.nextLine();
        if (input == null) return "";
        return input.trim().toLowerCase();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}