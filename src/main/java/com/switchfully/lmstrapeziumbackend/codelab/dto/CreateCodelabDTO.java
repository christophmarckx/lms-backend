package com.switchfully.lmstrapeziumbackend.codelab.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateCodelabDTO(
        @NotEmpty(message = "Name is required for Codelab creation.")
        String name,
        String description,
        @NotNull(message = "Parent module is required for Codelab creation.")
        UUID parentModule
) {
}
