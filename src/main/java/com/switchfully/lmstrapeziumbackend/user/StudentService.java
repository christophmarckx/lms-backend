package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.security.KeycloakService;
import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudentService {

    private UserRepository userRepository;
    private StudentMapper studentMapper;
    private KeycloakService keycloakService;

    public StudentService(UserRepository userRepository, StudentMapper studentMapper, KeycloakService keycloakService) {
        this.userRepository = userRepository;
        this.studentMapper = studentMapper;
        this.keycloakService = keycloakService;
    }

    public StudentDTO createStudent(CreateStudentDTO createStudentDTO) {
        UUID userKeycloakId = this.keycloakService.createUser(createStudentDTO);
        return this.studentMapper.toDTO(this.userRepository.save(this.studentMapper.toEntity(userKeycloakId, createStudentDTO)));
    }
}
