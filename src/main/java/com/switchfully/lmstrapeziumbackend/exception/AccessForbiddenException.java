package com.switchfully.lmstrapeziumbackend.exception;

import org.springframework.http.HttpStatus;

public class AccessForbiddenException extends LMSException {
    public AccessForbiddenException() {
        super("Authenticated user can't access this data", HttpStatus.FORBIDDEN.value());
    }
}
