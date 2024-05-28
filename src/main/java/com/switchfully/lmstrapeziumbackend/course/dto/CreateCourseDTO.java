package com.switchfully.lmstrapeziumbackend.course.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCourseDTO {
    @NotNull(message = "Name must be between 2 and 255 characters")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    private String name;

    private String description;

    //TODO Add modules when Module class is done

    public CreateCourseDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
