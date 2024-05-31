package com.switchfully.lmstrapeziumbackend.user.dto;

import java.util.UUID;

public record CoachDTO (
        UUID id,
        String email,
        String displayName
) {

}
