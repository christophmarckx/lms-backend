package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.exception.AccessForbiddenException;
import com.switchfully.lmstrapeziumbackend.exception.UserNotFoundException;
import com.switchfully.lmstrapeziumbackend.security.AuthenticationService;
import com.switchfully.lmstrapeziumbackend.user.dto.AuthenticatedUserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    public UserService(UserRepository userRepository, UserMapper userMapper, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
    }

    public AuthenticatedUserDTO getAuthenticatedUser(UUID userId, Authentication authentication) {
        UUID authenticatedUserId = this.authenticationService.getAuthenticatedUserId(authentication)
                .orElseThrow(UserNotFoundException::new);

        if (!authenticatedUserId.equals(userId)) {
            throw new AccessForbiddenException();
        }

        return this.userMapper.toDTO(
                this.userRepository.findById(authenticatedUserId)
                        .orElseThrow(UserNotFoundException::new));
    }

    public User getUserById(UUID userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public User updateStudent(User student) {
        return this.userRepository.save(student);
    }
}
