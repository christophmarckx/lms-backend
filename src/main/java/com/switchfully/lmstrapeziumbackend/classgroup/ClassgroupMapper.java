package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupWithMembersDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.CourseMapper;
import com.switchfully.lmstrapeziumbackend.user.dto.CoachDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import java.util.List;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserMapper;


public class ClassgroupMapper {
    public static Classgroup toClassgroup(String name, Course course, List<User> coachs) {
        return new Classgroup(name, course, coachs);
    }

    public static ClassgroupDTO toDTO(Classgroup classgroup) {
        return new ClassgroupDTO(classgroup.getId().toString(), classgroup.getName(), CourseMapper.toCourseSummaryDTO(classgroup.getCourse()));
    }

    public static ClassgroupWithMembersDTO toDTO(Classgroup classgroup, List<StudentDTO> students, List<CoachDTO> coaches) {
        return new ClassgroupWithMembersDTO(
                classgroup.getId().toString(),
                classgroup.getName(),
                CourseMapper.toDTO(classgroup.getCourse()),
                students,
                coaches
        );
    }
}
