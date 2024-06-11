package com.switchfully.lmstrapeziumbackend.user.student;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import com.switchfully.lmstrapeziumbackend.classgroup.ClassgroupMapper;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentWithClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentWithProgressDTO;
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

    public static StudentWithProgressDTO toStudentWithProgressDTO(User student, int actualProgression, int totalProgression, ClassgroupDTO classgroupDTO) {
        return new StudentWithProgressDTO(StudentMapper.toStudentWithClassgroupDTO(student, classgroupDTO),  actualProgression, totalProgression);
    }

    public static StudentWithClassgroupDTO toStudentWithClassgroupDTO(User student, ClassgroupDTO classgroupDTO) {
        return new StudentWithClassgroupDTO(student.getId(), student.getEmail(), student.getDisplayName(), classgroupDTO);
    }
}
