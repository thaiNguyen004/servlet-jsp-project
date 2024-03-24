package org.servlet.assignment.order.service;

import org.servlet.assignment.order.LineItem;
import org.servlet.assignment.order.Order;
import org.servlet.assignment.order.impl.OrderDao;

import java.util.List;

public class OrderService {

    private final OrderDao userDao = new OrderDao();

    public List<Order> findAllOrders(int limit, int offset) {
        return userDao.findAll(limit, offset);
    }

    public Order findById(Long id) {
        return userDao.findById(id);
    }

    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    public List<Order> findAllOrdersByUsername(String username, int limit, int offset) {
        return userDao.findAllOrdersByUsername(username, limit, offset);
    }

    public Order createOrder(Order order) {
        return userDao.createAndReturnOrder(order);
    }

    public void createLineItems(List<LineItem> lineItems) {
        userDao.createLineItems(lineItems);
    }
}
