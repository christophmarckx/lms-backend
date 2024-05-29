package com.switchfully.lmstrapeziumbackend.exception;
import java.util.UUID;

public class ModuleDoesNotExistException extends RuntimeException {
    public ModuleDoesNotExistException(UUID moduleID) {
        super("Module with id '" + moduleID.toString()+ "' does not exist");
    }

}
