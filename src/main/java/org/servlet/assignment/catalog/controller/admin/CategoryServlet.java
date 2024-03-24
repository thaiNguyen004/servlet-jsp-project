package org.servlet.assignment.catalog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.servlet.assignment.catalog.Category;
import org.servlet.assignment.catalog.dao.impl.CategoryDao;
import org.servlet.assignment.catalog.service.CategoryService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet(value = {"/admin/category", "/admin/category/edit", "/admin/category/update", "/admin/category/delete", "/admin/category/delete-product"})
public class CategoryServlet extends GenericServlet<Category> {

    private CategoryService categoryService = new CategoryService();

    public CategoryServlet() {
        super(new CategoryDao());
    }

    @Override
    protected void processDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/admin/category/edit")) {
            Long id = Long.parseLong(req.getParameter("id"));
            Category category = categoryService.findById(id);
            category.setProducts(categoryService.findAllProductById(id, 10, 0));
            req.setAttribute("category", category);
            req.getRequestDispatcher("/page/admin/category-edit.jsp").forward(req, resp);
        } else if (uri.equals("/admin/category")){
            List<Category> categories = categoryService.findAllCategory(10, 0);
            categories.forEach(c -> c.setProducts(categoryService.findAllProductById(c.getId(), 10, 0)));
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/page/admin/category.jsp").forward(req, resp);
        } else if (uri.equals("/admin/category/delete")) {
            Long id = Long.parseLong(req.getParameter("id"));
            categoryService.deleteById(id);
            resp.sendRedirect("/admin/category");
        } else if (uri.equals("/admin/category/delete-product")) {
            Long productId = Long.parseLong(req.getParameter("productId"));
            Long categoryId = Long.parseLong(req.getParameter("categoryId"));
            categoryService.deleteProduct(productId, categoryId);
            resp.sendRedirect("/admin/category/edit?id=" + categoryId);
        }
    }

    @Override
    void processRequest(HttpServletRequest request, HttpServletResponse response, Category entity) throws IOException {
        response.sendRedirect("/admin/category");
    }

    @Override
    void handleErrors(HttpServletRequest req, HttpServletResponse resp, Map<String, String> violations, Category entity) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/admin/category/update")) {
            List<Category> categories = categoryService.findAllCategory(10, 0);
            req.setAttribute("category", entity);
            req.setAttribute("categories", categories);
            req.setAttribute("violations_category", violations);
            req.getRequestDispatcher("/page/admin/category-edit.jsp").forward(req, resp);
        } else if (uri.equals("/admin/category")) {
            List<Category> categories = categoryService.findAllCategory(10, 0);
            categories.forEach(c -> c.setProducts(categoryService.findAllProductById(c.getId(), 10, 0)));
            req.setAttribute("categories", categories);
            req.setAttribute("violations_category", violations);
            req.getRequestDispatcher("/page/admin/category.jsp").forward(req, resp);
        }
    }

    @Override
    public Category createEntity(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException, ServletException, IOException {
        String uri = request.getRequestURI();
        Long id = null;
        if (uri.equals("/admin/category/update")) {
            id = Long.parseLong(request.getParameter("id"));
            Category category = categoryService.findById(id);
            category.setName(request.getParameter("name"));
            return category;
        } else {
            String name = request.getParameter("name");
            return Category.builder()
                    .id(id)
                    .name(name)
                    .build();
        }
    }
}
