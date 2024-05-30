package com.switchfully.lmstrapeziumbackend.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {

    public Optional<UUID> getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID authenticatedUserId;

        try {
            authenticatedUserId = UUID.fromString(authentication.getName());
        }
        catch (Exception ex) {
            return Optional.empty();
        }

        return Optional.of(authenticatedUserId);
    }
}
