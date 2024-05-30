package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.security.KeycloakService;
import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentControllerTest {
    public static final String CREATE_STUDENT_EMAIL = "email@email.email";
    public static final String CREATE_STUDENT_DISPLAY_NAME = "created_student";

    private static final String URI = "http://localhost";
    public static final String CREATE_STUDENT_PASSWORD = "password-created";

    @LocalServerPort
    int localPort;

    @Autowired
    EntityManager entityManager;
    @Autowired
    KeycloakService keycloakService;

    public static UUID userId = null;

    @Test
    void testIt() {
        System.out.println("test");
    }

    @Test
    public void givenCreateStudentDTO_whenCreateStudent_thenReturnStudentDTO() {
        CreateStudentDTO createStudentDTO = new CreateStudentDTO(CREATE_STUDENT_EMAIL, CREATE_STUDENT_PASSWORD, CREATE_STUDENT_DISPLAY_NAME);
        StudentDTO studentDTO = new StudentDTO(UUID.randomUUID(), CREATE_STUDENT_EMAIL, CREATE_STUDENT_DISPLAY_NAME);
        User student = new User(UUID.randomUUID(), CREATE_STUDENT_EMAIL, CREATE_STUDENT_DISPLAY_NAME, UserRole.STUDENT);

        StudentDTO returnedStudentDTO = RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
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

    @AfterAll
    public void afterAllTests() {
        keycloakService.deleteUserFromKeycloak(userId);
    }
}
