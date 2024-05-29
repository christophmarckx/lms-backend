package com.switchfully.lmstrapeziumbackend.module.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class CreateModuleDTO {

    @NotBlank(message="Module name must be provided")
    String name;

    UUID parentModuleId;

    public CreateModuleDTO(String name, UUID parentModuleId) {
        this.name = name;
        this.parentModuleId = parentModuleId;
    }

    public String getName() {
        return name;
    }

    public UUID getParentModuleId() {
        return parentModuleId;
    }
}
