package org.servlet.assignment.user.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.servlet.assignment.user.User;
import org.servlet.assignment.user.UsernameAlreadyExistException;
import org.servlet.assignment.user.dao.impl.UserDao;
import org.servlet.assignment.user.service.UserService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

@WebServlet(value = {"/admin/user", "/admin/user/edit", "/admin/user/update", "/admin/user/delete"})
public class UserServlet extends GenericUserServlet<User> {

    private UserService userService = new UserService();

    public UserServlet() {
        super(new UserDao());
    }

    @Override
    void processDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/admin/user/edit")) {
            Long id = Long.parseLong(req.getParameter("id"));
            User user = userService.findById(id);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/page/admin/user-edit.jsp").forward(req, resp);
        } else if (uri.equals("/admin/user")){
            List<User> users = userService.findAllUsers(10, 0);
            req.setAttribute("users", users);
            req.getRequestDispatcher("/page/admin/user.jsp").forward(req, resp);
        } else if (uri.equals("/admin/user/delete")) {
            Long id = Long.parseLong(req.getParameter("id"));
            userService.deleteById(id);
            resp.sendRedirect("/admin/user");
        }
    }

    @Override
    void processRequest(HttpServletRequest request, HttpServletResponse response, User entity) throws IOException {
        response.sendRedirect("/admin/user");
    }

    @Override
    void handleErrors(HttpServletRequest req, HttpServletResponse resp, Map<String, String> violations, User entity) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/admin/user/update")) {
            req.setAttribute("user", entity);
            req.setAttribute("violations_user", violations);
            req.getRequestDispatcher("/page/admin/user-edit.jsp").forward(req, resp);
        } else if (uri.equals("/admin/user")) {
            List<User> users = userService.findAllUsers(10, 0);
            req.setAttribute("users", users);
            req.setAttribute("violations_user", violations);
            req.getRequestDispatcher("/page/admin/user.jsp").forward(req, resp);
        }
    }

    @Override
    public User createEntity(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException, ServletException, IOException {
        String uri = request.getRequestURI();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        Long id = null;
        if (uri.equals("/admin/user/update")) {
            id = Long.parseLong(request.getParameter("id"));
            User user = userService.findById(id);
            user.setId(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(role);
            return user;
        } else {
            boolean isExist = userService.existsByUsername(username);
            if (isExist) {
                throw new UsernameAlreadyExistException("Username already exists");
            }
            return User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(username)
                    .email(email)
                    .password(password)
                    .role(role)
                    .build();
        }
    }

}
