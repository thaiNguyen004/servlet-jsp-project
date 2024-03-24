package org.servlet.assignment.order.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.servlet.assignment.order.DateTimeInvalidException;
import org.servlet.assignment.order.Discount;
import org.servlet.assignment.order.Order;
import org.servlet.assignment.order.impl.DiscountDao;
import org.servlet.assignment.order.service.DiscountService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@WebServlet(value = {"/admin/discount", "/admin/discount/edit"
        , "/admin/discount/update", "/admin/discount/delete"
        , "/admin/discount/search/order"})
public class DiscountServlet extends GenericServlet<Discount> {

    private DiscountService discountService = new DiscountService();

    public DiscountServlet() {
        super(new DiscountDao());
    }

    @Override
    void processDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/admin/discount/edit")) {
            Long id = Long.parseLong(req.getParameter("id"));
            Discount discount = discountService.findById(id);
            discount.setOrders(discountService.findOrdersByDiscountId(id));
            req.setAttribute("discount", discount);
            req.getRequestDispatcher("/page/admin/discount-edit.jsp").forward(req, resp);
        } else if (uri.equals("/admin/discount")){
            List<Discount> discounts = discountService.findAllDiscounts(10, 0);
//            discounts.forEach(discount -> discount.setOrders(discountService.findOrdersByDiscountId(discount.getId())));
            req.setAttribute("discounts", discounts);
            req.getRequestDispatcher("/page/admin/discount.jsp").forward(req, resp);
        } else if (uri.equals("/admin/discount/delete")) {
            Long id = Long.parseLong(req.getParameter("id"));
            discountService.deleteById(id);
            resp.sendRedirect("/admin/discount");
        } else if (uri.equals("/admin/discount/search/order")) {
            try {
                Long id = Long.parseLong(req.getParameter("id"));
                List<Order> orders = discountService.findOrdersByDiscountId(id);
                List<Discount> discounts = discountService.findAllDiscounts(10, 0);
                req.setAttribute("discounts", discounts);
                req.setAttribute("orders", orders);
                req.getRequestDispatcher("/page/admin/discount.jsp").forward(req, resp);
            } catch (NumberFormatException ex) {
                List<Discount> discounts = discountService.findAllDiscounts(10, 0);
                req.setAttribute("discounts", discounts);
                req.setAttribute("search_value", "Value of discount id is integer datatype");
                req.getRequestDispatcher("/page/admin/discount.jsp").forward(req, resp);
            }
        }
    }

    @Override
    void processRequest(HttpServletRequest request, HttpServletResponse response, Discount entity) throws IOException {
        response.sendRedirect("/admin/discount");
    }

    @Override
    void handleErrors(HttpServletRequest req, HttpServletResponse resp, Map<String, String> violations, Discount entity) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/admin/discount/update")) {
            req.setAttribute("discount", entity);
            req.setAttribute("violations_discount", violations);
            req.getRequestDispatcher("/page/admin/discount-edit.jsp").forward(req, resp);
        } else if (uri.equals("/admin/discount")) {
            List<Discount> discounts = discountService.findAllDiscounts(10, 0);
            discounts.forEach(discount -> discount.setOrders(discountService.findOrdersByDiscountId(discount.getId())));

            req.setAttribute("discounts", discounts);
            req.setAttribute("violations_discount", violations);
            req.getRequestDispatcher("/page/admin/discount.jsp").forward(req, resp);
        }
    }

    @Override
    public Discount createEntity(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException, ServletException, IOException {
        String uri = request.getRequestURI();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime start = request.getParameter("start")
                != "" ? LocalDateTime.parse(request.getParameter("start"), formatter) : null;
        LocalDateTime end = request.getParameter("end")
                != "" ? LocalDateTime.parse(request.getParameter("end"), formatter) : null;

        String quantity = request.getParameter("quantity");
        String value = request.getParameter("value");
        if (uri.equals("/admin/discount/update")) {
            Long id = Long.parseLong(request.getParameter("id"));
            Discount discount = discountService.findById(id);
            if (start != null) {
                discount.setStart(start);
            }
            if (end != null) {
                discount.setEnd(end);
            }
            if (discount.getEnd().compareTo(discount.getStart()) < 0) {
                throw new DateTimeInvalidException("End less than start");
            }

            discount.setQuantity(quantity);
            discount.setValue(value);
            return discount;
        } else {
            if (start != null && end != null) {
                if (end.compareTo(start) < 0) {
                    throw new DateTimeInvalidException("End less than start");
                }
            }

            return Discount.builder()
                    .start(start)
                    .end(end)
                    .quantity(quantity)
                    .value(value)
                    .build();
        }
    }
}
