package org.servlet.assignment.order.service;

import org.servlet.assignment.order.CartItem;
import org.servlet.assignment.order.impl.CartDao;

public class CartService {
    private final CartDao cartDao = new CartDao();

    public void addCartToShoppingSession(CartItem cartItem) {
        cartDao.save(cartItem);
    }

    public boolean productAlreadyExist(long productId) {
        return cartDao.findCartItemByProductId(productId)!=null;
    }

    public CartItem findCartItemByProductId(long productId) {
        return cartDao.findCartItemByProductId(productId);
    }
}
