package com.switchfully.lmstrapeziumbackend.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedActionException extends LMSException {
    public UnauthorizedActionException() {
        super("The action is not authorized for the user authenticated", HttpStatus.UNAUTHORIZED.value()); //Check message
    }
}
