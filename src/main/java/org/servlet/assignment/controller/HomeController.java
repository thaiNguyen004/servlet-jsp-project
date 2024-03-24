package org.servlet.assignment.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.servlet.assignment.catalog.Product;
import org.servlet.assignment.catalog.service.ProductService;
import org.servlet.assignment.user.User;
import org.servlet.assignment.user.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "home", value = {"/home", "/", "/home/pagination"})
public class HomeController extends HttpServlet {

    private ProductService productService = new ProductService();
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);
        if (uri.equals("/")) {
            resp.sendRedirect("/home");
        } else if (uri.equals("/home")) {
            if (session != null && session.getAttribute("user") != null) {
                req.setAttribute("loggedIn", true);
            }
            List<Product> products = productService.findAllProducts(10, 0);
            req.setAttribute("products", products);
            req.setAttribute("currentPage", 0);
            req.getRequestDispatcher("/page/home/index.jsp").forward(req, resp);
        } else if (uri.equals("/home/pagination")) {
            if (session != null && session.getAttribute("user") != null) {
                req.setAttribute("loggedIn", true);
            }

            int pageNumber = Integer.parseInt(req.getParameter("page"));
            // pageNumber start from 0
            List<Product> products = productService.findAllProducts(10, pageNumber * 10);
            req.setAttribute("currentPage", pageNumber);
            req.setAttribute("products", products);
            req.getRequestDispatcher("/page/home/index.jsp").forward(req, resp);
        }
    }
}
