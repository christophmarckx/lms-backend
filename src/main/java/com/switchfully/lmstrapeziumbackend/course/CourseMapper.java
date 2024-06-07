package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseSummaryDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseWithModulesDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;
import com.switchfully.lmstrapeziumbackend.module.Module;
import com.switchfully.lmstrapeziumbackend.module.ModuleMapper;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleWithCodelabsDTO;

import java.util.List;

public class CourseMapper {
    public static Course toCourse(CreateCourseDTO createCourseDTO, List<Module> modules) {
        return new Course(createCourseDTO.name(), createCourseDTO.description(), modules);
    }

    public static CourseDTO toDTO(Course course) {
        return new CourseDTO(course.getId(), course.getName(), course.getDescription(), ModuleMapper.toDTO(course.getModules()));
    }

    public static List<CourseDTO> toDTO(List<Course> courses) {
        return courses.stream().map(CourseMapper::toDTO).toList();
    }

    public static CourseSummaryDTO toCourseSummaryDTO(Course course) {
        return new CourseSummaryDTO(course.getId(), course.getName(), course.getDescription());
    }

    public static List<CourseSummaryDTO> toCourseSummaryDTO(List<Course> courses) {
        return courses.stream().map(CourseMapper::toCourseSummaryDTO).toList();
    }

    public static CourseWithModulesDTO toCourseWithModulesDTO(Course course, List<ModuleWithCodelabsDTO> moduleWithCodelabsDTOs) {
        return new CourseWithModulesDTO(course.getId(), course.getName(), course.getDescription(), moduleWithCodelabsDTOs);
    }
}
