package org.example.Model;

import java.util.List;

public interface ProductDAO {
    void addProduct(Product product);
    List<Product> getAllProduct();
    Product getProductById(int id);
    void updateProduct(Product product);
    void deleteProduct(int id);
    List<Product> searchByName(String name);  // add this
}