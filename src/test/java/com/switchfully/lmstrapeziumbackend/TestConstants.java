package com.switchfully.lmstrapeziumbackend;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.module.Module;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.CreateModuleDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestConstants {

    //****************** COURSES ******************//

    public static final CreateCourseDTO CREATE_COURSE_DTO_1 = new CreateCourseDTO("Java@Fin", "The Java@Fin course");
    public static final Course COURSE_1 = new Course(CREATE_COURSE_DTO_1.getName(), CREATE_COURSE_DTO_1.getDescription());
    public static final CourseDTO COURSE_DTO_1 = new CourseDTO(UUID.fromString("f953c154-36f2-4b79-8992-b6f5d4dd24a9"), COURSE_1.getName(), COURSE_1.getDescription());
    public static Map<String, Object> getExpectedMapForFullyInvalidCreateCourseDTO() {
        Map<String, Object> mapExpected = new HashMap<>();
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("name", "Name must be between 2 and 255 characters");
        mapExpected.put("message", "Following validation error(s) occurred on /courses");
        mapExpected.put("errors", errorsMap);
        return mapExpected;
    }

    //****************** CLASSGROUPS ******************//

    public static final CreateClassgroupDTO CREATE_CLASSGROUP_DTO_1 = new CreateClassgroupDTO("Java-2024-02", COURSE_DTO_1.getId().toString());
    public static final Classgroup CLASSGROUP_1 = new Classgroup(CREATE_CLASSGROUP_DTO_1.getName(), COURSE_1);
    public static final ClassgroupDTO CLASSGROUP_DTO_1 = new ClassgroupDTO("ID", CLASSGROUP_1.getName(), COURSE_DTO_1);
    public static Map<String, Object> getExpectedMapForFullyInvalidCreateClassgroupDTO() {
        Map<String, Object> mapExpected = new HashMap<>();
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("name", "The name of the classgroup must be between 2 and 255 characters");
        errorsMap.put("courseId", "The id of the course must be 36 characters long");
        mapExpected.put("message", "Following validation error(s) occurred on /classgroups");
        mapExpected.put("errors", errorsMap);
        return mapExpected;
    }
}
