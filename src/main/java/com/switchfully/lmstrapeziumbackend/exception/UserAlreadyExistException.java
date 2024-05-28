package com.switchfully.lmstrapeziumbackend.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String email) { super("A User with the email address " + email + " already exists"); }
}
