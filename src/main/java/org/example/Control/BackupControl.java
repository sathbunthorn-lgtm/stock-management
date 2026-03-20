package org.example.Control;

import org.example.Model.Product;
import org.example.Model.ProductDAO;
import org.example.View.ProductView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BackupControl {
    private final ProductDAO productDAO;
    private final ProductView view;
    private static final String BACKUP_DIR = "C:/Users/ASUS/OneDrive/Desktop/Fullstack/java/Mini_project/backups/";

    public BackupControl(ProductDAO productDAO, ProductView view) {
        this.productDAO = productDAO;
        this.view = view;
        new File(BACKUP_DIR).mkdirs();
    }

    public void backup() {
        List<Product> all = productDAO.getAllProduct();

        if (all.isEmpty()) {
            view.showMessage("No products to backup.");
            return;
        }

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String filename = BACKUP_DIR + "backup_" + timestamp + ".csv";

        try (Writer writer = new FileWriter(filename)) {
            // Header row
            writer.write("id,name,unit_price,stock_qty,imported_date\n");

            // Data rows
            for (Product p : all) {
                writer.write(
                        p.getId() + "," +
                                p.getName() + "," +
                                p.getPrice() + "," +
                                p.getStock_qty() + "," +
                                (p.getImported_date() != null ? p.getImported_date() : "") +
                                "\n"
                );
            }
            view.showMessage("Backup saved: " + filename);
            view.showMessage("Total products backed up: " + all.size());
        } catch (IOException e) {
            view.showMessage("Backup failed: " + e.getMessage());
        }
    }
}