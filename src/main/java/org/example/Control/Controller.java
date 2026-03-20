package org.example.Control;

import org.example.Model.Product;
import org.example.Model.ProductDAO;
import org.example.Model.Validate;
import org.example.View.ProductView;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

public class Controller {
    private final ProductDAO productDAO;
    private final ProductView view;
    private final PaginationController pagination;
    private final BackupControl backupControl;

    public Controller(ProductDAO productDAO, ProductView view) {
        this.productDAO = productDAO;
        this.view = view;
        this.pagination = new PaginationController(productDAO, view);
        this.backupControl = new BackupControl(productDAO, view);
    }

    // ─── Display ──────────────────────────────────────────────────────────────
    public void displayProductslist() {
        pagination.displayPage();
    }

    // ─── Add ──────────────────────────────────────────────────────────────────
    public void addProduct(int id, String name, double price, int stock_qty) {
        if (Validate.validate(id, name, price, stock_qty)) {
            productDAO.addProduct(new Product(id, name, price, stock_qty, null));
            view.showMessage("Product added successfully.");
        } else {
            view.showMessage("Invalid input product detail.");
        }
    }

    // ─── View One ─────────────────────────────────────────────────────────────
    public void viewProduct(int id) {
        Product p = productDAO.getProductById(id);
        if (p != null) {
            Table table = new Table(2, BorderStyle.UNICODE_BOX);
            table.addCell("Field");
            table.addCell("Value");
            table.addCell("ID");
            table.addCell(String.valueOf(p.getId()));
            table.addCell("Name");
            table.addCell(p.getName());
            table.addCell("Price");
            table.addCell(String.valueOf(p.getPrice()));
            table.addCell("Quantity");
            table.addCell(String.valueOf(p.getStock_qty()));
            table.addCell("Imported Date");
            table.addCell(p.getImported_date() != null ? String.valueOf(p.getImported_date()) : "N/A");
            System.out.println(table.render());
        } else {
            view.showMessage("Product not found with ID: " + id);
        }
    }

    // ─── Update ───────────────────────────────────────────────────────────────
    public void updateProduct(int id, String name, double price, int stock_qty) {
        Product existing = productDAO.getProductById(id);
        if (existing == null) {
            view.showMessage("Product not found with ID: " + id);
            return;
        }
        if (Validate.validate(id, name, price, stock_qty)) {
            productDAO.updateProduct(new Product(id, name, price, stock_qty, existing.getImported_date()));
            view.showMessage("Product updated successfully.");
        } else {
            view.showMessage("Invalid input product detail.");
        }
    }

    // ─── Delete ───────────────────────────────────────────────────────────────
    public void deleteProduct(int id) {
        Product existing = productDAO.getProductById(id);
        if (existing == null) {
            view.showMessage("Product not found with ID: " + id);
            return;
        }
        productDAO.deleteProduct(id);
        view.showMessage("Product deleted successfully.");
    }

    // ─── Search ───────────────────────────────────────────────────────────────
    public void searchProduct(String name) {
        List<Product> results = productDAO.searchByName(name);
        if (results.isEmpty()) {
            view.showMessage("No product found with name: " + name);
            return;
        }
        Table table = new Table(5, BorderStyle.UNICODE_BOX);
        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Unit Price");
        table.addCell("Qty");
        table.addCell("Import Date");
        for (Product p : results) {
            table.addCell(String.valueOf(p.getId()));
            table.addCell(p.getName());
            table.addCell(String.valueOf(p.getPrice()));
            table.addCell(String.valueOf(p.getStock_qty()));
            table.addCell(p.getImported_date() != null ? String.valueOf(p.getImported_date()) : "N/A");
        }
        System.out.println(table.render());
    }

    // ─── Pagination (delegates to PaginationController) ───────────────────────
    public void pagination(String direction) {
        switch (direction.toLowerCase()) {
            case "first"    -> pagination.firstPage();
            case "last"     -> pagination.lastPage();
            case "next"     -> pagination.nextPage();
            case "previous" -> pagination.previousPage();
            default -> view.showMessage("Unknown direction: " + direction);
        }
    }

    public void goToPage(int page) {
        pagination.goToPage(page);
    }

    public void setNumberofdisplayedProducts(int size) {
        pagination.setPageSize(size);
    }
    public void backup() {
        backupControl.backup();
    }

}