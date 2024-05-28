package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;

public class ClassgroupMapper {
    public static Classgroup toClassgroup(String name, Course course) {
        return new Classgroup(name, course);
    }

    public static ClassgroupDTO toDTO(Classgroup classgroup) {
        return null;
    }
}
