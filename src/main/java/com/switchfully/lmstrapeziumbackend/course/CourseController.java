package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "courses")
@CrossOrigin(originPatterns = "http://localhost:4200") //Edit if deployed
public class CourseController {
    private final Logger logger = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDTO createCourse(@Valid @RequestBody CreateCourseDTO createCourseDTO) {
        this.logger.info("POST /courses: Creating a course");
        return this.courseService.createCourse(createCourseDTO);
    }
}
