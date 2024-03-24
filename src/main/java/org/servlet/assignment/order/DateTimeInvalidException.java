package org.servlet.assignment.order;

public class DateTimeInvalidException extends RuntimeException{

    public DateTimeInvalidException() {
    }

    public DateTimeInvalidException(String message) {
        super(message);
    }
}
