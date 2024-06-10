package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.TestConstants;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.security.KeycloakService;
import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import com.switchfully.lmstrapeziumbackend.utility.KeycloakTestingUtility;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class StudentControllerTest {
    public static final String CREATE_STUDENT_EMAIL = "email@email.email";
    public static final String CREATE_STUDENT_DISPLAY_NAME = "created_student";

    private static final String URI = "http://localhost";
    public static final String CREATE_STUDENT_PASSWORD = "password-created";

    @LocalServerPort
    int port;

    @Autowired
    EntityManager entityManager;
    @Autowired
    KeycloakService keycloakService;
    @Autowired
    KeycloakTestingUtility keycloakTestingUtility;

    public static UUID userId = null;

    @Test
    public void givenCreateStudentDTO_whenCreateStudent_thenReturnStudentDTO() {
        CreateStudentDTO createStudentDTO = new CreateStudentDTO(CREATE_STUDENT_EMAIL, CREATE_STUDENT_PASSWORD, CREATE_STUDENT_DISPLAY_NAME);
        StudentDTO studentDTO = new StudentDTO(UUID.randomUUID(), CREATE_STUDENT_EMAIL, CREATE_STUDENT_DISPLAY_NAME);
        User student = new User(UUID.randomUUID(), CREATE_STUDENT_EMAIL, CREATE_STUDENT_DISPLAY_NAME, UserRole.STUDENT);

        StudentDTO returnedStudentDTO = RestAssured
                .given()
                .baseUri(URI)
                .port(port)
                .contentType(ContentType.JSON)
                .body(createStudentDTO)
                .when()
                .post("/students")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(StudentDTO.class);

        userId = returnedStudentDTO.id();

        User dbStudent = entityManager.createQuery("select s from User s LEFT JOIN FETCH s.classgroups where s.email = :email", User.class)
                .setParameter("email", CREATE_STUDENT_EMAIL)
                .getSingleResult();

        Assertions.assertThat(returnedStudentDTO)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*id")
                .isEqualTo(studentDTO);

        Assertions.assertThat(dbStudent)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*id")
                .isEqualTo(student);
    }

    @Test
    void givenAExistingId_whenGetAStudentById_thenReturnAStudentDTO() {
        String token = keycloakTestingUtility.getTokenFromTestingUser(UserRole.STUDENT);

        StudentDTO studentDTO =
                RestAssured
                        .given()
                        .contentType(ContentType.JSON)
                        .when()
                        .port(port)
                        .header("Authorization", "Bearer " + token)
                        .get("/students/" + TestConstants.TESTING_STUDENT_ID)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(StudentDTO.class);


        Assertions.assertThat(studentDTO)
                .usingRecursiveComparison()
                .isEqualTo(TestConstants.TESTING_STUDENT_DTO);
    }

    @Test
    void givenAExistingId_whenGetAStudentByIdWithWrongIdInPath_thenThrowError() {
        String token = keycloakTestingUtility.getTokenFromTestingUser(UserRole.STUDENT);

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .when()
            .port(port)
            .header("Authorization", "Bearer " + token)
            .get("/students/" + UUID.randomUUID())
            .then()
            .assertThat()
            .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Getting the classgroups of a student with correct id and authentication should return a List of 0 or 1 ClassgroupDTO")
    void givenCorrectStudentIdAndAuthentication_thenShouldReturnListOfClassgroupDTO() {
        //Given
        String TOKEN_STUDENT = keycloakTestingUtility.getTokenFromTestingUser(UserRole.STUDENT);
        //When
        List<ClassgroupDTO> classgroupOfStudent = RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_STUDENT)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .get("/users/" + TestConstants.TESTING_STUDENT_ID + "/classgroups")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", ClassgroupDTO.class);
        //Then
        Assertions
                .assertThat(classgroupOfStudent)
                .size()
                .isLessThanOrEqualTo(1);
    }

    @Test
    @DisplayName("Getting the classgroups of a student with wrong id and authentication should throw a 403 error")
    void givenWrongStudentIdAndCorrectAuthentication_thenShouldReturnA403() {
        //Given
        String TOKEN_STUDENT = keycloakTestingUtility.getTokenFromTestingUser(UserRole.STUDENT);
        //When
        RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_STUDENT)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .get("/users/c3be0437-d6cf-4cdc-9c92-26f002f8c55e/classgroups")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Getting the classgroups of a coach with correct id and authentication should return a List of 0 or infinite ClassgroupDTO")
    void givenCorrectCorrectIdAndAuthentication_thenShouldReturnListOfClassgroupDTO() {
        //Given
        String TOKEN_COACH = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);
        //When
        List<ClassgroupDTO> classgroupOfCoach = RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_COACH)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .get("/users/" + TestConstants.TESTING_COACH.getId() + "/classgroups")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", ClassgroupDTO.class);
        //Then
        Assertions
                .assertThat(classgroupOfCoach)
                .contains(TestConstants.CLASSGROUP_DTO_1);
    }

    @Test
    @DisplayName("Getting the classgroups of a coach with wrong id and authentication should throw a 403 error")
    void givenWrongCoachIdAndCorrectAuthentication_thenShouldReturnA403() {
        //Given
        String TOKEN_COACH = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);
        //When
        RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_COACH)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .get("/users/c3be0437-d6cf-4cdc-9c92-26f002f8c55e/classgroups")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @AfterAll
    public void afterAllTests() {
        keycloakService.deleteUserFromKeycloak(userId);
    }
}
