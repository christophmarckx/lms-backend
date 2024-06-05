package com.switchfully.lmstrapeziumbackend.user.student;

import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
public class StudentMapper {

    public static StudentDTO toDTO(User student) {
        return new StudentDTO(student.getId(), student.getEmail(), student.getDisplayName());
    }

    public static List<StudentDTO> toDTO(Collection<User> students) {
        return students.stream().map(StudentMapper::toDTO).toList();
    }

    public static User toUser(UUID userIdFromKeycloak, CreateStudentDTO createStudentDTO) {
        return new User(userIdFromKeycloak, createStudentDTO.email(), createStudentDTO.displayName(), UserRole.STUDENT);
    }
}
