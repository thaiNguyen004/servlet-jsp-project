package org.servlet.assignment.order.service;

import org.servlet.assignment.catalog.service.CategoryService;
import org.servlet.assignment.order.Discount;
import org.servlet.assignment.order.Order;
import org.servlet.assignment.order.impl.DiscountDao;

import java.util.List;

public class DiscountService {

    private final DiscountDao discountDao = new DiscountDao();
    private final CategoryService categoryService = new CategoryService();

    public List<Discount> findAllDiscounts(int limit, int offset) {
        return discountDao.findAll(limit, offset);
    }

    public Discount findById(Long id) {
        return discountDao.findById(id);
    }

    public void deleteById(Long id) {
        discountDao.deleteById(id);
    }

    public List<Order> findOrdersByDiscountId(Long id) {
        return discountDao.findOrdersByDiscountId(id);
    }

    public Discount checkDiscountAvailable(Long discountId) {
        return discountDao.checkDiscountAvailable(discountId);
    }
}
