package com.switchfully.lmstrapeziumbackend.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends LMSException  {
    public UserAlreadyExistException(String email) { super("A User with the email address " + email + " already exists", HttpStatus.CONFLICT.value()); }
}
