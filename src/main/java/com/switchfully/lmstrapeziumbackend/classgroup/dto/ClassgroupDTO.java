package com.switchfully.lmstrapeziumbackend.classgroup.dto;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseSummaryDTO;

public record ClassgroupDTO(
        String id,
        String name,
        CourseSummaryDTO course) {
}
