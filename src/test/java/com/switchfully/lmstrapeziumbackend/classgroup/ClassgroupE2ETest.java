package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.TestConstants;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.course.CourseMapper;
import com.switchfully.lmstrapeziumbackend.course.CourseService;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
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
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

import static io.restassured.http.ContentType.JSON;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ClassgroupE2ETest {
    @LocalServerPort
    private int port;
    @Autowired
    private CourseService courseService;

    @Test
    void givenAValidCreateClassgroupDTO_thenWillReturnAClassgroupDTO() {
        //When
        ClassgroupDTO classgroupCreated = RestAssured
                .given()
                .port(port)
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
        CreateClassgroupDTO invalidCreateClassgroupDTO = new CreateClassgroupDTO("A", "", List.of());
        //When
        Response response = RestAssured
                .given()
                .port(port)
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
    @DisplayName("Trying to create a Course with invalid data should not work")
    void givenACreateCourseDTOContainingANonCoachId_thenWillReturnAnError() {
        //Given
        CreateClassgroupDTO invalidCreateClassgroupDTO = new CreateClassgroupDTO("Best Classgroup", TestConstants.COURSE_DTO_1.id().toString(), List.of(TestConstants.TESTING_STUDENT_ID));
        //When
        String response = RestAssured
                .given()
                .port(port)
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
}
