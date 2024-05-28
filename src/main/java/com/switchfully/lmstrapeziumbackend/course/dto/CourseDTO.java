package com.switchfully.lmstrapeziumbackend.course.dto;

import lombok.Getter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return Objects.equals(name, courseDTO.name) && Objects.equals(description, courseDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
