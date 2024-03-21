package org.servlet.assignment.catalog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.servlet.assignment.catalog.Picture;
import org.servlet.assignment.catalog.Product;
import org.servlet.assignment.catalog.ProductSize;
import org.servlet.assignment.catalog.service.ProductService;
import org.servlet.assignment.catalog.service.SizeService;
import org.servlet.assignment.utils.ValidateUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/size")
public class ProductSizeController extends HttpServlet {
    private final SizeService sizeService = new SizeService();
    private final ProductService productService = new ProductService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductSize newSize = new ProductSize();
        newSize.setSizeName(req.getParameter("sizeName"));
        newSize.setWidth(req.getParameter("width"));
        newSize.setLength(req.getParameter("length"));
        Map<String, String> violations = ValidateUtils.validate(newSize);
        if (violations.isEmpty()) {
            sizeService.createSize(newSize);
            resp.sendRedirect(req.getContextPath() + "/admin/product");
        } else {
            List<Product> products = productService.findAllProducts(10, 0);
            for (Product product : products) {
                List<Picture> pictures = productService.findAllPicturesByProductId(product.getId(), 10, 0);
                product.setPictures(pictures);
            }
            List<ProductSize> sizes = sizeService.findAllSize(10, 0);

            req.setAttribute("products", products);
            req.setAttribute("sizes", sizes);
            req.setAttribute("violations_size", violations);
            req.getRequestDispatcher("/page/admin/product.jsp").forward(req, resp);
        }
    }
}
