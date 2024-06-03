package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;

import java.util.List;

public class CourseMapper {
    public static Course toCourse(CreateCourseDTO createCourseDTO) {
        return new Course(createCourseDTO.getName(), createCourseDTO.getDescription());
    }

    public static CourseDTO toDTO(Course course) {
        return new CourseDTO(course.getId(), course.getName(), course.getDescription());
    }
    public static List<CourseDTO> toDTO(List<Course> courses) {
        return courses.stream().map(CourseMapper::toDTO).toList();
    }
}
