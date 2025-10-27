package org.vvspickles.userservice.Exceptions;

public class InvalidUserNameOrPassword extends RuntimeException {
    public InvalidUserNameOrPassword(String message) {
        super(message);
    }
}
