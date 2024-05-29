package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.TestConstants;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class CourseE2ETest {
    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Trying to create a Course with invalid data should not work")
    void givenAFullyInvalidCreateCourseDTO_thenWillReturnAListOfErrors() {
        //Given
        CreateCourseDTO invalidCreateCourseDTO = new CreateCourseDTO("A", "");
        //When
        Response response = RestAssured
                .given()
                .port(port)
                .accept(JSON)
                .contentType(JSON)
                .body(invalidCreateCourseDTO)
                .when()
                .post("/courses")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .response();
        Map<String, Object> mapReturned = response.as(Map.class);
        //Then
        Assertions.assertThat(mapReturned).containsExactlyInAnyOrderEntriesOf(TestConstants.getExpectedMapForFullyInvalidCreateCourseDTO());
    }

    @Test
    @DisplayName("Trying to create a Course with correct data should work")
    void givenAValidCreateCourseDTO_thenShouldReturnACourseDTO() {
        //When
        CourseDTO courseCreated = RestAssured
                .given()
                .port(port)
                .accept(JSON)
                .contentType(JSON)
                .body(TestConstants.CREATE_COURSE_DTO_1)
                .when()
                .post("/courses")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CourseDTO.class);
        //Then
        Assertions
                .assertThat(courseCreated)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*id")
                .isEqualTo(TestConstants.COURSE_DTO_1);
    }

}
