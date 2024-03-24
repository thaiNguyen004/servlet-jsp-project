package org.servlet.assignment.order.service;

import org.servlet.assignment.order.CartItem;
import org.servlet.assignment.order.ShoppingSession;
import org.servlet.assignment.order.impl.ShoppingSessionDao;

public class ShoppingSessionService {

    private final ShoppingSessionDao shoppingSessionDao = new ShoppingSessionDao();

    public void addCartToShoppingSession(CartItem cartItem, Long shoppingSessionId) {
        shoppingSessionDao.saveCartItem(cartItem, shoppingSessionId);
    }

    public ShoppingSession findShoppingSessionByUsername(String username) {
        return shoppingSessionDao.findByUsername(username);
    }

    public void createShoppingSession(ShoppingSession shoppingSession) {
        shoppingSessionDao.save(shoppingSession);
    }
}
