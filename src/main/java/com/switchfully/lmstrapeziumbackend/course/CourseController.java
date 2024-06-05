package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseWithModulesDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.UpdateCourseDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDTO> getAllCourses() {
        this.logger.info("GET /courses: Getting all courses");
        return courseService.getAllCourses();

    }

    @PutMapping(consumes = "application/json", produces = "application/json", path = "{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO updateCourse(@PathVariable UUID courseId, @Valid @RequestBody UpdateCourseDTO updateCourseDTO) {
        this.logger.info("PUT /courses: Updating a course");
        return courseService.updateCourse(courseId, updateCourseDTO);
    }

    @GetMapping(produces = "application/json", path = "{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO getCourseById(@PathVariable UUID courseId) {
        this.logger.info("GET /courses: Getting a course by id");
        return courseService.getCourseDTOById(courseId);
    }

    @GetMapping("{courseId}/codelabs")
    public CourseWithModulesDTO getCourseWithModulesById(@PathVariable UUID courseId) {
        this.logger.info("GET /courses: Getting a course with modules by id");
        return courseService.getCourseWithModulesById(courseId);
    }
}
