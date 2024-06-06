package com.switchfully.lmstrapeziumbackend.codelab.dto;

import java.util.UUID;

public record CodelabDTO(
        UUID id,
        String name,
        String description
) {
}
