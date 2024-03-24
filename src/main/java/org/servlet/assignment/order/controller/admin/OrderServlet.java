package org.servlet.assignment.order.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.servlet.assignment.order.Order;
import org.servlet.assignment.order.Status;
import org.servlet.assignment.order.impl.OrderDao;
import org.servlet.assignment.order.service.OrderService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(value = {"/admin/order", "/admin/order/edit", "/admin/order/update", "/admin/order/filter"})
public class OrderServlet extends GenericServlet<Order> {

    private OrderService orderService = new OrderService();

    public OrderServlet() {
        super(new OrderDao());
    }

    @Override
    void processDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/admin/order/edit")) {
            Long id = Long.parseLong(req.getParameter("id"));
            Order order = orderService.findById(id);
            req.setAttribute("order", order);
            req.getRequestDispatcher("/page/admin/order-edit.jsp").forward(req, resp);
        } else if (uri.equals("/admin/order")){
            List<Order> orders = orderService.findAllOrders(10, 0);
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/page/admin/order.jsp").forward(req, resp);
        } else if (uri.equals("/admin/order/filter")) {
            String username = req.getParameter("username");
            List<Order> orders = orderService.findAllOrdersByUsername(username, 10, 0);
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/page/admin/order.jsp").forward(req, resp);
        }
    }

    @Override
    void processRequest(HttpServletRequest request, HttpServletResponse response, Order entity) throws IOException {
        response.sendRedirect("/admin/order");
    }

    @Override
    void handleErrors(HttpServletRequest req, HttpServletResponse resp, Map<String, String> violations, Order entity) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/admin/order/update")) {
            req.setAttribute("order", entity);
            req.setAttribute("violations_order", violations);
            req.getRequestDispatcher("/page/admin/order-edit.jsp").forward(req, resp);
        } else if (uri.equals("/admin/order")) {
            List<Order> orders = orderService.findAllOrders(10, 0);
            req.setAttribute("orders", orders);
            req.setAttribute("violations_order", violations);
            req.getRequestDispatcher("/page/admin/order.jsp").forward(req, resp);
        }
    }

    @Override
    public Order createEntity(HttpServletRequest request)  {
        // fields available to update
        Long id = Long.parseLong(request.getParameter("id"));
        String status = request.getParameter("status");
        String address = request.getParameter("address");
        Order order = orderService.findById(id);
        order.setStatus(Status.valueOf(status));
        order.setAddress(address);
        return order;
    }

}
