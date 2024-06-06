package com.switchfully.lmstrapeziumbackend.user.student;

import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO createStudent(@RequestBody @Valid CreateStudentDTO createStudentDTO) {
        return this.studentService.createStudent(createStudentDTO);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO getStudentById(/*@PathVariable UUID studentId, */Authentication authentication) {
        System.out.println("procced getStudentById");
        this.logger.info("GET /students: Get student by bearer token");
        return studentService.getStudentByAuthentication(authentication/*, studentId*/);
    }
}
