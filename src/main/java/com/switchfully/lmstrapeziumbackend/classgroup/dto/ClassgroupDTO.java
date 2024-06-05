package com.switchfully.lmstrapeziumbackend.classgroup.dto;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseSummaryDTO;

import java.util.UUID;

public record ClassgroupDTO(
        UUID id,
        String name,
        CourseSummaryDTO course) {
}
