package com.switchfully.lmstrapeziumbackend.exception;

import org.springframework.http.HttpStatus;

public class KeycloakException extends LMSException {
    public KeycloakException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }
}
