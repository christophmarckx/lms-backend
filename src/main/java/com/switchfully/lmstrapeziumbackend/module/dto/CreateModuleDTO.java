package com.switchfully.lmstrapeziumbackend.module.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateModuleDTO
        (
                @NotNull(message = "Module name must be provided")
                @Size(message = "Module name must be provided", min = 2, max = 255)
                String name,
                UUID parentModuleId
        ) {
}
