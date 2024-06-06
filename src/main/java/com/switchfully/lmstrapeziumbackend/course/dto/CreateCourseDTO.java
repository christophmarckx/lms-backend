package com.switchfully.lmstrapeziumbackend.course.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateCourseDTO(@NotNull(message = "Name must be between 2 and 255 characters")
                              @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
                              String name,

                              String description,

                              List<UUID> moduleIds
) {
}
