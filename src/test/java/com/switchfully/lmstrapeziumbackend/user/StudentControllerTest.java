package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentControllerTest {
    public static final String CREATE_STUDENT_EMAIL = "email@email.email";
    public static final String CREATE_STUDENT_DISPLAY_NAME = "created_student";

    private static final String URI = "http://localhost";

    @LocalServerPort
    int localPort;

    @Autowired
    EntityManager entityManager;

    @Test
    public void givenCreateStudentDTO_whenCreateStudent_thenReturnStudentDTO() {
        CreateStudentDTO createStudentDTO = new CreateStudentDTO(CREATE_STUDENT_EMAIL, CREATE_STUDENT_DISPLAY_NAME);

        StudentDTO studentDTO = RestAssured
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

        Assertions.assertThat(studentDTO)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*id")
                .isEqualTo(new StudentDTO(UUID.randomUUID(), CREATE_STUDENT_EMAIL, CREATE_STUDENT_DISPLAY_NAME));
    }
}
