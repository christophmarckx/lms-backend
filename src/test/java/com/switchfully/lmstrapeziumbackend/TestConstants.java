package com.switchfully.lmstrapeziumbackend;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabWithModuleDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CreateCodelabDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.UpdateCodelabDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.dto.*;
import com.switchfully.lmstrapeziumbackend.module.Module;
import com.switchfully.lmstrapeziumbackend.module.dto.CreateModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleWithCodelabsDTO;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;

import java.util.*;

public class TestConstants {
    //****************** STUDENTS ******************//

    public static final UUID TESTING_STUDENT_ID = UUID.fromString("d992d902-4953-4b9a-9a88-493d446c8ee0");
    public static final StudentDTO TESTING_STUDENT_DTO = new StudentDTO(TESTING_STUDENT_ID, "testing@student.com", "Super Testing Student");
    private static final User TESTING_STUDENT = new User(TESTING_STUDENT_ID, TESTING_STUDENT_DTO.email(), TESTING_STUDENT_DTO.displayName(), UserRole.STUDENT);
    public static final UUID STUDENT_ID = UUID.fromString("e0e8b085-df45-11ec-9d64-0242ac120002");

    public static final User STUDENT_JOHN_DOE = new User(UUID.fromString("e0e8b085-df45-11ec-9d64-0242ac120002"), "john.doe@example.com", "John Doe", UserRole.STUDENT);
    public static final StudentDTO STUDENT_DTO = new StudentDTO(UUID.fromString("e0e8b085-df45-11ec-9d64-0242ac120002"), "john.doe@example.com", "John Doe");

    //****************** MODULES ******************//
    public static final CreateModuleDTO CREATE_MODULE_DTO_1 = new CreateModuleDTO("Intro to Programming", null);
    public static final Module MODULE_1 = new Module(CREATE_MODULE_DTO_1.name(), null);
    public static final ModuleDTO MODULE_DTO_1 = new ModuleDTO(UUID.fromString("e0e8b090-df45-11ec-9d64-0242ac120002"), CREATE_MODULE_DTO_1.name(), null);
    public static final CreateModuleDTO CREATE_MODULE_DTO_2 = new CreateModuleDTO("Algorithms", UUID.fromString("e0e8b090-df45-11ec-9d64-0242ac120002"));
    public static final Module MODULE_2 = new Module(CREATE_MODULE_DTO_2.name(), MODULE_1);
    public static final ModuleDTO MODULE_DTO_2 = new ModuleDTO(UUID.fromString("e0e8b091-df45-11ec-9d64-0242ac120002"), CREATE_MODULE_DTO_2.name(), MODULE_DTO_1);
    public static final CreateModuleDTO CREATE_MODULE_DTO_3 = new CreateModuleDTO("Data Structures", UUID.fromString("e0e8b090-df45-11ec-9d64-0242ac120002"));
    public static final Module MODULE_3 = new Module(CREATE_MODULE_DTO_3.name(), MODULE_1);
    public static final ModuleDTO MODULE_DTO_3 = new ModuleDTO(UUID.fromString("e0e8b092-df45-11ec-9d64-0242ac120002"), CREATE_MODULE_DTO_3.name(), MODULE_DTO_1);

    //****************** COURSES ******************//

    public static final CreateCourseDTO CREATE_COURSE_DTO_1 = new CreateCourseDTO("Java@Fin", "The Java@Fin course", List.of(UUID.fromString("e0e8b090-df45-11ec-9d64-0242ac120002"), UUID.fromString("e0e8b091-df45-11ec-9d64-0242ac120002")));
    public static final Course COURSE_1 = new Course(CREATE_COURSE_DTO_1.name(), CREATE_COURSE_DTO_1.description(), List.of(MODULE_1, MODULE_2));
    public static final CourseDTO COURSE_DTO_1 = new CourseDTO(UUID.fromString("f953c154-36f2-4b79-8992-b6f5d4dd24a9"), COURSE_1.getName(), COURSE_1.getDescription(), List.of(MODULE_DTO_1, MODULE_DTO_2));
    public static final CourseSummaryDTO COURSE_SUMMARY_DTO_1 = new CourseSummaryDTO(UUID.fromString("f953c154-36f2-4b79-8992-b6f5d4dd24a9"), COURSE_1.getName(), COURSE_1.getDescription());
    public static final UpdateCourseDTO UPDATED_COURSE_1 = new UpdateCourseDTO("NEW UPDATED NAME", "", new ArrayList<>());

    //****************** CODELABS ******************//
    public static final CodelabDTO CODELAB_DTO_1 = new CodelabDTO(UUID.fromString("e0e8b096-df45-11ec-9d64-0242ac120002"), "Hello World Lab", "First Hello World Lab");
    public static final CodelabDTO CODELAB_DTO_2 = new CodelabDTO(UUID.fromString("e0e8b097-df45-11ec-9d64-0242ac120002"), "Sorting Algorithms Lab", "Lab about complex algorithm for sorting (Like bubble sort, the best)");
    public static final CodelabDTO CODELAB_DTO_3 = new CodelabDTO(UUID.fromString("e0e8b098-df45-11ec-9d64-0242ac120002"), "Binary Trees Lab", "You love tree ? You love binary ? You will love Binary Tree !");
    public static final UpdateCodelabDTO UPDATED_CODELAB_1 = new UpdateCodelabDTO("NEW UPDATED NAME", "", UUID.fromString("e0e8b090-df45-11ec-9d64-0242ac120002"));
    public static final CodelabWithModuleDTO CODELAB_WITH_MODULE_DTO_1 = new CodelabWithModuleDTO(CODELAB_DTO_1.id(), CODELAB_DTO_1.name(), CODELAB_DTO_1.description(), MODULE_DTO_1);

    public static final ModuleWithCodelabsDTO MODULE_WITH_CODELABS_DTO_2 = new ModuleWithCodelabsDTO(MODULE_DTO_2.id(), MODULE_DTO_2.name(), List.of(), List.of(CODELAB_DTO_2));
    public static final ModuleWithCodelabsDTO MODULE_WITH_CODELABS_DTO_3 = new ModuleWithCodelabsDTO(MODULE_DTO_3.id(), MODULE_DTO_3.name(), List.of(), List.of(CODELAB_DTO_3));
    public static final ModuleWithCodelabsDTO MODULE_WITH_CODELABS_DTO_1 = new ModuleWithCodelabsDTO(MODULE_DTO_1.id(), MODULE_DTO_1.name(), List.of(MODULE_WITH_CODELABS_DTO_2, MODULE_WITH_CODELABS_DTO_3), List.of(CODELAB_DTO_1));
    public static final CourseWithModulesDTO COURSE_WITH_MODULES_DTO_1 = new CourseWithModulesDTO(COURSE_DTO_1.id(), COURSE_DTO_1.name(), COURSE_DTO_1.description(), List.of(MODULE_WITH_CODELABS_DTO_1));


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
        mapExpected.put("message", "Following validation error(s) occurred on /courses/" + COURSE_DTO_1.id());
        mapExpected.put("errors", errorsMap);
        return mapExpected;
    }

    //****************** COACHS ******************//

    public static final User TESTING_COACH = new User(UUID.fromString("e0e8b087-df45-11ec-9d64-0242ac120002"), "admin@example.com", "Admin User", UserRole.COACH);

    //****************** CLASSGROUPS ******************//

    public static final CreateClassgroupDTO CREATE_CLASSGROUP_DTO_1 = new CreateClassgroupDTO("CS101 Group A", COURSE_DTO_1.id(), List.of(TESTING_COACH.getId()));
    public static final Classgroup CLASSGROUP_1 = new Classgroup(CREATE_CLASSGROUP_DTO_1.name(), COURSE_1, List.of(TESTING_COACH));
    public static final ClassgroupDTO CLASSGROUP_DTO_1 = new ClassgroupDTO(UUID.fromString("e0e8b082-df45-11ec-9d64-0242ac120002"), CLASSGROUP_1.getName(), COURSE_SUMMARY_DTO_1);

    public static Map<String, Object> getExpectedMapForFullyInvalidCreateClassgroupDTO() {
        Map<String, Object> mapExpected = new HashMap<>();
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("name", "The name of the classgroup must be between 2 and 255 characters");
        errorsMap.put("courseId", "The id of the course must be 36 characters long");
        errorsMap.put("coaches", "Provide at least one coach to create the Classgroup");
        mapExpected.put("message", "Following validation error(s) occurred on /classgroups");
        mapExpected.put("errors", errorsMap);
        return mapExpected;
    }

    //****************** CODELABS 2.0 ******************//
    public static final String CODELAB_NAME = "Loops in Java";
    public static final String CODELAB_DESCRIPTION = "Loops go zoom go zoom go zoom";
    public static final UUID CODELAB_PARENT_MODULE_ID = UUID.fromString("e0e8b090-df45-11ec-9d64-0242ac120002");
    public static final CreateCodelabDTO CREATE_CODELAB_DTO_CORRECT_DATA = new CreateCodelabDTO(
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
        errorsMap.put("parentModuleId", "Parent module is required for Codelab creation.");
        mapExpected.put("message", "Following validation error(s) occurred on /codelabs");
        mapExpected.put("errors", errorsMap);
        return mapExpected;
    }
}
