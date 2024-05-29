package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.TestConstants;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.course.CourseMapper;
import com.switchfully.lmstrapeziumbackend.course.CourseService;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.http.ContentType.JSON;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class ClassgroupE2ETest {
    @LocalServerPort
    private int port;
    @Autowired
    private CourseService courseService;

    @Test
    @Sql(scripts = {"/addData.sql"})
    void givenAValidCreateClassgroupDTO_thenWillReturnAClassgroupDTO() {
        //When
        ClassgroupDTO courseCreated = RestAssured
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
        Assertions.assertThat(courseCreated).isEqualTo(TestConstants.CLASSGROUP_DTO_1);
    }
}
