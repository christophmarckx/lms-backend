package com.switchfully.lmstrapeziumbackend.classgroup.dto;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseSummaryDTO;
import com.switchfully.lmstrapeziumbackend.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class ClassgroupDTO {
    private String id;
    private String name;
    private CourseSummaryDTO course;

    public ClassgroupDTO(String id, String name, CourseSummaryDTO course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }
}
