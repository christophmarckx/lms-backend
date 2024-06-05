package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import com.switchfully.lmstrapeziumbackend.exception.UserNotFoundException;
import com.switchfully.lmstrapeziumbackend.security.AuthenticationService;
import com.switchfully.lmstrapeziumbackend.user.dto.AuthenticatedUserDTO;
import org.springframework.security.core.Authentication;
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

    public AuthenticatedUserDTO getAuthenticatedUser(Authentication authentication) {
        UUID authenticatedUserId = this.authenticationService.getAuthenticatedUserId(authentication)
                .orElseThrow(UserNotFoundException::new);

        return this.userMapper.toDTO(
                this.userRepository.findById(authenticatedUserId)
                        .orElseThrow(UserNotFoundException::new));
    }

    public User getUserById(UUID userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public void addClassGroupToUser(Classgroup classgroup, User user){
        user.addClassgroup(classgroup);
        this.userRepository.save(user);
    }
}
