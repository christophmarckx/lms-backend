package com.switchfully.lmstrapeziumbackend;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CreateCodelabDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.UpdateCourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestConstants {

    //****************** COURSES ******************//

    public static final CreateCourseDTO CREATE_COURSE_DTO_1 = new CreateCourseDTO("Java@Fin", "The Java@Fin course");
    public static final Course COURSE_1 = new Course(CREATE_COURSE_DTO_1.getName(), CREATE_COURSE_DTO_1.getDescription());
    public static final CourseDTO COURSE_DTO_1 = new CourseDTO(UUID.fromString("f953c154-36f2-4b79-8992-b6f5d4dd24a9"), COURSE_1.getName(), COURSE_1.getDescription());
    public static final UpdateCourseDTO UPDATED_COURSE_1 = new UpdateCourseDTO("NEW UPDATED NAME");

    public static Map<String, Object> getExpectedMapForFullyInvalidCreateCourseDTO() {
        Map<String, Object> mapExpected = new HashMap<>();
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("name", "Name must be between 2 and 255 characters");
        mapExpected.put("message", "Following validation error(s) occurred on /courses");
        mapExpected.put("errors", errorsMap);
        return mapExpected;
    }

    public static Map<String, Object> getExpectedMapForFullyInvalidUpdateCourseDTO() {
        Map<String, Object> mapExpected = new HashMap<>();
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("name", "Name must be between 2 and 255 characters");
        mapExpected.put("message", "Following validation error(s) occurred on /courses/" + COURSE_DTO_1.getId());
        mapExpected.put("errors", errorsMap);
        return mapExpected;
    }

    //****************** COACHS ******************//

    public static final User TESTING_COACH = new User(UUID.fromString("edc88e86-3185-4354-8b21-9ae9b4057b09"), "test_coach@coach.com", "test_coach", UserRole.COACH);

    //****************** CLASSGROUPS ******************//

    public static final CreateClassgroupDTO CREATE_CLASSGROUP_DTO_1 = new CreateClassgroupDTO("Java-2024-02", COURSE_DTO_1.getId().toString(), List.of(TESTING_COACH.getId()));
    public static final Classgroup CLASSGROUP_1 = new Classgroup(CREATE_CLASSGROUP_DTO_1.getName(), COURSE_1);
    public static final ClassgroupDTO CLASSGROUP_DTO_1 = new ClassgroupDTO("ID", CLASSGROUP_1.getName(), COURSE_DTO_1);

    public static Map<String, Object> getExpectedMapForFullyInvalidCreateClassgroupDTO() {
        Map<String, Object> mapExpected = new HashMap<>();
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("name", "The name of the classgroup must be between 2 and 255 characters");
        errorsMap.put("courseId", "The id of the course must be 36 characters long");
        errorsMap.put("coachs", "Provide at least one coach to create the Classgroup");
        mapExpected.put("message", "Following validation error(s) occurred on /classgroups");
        mapExpected.put("errors", errorsMap);
        return mapExpected;
    }

    //****************** CODELABS ******************//
    public static final String CODELAB_NAME = "Loops in Java";
    public static final String CODELAB_DESCRIPTION = "Loops go zoom go zoom go zoom";
    public static final UUID CODELAB_PARENT_MODULE_ID = UUID.fromString("e0e8b090-df45-11ec-9d64-0242ac120002");
    public static final CreateCodelabDTO CREATE_CODELAB_DTO_1_CORRECT_DATA = new CreateCodelabDTO(
            CODELAB_NAME,
            CODELAB_DESCRIPTION,
            CODELAB_PARENT_MODULE_ID
    );

    public static final CreateCodelabDTO CREATE_CODELAB_DTO_1_INCORRECT_DATA = new CreateCodelabDTO(
            null,
            CODELAB_DESCRIPTION,
            null
    );

    public static Map<String, Object> getExpectedMapForFullyInvalidCreateCodelabDTO() {
        Map<String, Object> mapExpected = new HashMap<>();
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("name", "Name is required for Codelab creation.");
        errorsMap.put("parentModule", "Parent module is required for Codelab creation.");
        mapExpected.put("message", "Following validation error(s) occurred on /codelabs");
        mapExpected.put("errors", errorsMap);
        return mapExpected;
    }

    //****************** STUDENTS ******************//

    public static final UUID TESTING_STUDENT_ID = UUID.fromString("d992d902-4953-4b9a-9a88-493d446c8ee0");
    public static final StudentDTO TESTING_STUDENT_DTO = new StudentDTO(TESTING_STUDENT_ID, "testing@student.com", "Super Testing Student");
    public static final UUID STUDENT_ID = UUID.fromString("e0e8b085-df45-11ec-9d64-0242ac120002");
    public static final StudentDTO STUDENT_DTO = new StudentDTO(UUID.fromString("e0e8b085-df45-11ec-9d64-0242ac120002"), "john.doe@example.com","John Doe");


}
