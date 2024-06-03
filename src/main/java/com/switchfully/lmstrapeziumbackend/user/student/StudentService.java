package com.switchfully.lmstrapeziumbackend.user.student;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import com.switchfully.lmstrapeziumbackend.classgroup.ClassgroupService;
import com.switchfully.lmstrapeziumbackend.exception.UserNotFoundException;
import com.switchfully.lmstrapeziumbackend.security.AuthenticationService;
import com.switchfully.lmstrapeziumbackend.security.KeycloakService;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserRepository;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    private UserRepository userRepository;
    private KeycloakService keycloakService;
    private AuthenticationService authenticationService;

    public StudentService(UserRepository userRepository, KeycloakService keycloakService, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
        this.authenticationService = authenticationService;
    }

    public StudentDTO createStudent(CreateStudentDTO createStudentDTO) {
        UUID userKeycloakId = this.keycloakService.createUser(createStudentDTO);
        return StudentMapper.toDTO(this.userRepository.save(StudentMapper.toUser(userKeycloakId, createStudentDTO)));
    }

    public List<StudentDTO> getStudentFollowingClass(Classgroup classgroup){
        return this.userRepository
                .findAllByClassgroups(classgroup)
                .stream().map(StudentMapper::toDTO)
                .toList();
    }

    public StudentDTO getStudentByAuthentication(Authentication authentication) {
        UUID studentId = this.authenticationService.getAuthenticatedUserId(authentication)
                .orElseThrow(UserNotFoundException::new);

        Optional<User> userOptional = userRepository.findById(studentId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        return StudentMapper.toDTO(userOptional.get());
    }
}
