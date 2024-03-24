package org.servlet.assignment.user.controller.home;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.servlet.assignment.user.CustomPrincipal;
import org.servlet.assignment.user.User;
import org.servlet.assignment.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private final UserService userService = new UserService();
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("user");
        }
        if (username != "" && password != "") {
            User userAuthentication = userService.authenticate(username, password);
            if (userAuthentication != null) {
                req.getSession().setAttribute("user", new CustomPrincipal(
                        userAuthentication.getUsername(),
                        userAuthentication.getRole()
                ));
                logger.info("Principal is: name: " + userAuthentication.getUsername()
                        + "; role: " + userAuthentication.getRole());
            } else {
                req.setAttribute("loginError", true);
                req.getRequestDispatcher("/page/home/login.jsp").forward(req, resp);
                return;
            }
        } else {
            req.setAttribute("loginError", true);
            req.getRequestDispatcher("/page/home/login.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/page/home/login.jsp").forward(req, resp);
    }
}
