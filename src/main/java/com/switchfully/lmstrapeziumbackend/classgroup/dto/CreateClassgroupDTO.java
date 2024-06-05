package com.switchfully.lmstrapeziumbackend.classgroup.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class CreateClassgroupDTO {
    @NotNull(message = "The name of the classgroup must be between 2 and 255 characters")
    @Size(min = 2, max = 255, message = "The name of the classgroup must be between 2 and 255 characters")
    private String name;

    @NotNull(message = "The id of the course must be 36 characters long")
    @Size(min = 36, max = 36, message = "The id of the course must be 36 characters long")
    private String courseId;

    @NotNull(message="Provide at least one coach to create the Classgroup")
    @Size(min= 1, message="Provide at least one coach to create the Classgroup")
    private List<UUID> coaches;

    public CreateClassgroupDTO(String name, String courseId ,List<UUID> coaches) {
        this.name = name;
        this.courseId = courseId;
        this.coaches= coaches;
    }

    public CreateClassgroupDTO() {
    }
}
