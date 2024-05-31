package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.exception.UserNotFoundException;
import com.switchfully.lmstrapeziumbackend.security.AuthenticationService;
import com.switchfully.lmstrapeziumbackend.security.KeycloakService;
import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private UserRepository userRepository;
    private StudentMapper studentMapper;
    private KeycloakService keycloakService;
    private AuthenticationService authenticationService;

    public StudentService(UserRepository userRepository, StudentMapper studentMapper, KeycloakService keycloakService, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.studentMapper = studentMapper;
        this.keycloakService = keycloakService;
        this.authenticationService = authenticationService;
    }

    public StudentDTO createStudent(CreateStudentDTO createStudentDTO) {
        UUID userKeycloakId = this.keycloakService.createUser(createStudentDTO);
        return this.studentMapper.toDTO(this.userRepository.save(this.studentMapper.toUser(userKeycloakId, createStudentDTO)));
    }

    public StudentDTO getStudentByAuthentication(Authentication authentication) {
        UUID studentId = this.authenticationService.getAuthenticatedUserId(authentication)
                .orElseThrow(UserNotFoundException::new);

        Optional<User> userOptional = userRepository.findById(studentId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        return this.studentMapper.toDTO(userOptional.get());
    }
}
