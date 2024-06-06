package com.switchfully.lmstrapeziumbackend.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ModuleNotFoundException extends LMSException {
    public ModuleNotFoundException(UUID moduleID) {
        super("Module with id '" + moduleID.toString() + "' does not exist", HttpStatus.NOT_FOUND.value());
    }

}
