package org.servlet.assignment.order.controller.home;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.servlet.assignment.catalog.Product;
import org.servlet.assignment.catalog.service.ProductService;
import org.servlet.assignment.order.*;
import org.servlet.assignment.order.service.DiscountService;
import org.servlet.assignment.order.service.OrderService;
import org.servlet.assignment.order.service.PaymentService;
import org.servlet.assignment.user.CustomPrincipal;
import org.servlet.assignment.user.User;
import org.servlet.assignment.user.service.UserService;
import org.servlet.assignment.utils.ValidateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(value = {"/order/place", "/order/buy", "/discount/check", "/my-order"})
public class OrderController extends HttpServlet {

    private final OrderService orderService = new OrderService();
    private final ProductService productService = new ProductService();
    private final DiscountService discountService = new DiscountService();
    private final PaymentService paymentService = new PaymentService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("/login");
            return;
        }
        if (uri.equals("/my-order")) {
            CustomPrincipal principal = (CustomPrincipal) req.getSession().getAttribute("user");
            List<Order> orders = orderService.findAllOrdersByUsername(principal.getName(), 10, 0);
            User user = userService.findByUsername(principal.getName());
            req.setAttribute("orders", orders);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/page/home/my-orders.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Map<Product, Integer> orderProducts = null;
        if (uri.equals("/order/place")) {
            orderProducts = new HashMap<>();
            String[] pickedProducts = req.getParameterValues("pickedProducts");
            for (String productPickId : pickedProducts) {
                Product product = productService.findById(Long.parseLong(productPickId));
                orderProducts.put(product, Integer.valueOf(req.getParameter("quantity_" + productPickId)));
            }
            req.setAttribute("orderProducts", orderProducts);
            req.setAttribute("totalPrice", calculateTotalPrice(orderProducts));
            req.setAttribute("endPrice", calculateTotalPrice(orderProducts));
            req.getRequestDispatcher("/page/home/place-order.jsp").forward(req, resp);
        }
        else if (uri.equals("/discount/check")) {
            orderProducts = new HashMap<>();
            String[] pickedProducts = req.getParameterValues("productIds");
            for (String productPickId : pickedProducts) {
                Product product = productService.findById(Long.parseLong(productPickId));
                orderProducts.put(product, Integer.valueOf(req.getParameter("quantity_" + productPickId)));
            }

            String discountStr = req.getParameter("discount");
            try {
                Long discountId = Long.parseLong(discountStr);
                Discount discount = discountService.checkDiscountAvailable(discountId);
                if (discount != null) {
                    req.setAttribute("discountId", discount.getId());
                    req.setAttribute("discountValue", discount.getValue());
                    req.setAttribute("endPrice", (calculateTotalPrice(orderProducts) - Integer.parseInt(discount.getValue())));
                } else {
                    req.setAttribute("discountIssue", "Discount not found");
                    req.setAttribute("endPrice", calculateTotalPrice(orderProducts));
                }
            } catch (NumberFormatException ex) {
                req.setAttribute("endPrice", calculateTotalPrice(orderProducts));
                req.setAttribute("discountIssue", "Discount invalid");
            }
            req.setAttribute("totalPrice", calculateTotalPrice(orderProducts));
            req.setAttribute("orderProducts", orderProducts);
            req.getRequestDispatcher("/page/home/place-order.jsp").forward(req, resp);

        } else {
            // init order object
            Order order = new Order();

            String[] productIds = req.getParameterValues("productIds");
            orderProducts = new HashMap<>();
            for (String productId : productIds) {
                Product product = productService.findById(Long.parseLong(productId));
                orderProducts.put(product, Integer.valueOf(req.getParameter("quantity_" + productId)));
            }
            order.setTotalPrice(calculateTotalPrice(orderProducts));
            String discountIdStr = req.getParameter("discountId");
            try {
                Long discountId = Long.parseLong(discountIdStr);
                Discount discount = discountService.findById(discountId);
                if (discount != null) {
                    order.setDiscount(discount);
                    order.setEndPrice(order.getTotalPrice() - Integer.parseInt(discount.getValue()));
                }
            } catch (NumberFormatException ex) {
                order.setDiscount(null);
                order.setEndPrice(order.getTotalPrice());
            }
            String address = req.getParameter("address");
            order.setAddress(address);
            String description = req.getParameter("description");
            order.setDescription(description);

            // add user
            CustomPrincipal principal = (CustomPrincipal) req.getSession().getAttribute("user");
            order.setUser(userService.findByUsername(principal.getName()));
            order.setStatus(Status.PENDING);
            Map<String, String> violations = ValidateUtils.validate(order);

            // create payment
            Payment payment = new Payment();
            String paymentMethod = req.getParameter("paymentMethod");
            if (paymentMethod == null) {
                violations.put("payment", "Payment method is required");
            } else {
                payment.setMethod(Payment.Method.valueOf(paymentMethod));
                payment.setAmount(order.getEndPrice());
                payment.setStatus(Status.PENDING);
            }

            if (violations.isEmpty()) {
                orderService.createOrder(order);
                payment.setOrder(order);
                paymentService.createPayment(payment);
                List<LineItem> lineItems = createLineItems(orderProducts, order);
                orderService.createLineItems(lineItems);
                resp.sendRedirect("/my-order");
            } else {
                req.setAttribute("violations_order", violations);
                req.setAttribute("orderProducts", orderProducts);
                req.setAttribute("totalPrice", calculateTotalPrice(orderProducts));
                req.setAttribute("endPrice", calculateTotalPrice(orderProducts));
                req.getRequestDispatcher("/page/home/place-order.jsp").forward(req, resp);
            }
        }
    }

    private List<LineItem> createLineItems(Map<Product, Integer> orderProducts, Order orderPersist) {
        List<LineItem> lineItems = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : orderProducts.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setPrice(Integer.valueOf(product.getPrice()));
            lineItem.setQuantity(quantity);
            lineItem.setOrder(orderPersist);
            lineItems.add(lineItem);
        }
        return lineItems;
    }

    public Integer calculateTotalPrice(Map<Product, Integer> orderProducts) {
        Integer totalPrice = 0;
        for (Map.Entry<Product, Integer> entry : orderProducts.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            totalPrice += Integer.valueOf(product.getPrice()) * quantity;
        }
        return totalPrice;
    }

}
