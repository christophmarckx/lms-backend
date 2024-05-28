package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private UserRepository userRepository;
    private StudentMapper studentMapper;

    public StudentService(UserRepository userRepository, StudentMapper studentMapper) {
        this.userRepository = userRepository;
        this.studentMapper = studentMapper;
    }

    public StudentDTO createStudent(CreateStudentDTO createStudentDTO) {
        return this.studentMapper.toDTO(this.userRepository.save(this.studentMapper.toEntity(createStudentDTO)));
    }
}
