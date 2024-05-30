package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;
import com.switchfully.lmstrapeziumbackend.exception.CourseNotFoundException;
import com.switchfully.lmstrapeziumbackend.module.ModuleRepository;
import com.switchfully.lmstrapeziumbackend.module.Module;
import com.switchfully.lmstrapeziumbackend.module.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModuleService moduleService;

    public CourseService(CourseRepository courseRepository,
                         ModuleService moduleService) {
        this.courseRepository = courseRepository;
        this.moduleService = moduleService;
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

    public List<CourseDTO> getAllCourses() {
            return CourseMapper.toDTO(courseRepository.findAll());
    }
}
