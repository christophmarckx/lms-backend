package com.switchfully.lmstrapeziumbackend.codelab.dto;

import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;

import java.util.UUID;

public record CodelabWithModuleDTO(
        UUID id,
        String name,
        String description,
        ModuleDTO module) {
}
