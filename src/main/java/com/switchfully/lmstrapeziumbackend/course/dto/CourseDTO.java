package com.switchfully.lmstrapeziumbackend.course.dto;

import lombok.Getter;

@Getter
public class CourseDTO {
    private String id;
    private String name;
    private String description;
    //TODO Add ModuleDTO

    public CourseDTO(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
