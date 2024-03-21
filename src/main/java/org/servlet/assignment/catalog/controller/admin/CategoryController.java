package org.servlet.assignment.catalog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.servlet.assignment.catalog.Category;
import org.servlet.assignment.catalog.service.CategoryService;
import org.servlet.assignment.utils.ValidateUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "category", value = {"/admin/category"})
public class CategoryController extends HttpServlet {

    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Category> categories = categoryService.findAllCategory(10, 0);
        categories.forEach(c -> c.setProducts(categoryService.findAllProductById(c.getId(), 10, 0)));

        req.setAttribute("categories", categories);
        req.getRequestDispatcher("/page/admin/category.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Category category = new Category();
        category.setName(req.getParameter("name"));
        Map<String, String> violations = ValidateUtils.validate(category);
        if (violations.isEmpty()) {
            categoryService.create(category);
            resp.sendRedirect("/admin/category");
        } else {
            List<Category> categories = categoryService.findAllCategory(10, 0);
            categories.forEach(c -> c.setProducts(categoryService.findAllProductById(c.getId(), 10, 0)));

            req.setAttribute("categories", categories);
            req.setAttribute("violations_category", violations);
            req.getRequestDispatcher("/page/admin/category.jsp").forward(req, resp);
        }
    }

}
