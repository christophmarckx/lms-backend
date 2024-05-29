package com.switchfully.lmstrapeziumbackend;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;

import java.util.HashMap;
import java.util.Map;

public class TestConstants {

    //****************** COURSES ******************//

    public static final CreateCourseDTO CREATE_COURSE_DTO_1 = new CreateCourseDTO("Java@Fin", "The Java@Fin course");
    public static final Course COURSE_1 = new Course(CREATE_COURSE_DTO_1.getName(), CREATE_COURSE_DTO_1.getDescription());
    public static final CourseDTO COURSE_DTO_1 = new CourseDTO("f953c154-36f2-4b79-8992-b6f5d4dd24a9", COURSE_1.getName(), COURSE_1.getDescription());
    public static Map<String, Object> getExpectedMapForFullyInvalidCreateCourseDTO() {
        Map<String, Object> mapExpected = new HashMap<>();
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("name", "Name must be between 2 and 255 characters");
        mapExpected.put("message", "Following validation error(s) occurred on /courses");
        mapExpected.put("errors", errorsMap);
        return mapExpected;
    }

    //****************** CLASSGROUPS ******************//

    public static final CreateClassgroupDTO CREATE_CLASSGROUP_DTO_1 = new CreateClassgroupDTO("Java-2024-02", COURSE_DTO_1.getId());
    public static final Classgroup CLASSGROUP_1 = new Classgroup(CREATE_CLASSGROUP_DTO_1.getName(), COURSE_1);
    public static final ClassgroupDTO CLASSGROUP_DTO_1 = new ClassgroupDTO("ID", CLASSGROUP_1.getName(), COURSE_DTO_1);
}
