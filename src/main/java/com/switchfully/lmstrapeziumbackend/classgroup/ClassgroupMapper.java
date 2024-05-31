package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupWithMembersDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.CourseMapper;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;

import java.util.List;

public class ClassgroupMapper {
    public static Classgroup toClassgroup(String name, Course course) {
        return new Classgroup(name, course);
    }

    public static ClassgroupDTO toDTO(Classgroup classgroup) {
        return new ClassgroupDTO(classgroup.getId().toString(), classgroup.getName(), CourseMapper.toDTO(classgroup.getCourse()));
    }

    public static ClassgroupWithMembersDTO toDTO(Classgroup classgroup, List<StudentDTO> students) {
        return new ClassgroupWithMembersDTO(
                classgroup.getId().toString(),
                classgroup.getName(),
                CourseMapper.toDTO(classgroup.getCourse()),
                students
        );

    }
}
