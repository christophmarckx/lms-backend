package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupWithMembersDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.CourseMapper;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.dto.CoachDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;

import java.util.List;


public class ClassgroupMapper {
    public static Classgroup toClassgroup(String name, Course course, List<User> coaches) {
        return new Classgroup(name, course, coaches);
    }

    public static ClassgroupDTO toDTO(Classgroup classgroup) {
        return new ClassgroupDTO(classgroup.getId(), classgroup.getName(), CourseMapper.toCourseSummaryDTO(classgroup.getCourse()));
    }

    public static ClassgroupWithMembersDTO toDTO(Classgroup classgroup, List<StudentDTO> students, List<CoachDTO> coaches) {
        return new ClassgroupWithMembersDTO(
                classgroup.getId(),
                classgroup.getName(),
                CourseMapper.toDTO(classgroup.getCourse()),
                students,
                coaches
        );
    }
}
