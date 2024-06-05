package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.user.dto.AuthenticatedUserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public AuthenticatedUserDTO toDTO(User user) {
        return new AuthenticatedUserDTO(user.getId(),
                user.getEmail(), user.getDisplayName(), user.getRole());
    }
}
