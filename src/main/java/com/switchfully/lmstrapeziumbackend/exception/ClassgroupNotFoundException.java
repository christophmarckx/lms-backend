package com.switchfully.lmstrapeziumbackend.exception;

import java.util.UUID;

public class ClassgroupNotFoundException extends RuntimeException {
    public ClassgroupNotFoundException(UUID classgroupId) {
        super("Classgroup with id '" + classgroupId.toString()+ "' does not exist");
    }
}
