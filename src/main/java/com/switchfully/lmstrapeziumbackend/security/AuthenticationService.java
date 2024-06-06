package com.switchfully.lmstrapeziumbackend.security;

import com.switchfully.lmstrapeziumbackend.exception.IllegalUserRoleException;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {

    public Optional<UUID> getAuthenticatedUserId(Authentication authentication) {
        UUID authenticatedUserId;

        try {
            authenticatedUserId = UUID.fromString(authentication.getName());
        }
        catch (Exception ex) {
            return Optional.empty();
        }

        return Optional.of(authenticatedUserId);
    }

    public UserRole getAuthenticatedUserRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(authority -> UserRole.valueOf(authority.getAuthority()))
                .findFirst()
                .orElseThrow(IllegalUserRoleException::new);
    }
}
