package com.switchfully.lmstrapeziumbackend.course.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateCourseDTO {
    @NotNull(message = "Name must be between 2 and 255 characters")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    String name;

    public UpdateCourseDTO(String name) {
        this.name = name;
    }
}
