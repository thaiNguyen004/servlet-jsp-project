package org.servlet.assignment.catalog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.servlet.assignment.catalog.Category;
import org.servlet.assignment.catalog.Picture;
import org.servlet.assignment.catalog.Product;
import org.servlet.assignment.catalog.ProductSize;
import org.servlet.assignment.catalog.service.CategoryService;
import org.servlet.assignment.catalog.service.ProductService;
import org.servlet.assignment.catalog.service.SizeService;
import org.servlet.assignment.utils.ValidateUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/product")
public class ProductController extends HttpServlet {

    private final ProductService productService = new ProductService();
    private final SizeService sizeService = new SizeService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productService.findAllProducts(10, 0);
        for (Product product : products) {
            List<Picture> pictures = productService.findAllPicturesByProductId(product.getId(), 10, 0);
            product.setPictures(pictures);
        }
        List<ProductSize> sizes = sizeService.findAllSize(10, 0);
        List<Category> categories = categoryService.findAllCategory(10, 0);

        req.setAttribute("products", products);
        req.setAttribute("sizes", sizes);
        req.setAttribute("categories", categories);
        req.getRequestDispatcher("/page/admin/product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = new Product();
        /*Name*/
        product.setName(req.getParameter("name"));

        /*Size*/
        Long sizeId = Long.valueOf(req.getParameter("size_id"));
        ProductSize productSize = sizeService.findById(sizeId);
        product.setSize(productSize);

        /*Category*/
        Long categoryId = Long.valueOf(req.getParameter("category_id"));
        Category category = categoryService.findById(categoryId);
        product.setCategory(category);

        /*Price*/
        product.setPrice(req.getParameter("price"));

        Map<String, String> violations = ValidateUtils.validate(product);
        if (violations.isEmpty()) {
            productService.create(product);
            resp.sendRedirect(req.getContextPath() + "/admin/product");
        } else {
            List<Product> products = productService.findAllProducts(10, 0);
            for (Product p : products) {
                List<Picture> pictures = productService.findAllPicturesByProductId(p.getId(), 10, 0);
                p.setPictures(pictures);
            }
            List<ProductSize> sizes = sizeService.findAllSize(10, 0);
            List<Category> categories = categoryService.findAllCategory(10, 0);
            req.setAttribute("products", products);
            req.setAttribute("categories", categories);
            req.setAttribute("sizes", sizes);
            req.setAttribute("violations_product", violations);
            req.getRequestDispatcher("/page/admin/product.jsp").forward(req, resp);
        }
    }

    public static String getFileName(Part part) {
        String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
