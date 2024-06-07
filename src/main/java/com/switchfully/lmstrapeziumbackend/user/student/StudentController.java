package com.switchfully.lmstrapeziumbackend.user.student;

import com.switchfully.lmstrapeziumbackend.classgroup.ClassgroupService;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseSummaryDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;
    private final ClassgroupService classgroupService;

    public StudentController(StudentService studentService, ClassgroupService classgroupService) {
        this.studentService = studentService;
        this.classgroupService = classgroupService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO createStudent(@RequestBody @Valid CreateStudentDTO createStudentDTO) {
        return this.studentService.createStudent(createStudentDTO);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO getStudentById(/*@PathVariable UUID studentId, */Authentication authentication) {
        this.logger.info("GET /students: Get student by bearer token");
        return studentService.getStudentByAuthentication(authentication/*, studentId*/);
    }

    @GetMapping("{studentId}/course")
    public ResponseEntity<CourseSummaryDTO> getFollowedCourseByStudentId(@PathVariable UUID studentId, Authentication authentication) {
        this.logger.info("GET /students: Get course followed by a student");
        Optional<CourseSummaryDTO> optCourseDTO = studentService.getCourseFollowedByStudentId(authentication, studentId);
        if (optCourseDTO.isPresent()) {
            return ResponseEntity.of(optCourseDTO);
        }
        return ResponseEntity.noContent().build();
    }
}
