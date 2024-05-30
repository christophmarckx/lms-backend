package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.security.AuthenticationService;
import com.switchfully.lmstrapeziumbackend.user.dto.AuthenticatedUserDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    public UserService(UserRepository userRepository, UserMapper userMapper, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
    }

    public AuthenticatedUserDTO getAuthenticatedUser() {
        UUID authenticatedUserId = this.authenticationService.getAuthenticatedUserId()
                .orElseThrow(() -> new RuntimeException("IMPLEMENT CUSTOM EXCEPTION"));

        return this.userMapper.toDTO(
                this.userRepository.findById(authenticatedUserId)
                        .orElseThrow(() -> new RuntimeException("IMPLEMENT CUSTOM EXCEPTION"))
        );
    }
}
