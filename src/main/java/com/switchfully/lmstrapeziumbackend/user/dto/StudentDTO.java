package com.switchfully.lmstrapeziumbackend.user.dto;

import java.util.UUID;

public record StudentDTO (
        UUID id,
        String email,
        String displayName
) {}