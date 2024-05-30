package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.security.KeycloakService;
import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO createStudent(@RequestBody @Valid CreateStudentDTO createStudentDTO) {
        return this.studentService.createStudent(createStudentDTO);
    }

    @GetMapping(produces = "application/json", path = "{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO getStudentById(@PathVariable String studentId) {
        this.logger.info("GET /students: Get student by id");
        return studentService.getStudentById(UUID.fromString(studentId));
    }
}
