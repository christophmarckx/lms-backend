package com.switchfully.lmstrapeziumbackend.classgroup.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateClassgroupDTO {
    @NotNull(message = "The name of the classgroup must be between 2 and 255 characters")
    @Size(min = 2, max = 255, message = "The name of the classgroup must be between 2 and 255 characters")
    private String name;

    @NotNull(message = "The id of the course must be 36 characters long")
    @Size(min = 36, max = 36, message = "The id of the course must be 36 characters long")
    private String courseId;
}
