package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
public class StudentMapper {

    public StudentDTO toDTO(User student) {
        return new StudentDTO(student.getId(), student.getEmail(), student.getDisplayName());
    }

    public List<StudentDTO> toDTO(Collection<User> students) {
        return students.stream().map(this::toDTO).toList();
    }

    public User toEntity(UUID userIdFromKeycloak, CreateStudentDTO createStudentDTO) {
        return new User(userIdFromKeycloak, createStudentDTO.email(), createStudentDTO.displayName(), UserRole.STUDENT);
    }
}
