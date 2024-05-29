package com.switchfully.lmstrapeziumbackend.course.dto;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class CourseDTO {
    private UUID id;
    private String name;
    private String description;
    //TODO Add ModuleDTO

    public CourseDTO(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
