package com.switchfully.lmstrapeziumbackend.module.dto;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseSummaryDTO;

import java.util.List;
import java.util.UUID;

public record ModuleWithCoursesDTO (
        UUID id,
        String name,
        List<CourseSummaryDTO> courses) {}
