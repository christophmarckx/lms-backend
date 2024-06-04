package com.switchfully.lmstrapeziumbackend.codelab;

import com.switchfully.lmstrapeziumbackend.TestConstants;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CodelabControllerTest {
    @LocalServerPort
    private int port;

    @PersistenceContext
    @Autowired
    EntityManager entityManager;

    private final static String URI = "http://localhost/codelabs";


    @Test
    @DisplayName("Trying to create a Codelab with correct data should work")
    void givenAValidCodelabDTO_thenShouldReturnACourseDTO() {
        //When
        CodelabDTO actualCreatedCodelab = RestAssured
                .given()
                .port(port)
                .accept(JSON)
                .contentType(JSON)
                .body(TestConstants.CREATE_CODELAB_DTO_CORRECT_DATA)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CodelabDTO.class);
        //Then
        Codelab expected = entityManager.createQuery("select cl from Codelab cl where cl.id = :id", Codelab.class).setParameter("id", actualCreatedCodelab.id()).getSingleResult();

        assertThat(actualCreatedCodelab)
                .usingRecursiveComparison()
                .ignoringFields("moduleDTO")
                .isEqualTo(expected);

        assertThat(actualCreatedCodelab).extracting("moduleDTO")
                .usingRecursiveComparison()
                .isEqualTo(expected.getModule());
    }

    @Test
    @DisplayName("Trying to update a course name with invalid data should not work")
    void givenAFullyInvalidUpdateCourseDTO_thenWillReturnAListOfErrors() {
        //Given
        //When
        Response response = RestAssured
                .given()
                .port(port)
                .accept(JSON)
                .contentType(JSON)
                .body(TestConstants.CREATE_CODELAB_DTO_1_INCORRECT_DATA)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .response();
        Map<String, Object> mapReturned = response.as(Map.class);
        //Then
        assertThat(mapReturned)
                .containsExactlyInAnyOrderEntriesOf(TestConstants
                        .getExpectedMapForFullyInvalidCreateCodelabDTO());
    }
}