package org.example.Control;

import org.example.Model.Product;
import org.example.Model.ProductDAO;
import org.example.View.ProductView;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.CellStyle.HorizontalAlign;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

public class PaginationController {
    private final ProductDAO productDAO;
    private final ProductView view;

    private int pageSize = 5;
    private int currentPage = 1;

    public PaginationController(ProductDAO productDAO, ProductView view) {
        this.productDAO = productDAO;
        this.view = view;
    }

    // ─── Helper: pad string to minimum width ──────────────────────────────────
    private String pad(String value, int width) {
        if (value == null) value = "";
        if (value.length() >= width) return value;
        return value + " ".repeat(width - value.length());
    }

    // ─── Core Display ─────────────────────────────────────────────────────────
    public void displayPage() {
        List<Product> all = productDAO.getAllProduct();
        int totalPages = getTotalPages(all);

        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPages) currentPage = totalPages;

        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, all.size());
        List<Product> pageData = all.subList(start, end);

        CellStyle left = new CellStyle(HorizontalAlign.left);

        Table table = new Table(5, BorderStyle.UNICODE_BOX);

        // Header
        table.addCell(pad("ID",          6),  left);
        table.addCell(pad("Name",        20), left);
        table.addCell(pad("Unit Price",  12), left);
        table.addCell(pad("Qty",         8),  left);
        table.addCell(pad("Import Date", 14), left);

        // Rows
        for (Product p : pageData) {
            table.addCell(pad(String.valueOf(p.getId()),        6),  left);
            table.addCell(pad(p.getName(),                      20), left);
            table.addCell(pad(String.valueOf(p.getPrice()),     12), left);
            table.addCell(pad(String.valueOf(p.getStock_qty()), 8),  left);
            table.addCell(pad(p.getImported_date() != null
                    ? String.valueOf(p.getImported_date())
                    : "N/A",                                    14), left);
        }

        // Footer
        table.addCell(pad("Page: " + currentPage + " of " + totalPages, 38), left, 3);
        table.addCell(pad("Total Records: " + all.size(),                22), left, 2);

        System.out.println(table.render());
    }

    // ─── Navigation ───────────────────────────────────────────────────────────
    public void nextPage() {
        List<Product> all = productDAO.getAllProduct();
        if (currentPage < getTotalPages(all)) {
            currentPage++;
        } else {
            view.showMessage("Already on last page.");
        }
        displayPage();
    }

    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
        } else {
            view.showMessage("Already on first page.");
        }
        displayPage();
    }

    public void firstPage() {
        currentPage = 1;
        displayPage();
    }

    public void lastPage() {
        List<Product> all = productDAO.getAllProduct();
        currentPage = getTotalPages(all);
        displayPage();
    }

    public void goToPage(int page) {
        List<Product> all = productDAO.getAllProduct();
        int totalPages = getTotalPages(all);
        if (page < 1 || page > totalPages) {
            view.showMessage("Invalid page. Total pages: " + totalPages);
            return;
        }
        currentPage = page;
        displayPage();
    }

    // ─── Settings ─────────────────────────────────────────────────────────────
    public void setPageSize(int size) {
        if (size <= 0) {
            view.showMessage("Row count must be greater than 0.");
            return;
        }
        this.pageSize = size;
        this.currentPage = 1;
        view.showMessage("Rows per page set to: " + size);
        displayPage();
    }

    // ─── Helper ───────────────────────────────────────────────────────────────
    private int getTotalPages(List<Product> all) {
        int total = (int) Math.ceil((double) all.size() / pageSize);
        return total == 0 ? 1 : total;
    }
}