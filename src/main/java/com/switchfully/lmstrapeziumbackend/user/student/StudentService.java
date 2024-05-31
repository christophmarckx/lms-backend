package com.switchfully.lmstrapeziumbackend.user.student;

import com.switchfully.lmstrapeziumbackend.security.KeycloakService;
import com.switchfully.lmstrapeziumbackend.user.UserRepository;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.user.coach.CoachMapper;
import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    public StudentService(UserRepository userRepository, KeycloakService keycloakService) {
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
    }

    public StudentDTO createStudent(CreateStudentDTO createStudentDTO) {
        UUID userKeycloakId = this.keycloakService.createUser(createStudentDTO);
        return StudentMapper.toDTO(this.userRepository.save(StudentMapper.toEntity(userKeycloakId, createStudentDTO)));
    }

    public List<StudentDTO> getStudentFollowingClass(UUID classId){
        return this.userRepository
                .findAllByClassgroupsAndByUserRole(classId, UserRole.STUDENT)
                .stream().map(StudentMapper::toDTO)
                .toList();
    }
}
