package com.switchfully.lmstrapeziumbackend.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends LMSException {
    public UserNotFoundException(){super("No user with the provided id exists", HttpStatus.NOT_FOUND.value());}
}
