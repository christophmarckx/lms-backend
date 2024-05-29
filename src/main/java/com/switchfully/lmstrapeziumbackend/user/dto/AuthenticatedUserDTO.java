package com.switchfully.lmstrapeziumbackend.user.dto;

import com.switchfully.lmstrapeziumbackend.user.UserRole;

import java.util.UUID;

public record AuthenticatedUserDTO(
        UUID id,
        String email,
        String displayName,
        UserRole role
) {
}
