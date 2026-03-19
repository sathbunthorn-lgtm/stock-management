package org.example.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public void addProduct(Product product) {
        String query = "INSERT INTO products (id, name, unit_price, stock_qty) VALUES (?, ?, ?, ?)";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getStock_qty());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("unit_price"),
                        rs.getInt("stock_qty"),
                        rs.getDate("imported_date") != null
                                ? rs.getDate("imported_date").toLocalDate()
                                : null
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all products: " + e.getMessage());
        }
        return products;
    }

    @Override
    public Product getProductById(int id) {
        Product product = null;
        String query = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    product = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("unit_price"),
                            rs.getInt("stock_qty"),
                            rs.getDate("imported_date") != null
                                    ? rs.getDate("imported_date").toLocalDate()
                                    : null
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching product by ID: " + e.getMessage());
        }
        return product;
    }

    @Override
    public void updateProduct(Product product) {
        // imported_date is NOT updated — it stays as originally set by the database
        String query = "UPDATE products SET name = ?, unit_price = ?, stock_qty = ? WHERE id = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getStock_qty());
            pstmt.setInt(4, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
        }
    }

    @Override
    public void deleteProduct(int id) {
        String query = "DELETE FROM products WHERE id = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE LOWER(name) LIKE LOWER(?)";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("unit_price"),
                            rs.getInt("stock_qty"),
                            rs.getDate("imported_date") != null
                                    ? rs.getDate("imported_date").toLocalDate()
                                    : null
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching product by name: " + e.getMessage());
        }
        return products;
    }
}