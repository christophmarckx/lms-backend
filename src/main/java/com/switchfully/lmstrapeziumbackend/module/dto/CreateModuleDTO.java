package com.switchfully.lmstrapeziumbackend.module.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateModuleDTO
(
        @NotBlank(message="Module name must be provided")
        String name,
        UUID parentModuleId

) { }
