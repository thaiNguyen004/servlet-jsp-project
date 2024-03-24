package org.servlet.assignment.order;

public class ActionMustLoggedInException extends RuntimeException{

    public ActionMustLoggedInException() {
    }

    public ActionMustLoggedInException(String message) {
        super(message);
    }
}
