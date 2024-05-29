package com.switchfully.lmstrapeziumbackend.classgroup.dto;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import lombok.Getter;

@Getter
public class ClassgroupDTO {
    private String id;
    private String name;
    private CourseDTO course;

    public ClassgroupDTO(String id, String name, CourseDTO course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }
}
