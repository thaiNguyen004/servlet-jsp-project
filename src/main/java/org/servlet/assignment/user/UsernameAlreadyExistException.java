package org.servlet.assignment.user;

public class UsernameAlreadyExistException extends RuntimeException{

    public UsernameAlreadyExistException() {
    }

    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
