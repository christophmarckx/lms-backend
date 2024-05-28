package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;
import com.switchfully.lmstrapeziumbackend.exception.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseDTO createCourse(CreateCourseDTO createCourseDTO) {
        Course courseCreated = courseRepository.save(CourseMapper.toCourse(createCourseDTO));
        return CourseMapper.toDTO(courseCreated);
    }

    public Course getCourseById(UUID courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new CourseNotFoundException();
        }
        return courseOptional.get();
    }
}
