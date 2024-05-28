package com.switchfully.lmstrapeziumbackend;

import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;

import java.util.HashMap;
import java.util.Map;

public class TestConstants {
    public static final CreateCourseDTO CREATE_COURSE_DTO_1 = new CreateCourseDTO("Java@Fin", "The Java@Fin course");
    public static final Course COURSE_1 = new Course(CREATE_COURSE_DTO_1.getName(), CREATE_COURSE_DTO_1.getDescription());
    public static final CourseDTO COURSE_DTO_1 = new CourseDTO("ID", COURSE_1.getName(), COURSE_1.getDescription());
    public static Map<String, Object> getExpectedMapForFullyInvalidCreateCourseDTO() {
        Map<String, Object> mapExpected = new HashMap<>();
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("name", "Name must be between 2 and 255 characters");
        mapExpected.put("message", "Following validation error(s) occurred on /courses");
        mapExpected.put("errors", errorsMap);
        return mapExpected;
    }
}
