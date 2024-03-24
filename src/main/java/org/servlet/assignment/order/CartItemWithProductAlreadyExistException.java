package org.servlet.assignment.order;

public class CartItemWithProductAlreadyExistException extends RuntimeException {

    public CartItemWithProductAlreadyExistException() {
        super();
    }

    public CartItemWithProductAlreadyExistException(String message) {
        super(message);
    }
}
