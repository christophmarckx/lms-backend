package com.switchfully.lmstrapeziumbackend.codelab.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateCodelabDTO(
        @NotEmpty(message = "Name is required for Codelab.")
        String name,
        String description,
        @NotNull(message = "Parent module is required for Codelab.")
        UUID moduleId

) {

}
