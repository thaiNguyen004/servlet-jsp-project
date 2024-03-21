package org.servlet.assignment.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.servlet.assignment.catalog.Product;

import java.io.IOException;

@WebServlet({"/home", "/"})
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/")) {
            resp.sendRedirect("/home");
        } else {
            req.getRequestDispatcher("/page/home/index.jsp").forward(req, resp);
        }
    }
}
