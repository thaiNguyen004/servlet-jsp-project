package org.servlet.assignment.catalog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.servlet.assignment.catalog.Category;
import org.servlet.assignment.catalog.Product;
import org.servlet.assignment.catalog.service.CategoryService;
import org.servlet.assignment.catalog.service.ProductService;
import org.servlet.assignment.generic.dao.GenericDao;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet(value = {"/admin/product", "/admin/product/edit", "/admin/product/update", "/admin/product/delete"})
public class ProductServlet extends GenericServlet<Product> {

    private ProductService productService = new ProductService();
    private CategoryService categoryService = new CategoryService();

    public ProductServlet() {
        super(new GenericDao<>());
    }

    @Override
    void processDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/admin/product/edit")) {
            List<Category> categories = categoryService.findAllCategory(10, 0);
            Long id = Long.parseLong(req.getParameter("id"));
            Product product = productService.findById(id);
            req.setAttribute("product", product);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/page/admin/product-edit.jsp").forward(req, resp);
        } else if (uri.equals("/admin/product")){
            List<Product> products = productService.findAllProducts(10, 0);
            List<Category> categories = categoryService.findAllCategory(10, 0);
            req.setAttribute("products", products);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/page/admin/product.jsp").forward(req, resp);
        } else if (uri.equals("/admin/product/delete")) {
            Long id = Long.parseLong(req.getParameter("id"));
            productService.deleteById(id);
            resp.sendRedirect("/admin/product");
        }
    }

    @Override
    void processRequest(HttpServletRequest request, HttpServletResponse response, Product entity) throws IOException {
        response.sendRedirect("/admin/product");
    }

    @Override
    void handleErrors(HttpServletRequest req, HttpServletResponse resp, Map<String, String> violations, Product entity) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/admin/product/update")) {
            List<Category> categories = categoryService.findAllCategory(10, 0);
            req.setAttribute("product", entity);
            req.setAttribute("categories", categories);
            req.setAttribute("violations_product", violations);
            req.getRequestDispatcher("/page/admin/product-edit.jsp").forward(req, resp);
        } else if (uri.equals("/admin/product")) {
            List<Product> products = productService.findAllProducts(10, 0);
            List<Category> categories = categoryService.findAllCategory(10, 0);

            req.setAttribute("products", products);
            req.setAttribute("categories", categories);
            req.setAttribute("violations_product", violations);
            req.getRequestDispatcher("/page/admin/product.jsp").forward(req, resp);
        }
    }

    @Override
    public Product createEntity(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException, ServletException, IOException {
        String uri = request.getRequestURI();
        Long id = null;
        if (uri.equals("/admin/product/update")) {
            id = Long.parseLong(request.getParameter("id"));
        }
        String name = request.getParameter("name");
        String size = request.getParameter("size");
        String price = request.getParameter("price");
        Category category = null;
        if (request.getParameter("category_id") != null) {
            category = categoryService.findById(Long.parseLong(request.getParameter("category_id")));
        }

        return Product.builder()
                .id(id)
                .name(name)
                .size(size)
                .price(price)
                .category(category)
                .build();
    }
}
