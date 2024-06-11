package com.switchfully.lmstrapeziumbackend.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ClassgroupNotFoundException extends LMSException {
    public ClassgroupNotFoundException(UUID classgroupId) {
        super("Classgroup with id '" + classgroupId.toString() + "' does not exist", HttpStatus.NOT_FOUND.value());
    }
}
