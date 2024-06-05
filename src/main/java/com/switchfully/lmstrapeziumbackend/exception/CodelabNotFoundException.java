package com.switchfully.lmstrapeziumbackend.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class CodelabNotFoundException extends LMSException {
    public CodelabNotFoundException(UUID codelabID) {
        super("Codelab with id '" + codelabID.toString() + "' does not exist", HttpStatus.NOT_FOUND.value());
    }

}
