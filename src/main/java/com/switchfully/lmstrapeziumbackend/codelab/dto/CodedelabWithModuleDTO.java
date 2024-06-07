package com.switchfully.lmstrapeziumbackend.codelab.dto;

import java.util.UUID;

public record CodedelabWithModuleDTO(
        UUID id,
        String name,
        String description,
        UUID moduleId
) {
}
