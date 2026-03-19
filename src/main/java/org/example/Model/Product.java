package org.example.Model;

import java.time.LocalDate;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock_qty;
    private LocalDate imported_date;

    public Product(int id, String name, double price, int stock_qty, LocalDate imported_date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock_qty = stock_qty;
        this.imported_date = imported_date;
    }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock_qty(int stock_qty) { this.stock_qty = stock_qty; }
    public void setImported_date(LocalDate imported_date) { this.imported_date = imported_date; }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock_qty() { return stock_qty; }
    public LocalDate getImported_date() { return imported_date; }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock_qty=" + stock_qty +
                ", imported_date=" + imported_date +
                '}';
    }
}