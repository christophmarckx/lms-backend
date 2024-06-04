package com.switchfully.lmstrapeziumbackend.course.dto;

import java.util.UUID;

public record CourseSummaryDTO(UUID id,
                               String name,
                               String description) {}
