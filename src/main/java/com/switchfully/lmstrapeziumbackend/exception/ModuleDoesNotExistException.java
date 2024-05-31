package com.switchfully.lmstrapeziumbackend.exception;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ModuleDoesNotExistException extends LMSException {
    public ModuleDoesNotExistException(UUID moduleID) {
        super("Module with id '" + moduleID.toString()+ "' does not exist", HttpStatus.NOT_FOUND.value());
    }

}
