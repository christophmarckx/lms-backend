package com.switchfully.lmstrapeziumbackend.exception;

import org.springframework.http.HttpStatus;

public class IllegalUserRoleException extends LMSException {
    public IllegalUserRoleException() {
        super("You can only give Coaches to create a classgroup", HttpStatus.BAD_REQUEST.value());
    }
}
