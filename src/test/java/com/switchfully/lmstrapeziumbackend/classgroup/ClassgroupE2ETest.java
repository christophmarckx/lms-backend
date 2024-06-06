package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.TestConstants;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.course.CourseService;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.utility.KeycloakTestingUtility;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ClassgroupE2ETest {
    @LocalServerPort
    private int port;
    @Autowired
    private CourseService courseService;
    @Autowired
    EntityManager entityManager;

    @Autowired
    KeycloakTestingUtility keycloakTestingUtility;

    @Test
    @DisplayName("Given a valid CreateClassgroupDTO, it should create the Classgroup and return a ClassgroupDTO")
    void givenAValidCreateClassgroupDTO_thenWillReturnAClassgroupDTO() {
        //Given
        String TOKEN_COACH = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);
        //When
        ClassgroupDTO classgroupCreated = RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_COACH)
                .accept(JSON)
                .contentType(JSON)
                .body(TestConstants.CREATE_CLASSGROUP_DTO_1)
                .when()
                .post("/classgroups")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ClassgroupDTO.class);
        //Then
        Assertions
                .assertThat(classgroupCreated)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*id")
                .isEqualTo(TestConstants.CLASSGROUP_DTO_1);
    }

    @Test
    @DisplayName("Trying to create a Course with invalid data should not work")
    void givenAFullyInvalidCreateCourseDTO_thenWillReturnAListOfErrors() {
        //Given
        String TOKEN_COACH = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);
        CreateClassgroupDTO invalidCreateClassgroupDTO = new CreateClassgroupDTO("A", null, List.of());
        //When
        Response response = RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_COACH)
                .accept(JSON)
                .contentType(JSON)
                .body(invalidCreateClassgroupDTO)
                .when()
                .post("/classgroups")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .response();
        Map<String, Object> mapReturned = response.as(Map.class);
        //Then
        Assertions.assertThat(mapReturned).containsExactlyInAnyOrderEntriesOf(TestConstants.getExpectedMapForFullyInvalidCreateClassgroupDTO());
    }

    @Test
    @DisplayName("Trying to create a Course with valid data but a student instead of a coach, then should not work")
    void givenACreateCourseDTOContainingANonCoachId_thenWillReturnAnError() {
        //Given
        String TOKEN_COACH = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);
        CreateClassgroupDTO invalidCreateClassgroupDTO = new CreateClassgroupDTO("Best Classgroup", TestConstants.COURSE_DTO_1.id(), List.of(TestConstants.TESTING_STUDENT_ID));
        //When
        String response = RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_COACH)
                .accept(JSON)
                .contentType(JSON)
                .body(invalidCreateClassgroupDTO)
                .when()
                .post("/classgroups")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .asString();
        //Then
        Assertions.assertThat(response).isEqualTo("You can only give Coaches to create a classgroup");
    }

    //BAD test sucks a bit. We cannot integration test edge cases because we need authentication to access the service. plz help michael
    @Test
    @DisplayName("Adding a student to a classgroup if they are already on another classgroup will override their current re")
    void givenStudentAlreadyRegistered_whenAddingStudentToOtherClassGroup_ThenClassgroupIsChanged() {
        String TOKEN_STUDENT = keycloakTestingUtility.getTokenFromTestingUser(UserRole.STUDENT);
        //When
        RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_STUDENT)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .put("/classgroups/" + TestConstants.CLASSGROUP_DTO_1.id() + "/add-student")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        //Then
        User userToCheck = entityManager.createQuery("SELECT u from User u where u.id = :id", User.class)
                .setParameter("id", TestConstants.TESTING_STUDENT_DTO.id())
                .getSingleResult();

        Classgroup classGroupToVerifyStudentOf = entityManager.createQuery("SELECT cg from Classgroup cg WHERE cg.id = :id", Classgroup.class)
                .setParameter("id", TestConstants.CLASSGROUP_DTO_1.id())
                .getSingleResult();

        Assertions.assertThat(classGroupToVerifyStudentOf.getUsers()).extracting("id").containsOnlyOnce(userToCheck.getId());
        Assertions.assertThat(userToCheck.getClassgroups()).extracting("id").containsExactly(classGroupToVerifyStudentOf.getId());

    }
}
