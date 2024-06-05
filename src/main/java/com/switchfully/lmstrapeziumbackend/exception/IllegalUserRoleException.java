package com.switchfully.lmstrapeziumbackend.exception;

public class IllegalUserRoleException extends LMSException {
    public IllegalUserRoleException() {
        super("You can only give Coaches to create a classgroup", 400);
    }
}
