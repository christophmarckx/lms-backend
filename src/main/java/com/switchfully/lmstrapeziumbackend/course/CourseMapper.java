package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;

public class CourseMapper {
    public static Course toCourse(CreateCourseDTO createCourseDTO) {
        return new Course(createCourseDTO.getName(), createCourseDTO.getDescription());
    }

    public static CourseDTO toDTO(Course course) {
        return new CourseDTO(course.getId().toString(), course.getName(), course.getDescription());
    }
}
