package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseSummaryDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;
import com.switchfully.lmstrapeziumbackend.module.ModuleMapper;

import java.util.List;

public class CourseMapper {
    public static Course toCourse(CreateCourseDTO createCourseDTO) {
        return new Course(createCourseDTO.name(), createCourseDTO.description());
    }

    public static CourseDTO toDTO(Course course) {
        return new CourseDTO(course.getId(), course.getName(), course.getDescription(), ModuleMapper.toDTO(course.getModules()));
    }

    public static CourseSummaryDTO toCourseSummaryDTO(Course course) {
        return new CourseSummaryDTO(course.getId(), course.getName(), course.getDescription());
    }

    public static List<CourseDTO> toDTO(List<Course> courses) {
        return courses.stream().map(CourseMapper::toDTO).toList();
    }

    public static List<CourseSummaryDTO> toCourseSummaryDTO(List<Course> courses) {
        return courses.stream().map(CourseMapper::toCourseSummaryDTO).toList();
    }
}
