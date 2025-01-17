package com.switchfully.lmstrapeziumbackend.module;

import com.switchfully.lmstrapeziumbackend.TestConstants;
import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleWithCoursesDTO;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.utility.KeycloakTestingUtility;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class ModuleControllerTest {
    private static final String URI = "http://localhost";

    @LocalServerPort
    int port;

    @PersistenceContext
    @Autowired
    EntityManager entityManager;

    @Autowired
    ModuleMapper moduleMapper;

    @Autowired
    KeycloakTestingUtility keycloakTestingUtility;

    @Test
    @DisplayName("Given connected user, getting all the modules will return a list of module DTO")
    void givenConnectedUser_whenGettingAllModules_thenReturnListOfModuleDTO() {
        String TOKEN_COACH = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);

        List<ModuleDTO> actualModuleDTOList = RestAssured
                .given()
                .baseUri(URI)
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_COACH)
                .when()
                .get("/modules")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", ModuleDTO.class);
        List<Module> expectedModuleList = entityManager.createQuery("select m from Module m", Module.class).getResultList();
        assertThat(actualModuleDTOList).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedModuleList);
    }

    @Test
    @Transactional
    @DisplayName("Given connected user, creating a module will return the module DTO")
    void givenConnectedUser_whenCreatingModule_returnModuleDTO() {
        String TOKEN_COACH = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);

        ModuleDTO actualModuleDTO = RestAssured
                .given()
                .baseUri(URI)
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_COACH)
                .accept(JSON)
                .contentType(JSON)
                .body(TestConstants.CREATE_MODULE_DTO_1)
                .when()
                .post("/modules")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ModuleDTO.class);

        Module dbModule = entityManager.createQuery("select m from Module m where m.id= :id", Module.class)
                .setParameter("id", actualModuleDTO.id())
                .getSingleResult();

        assertThat(actualModuleDTO)
                .usingRecursiveComparison()
                .isEqualTo(dbModule);
    }

    @Test
    @DisplayName("Getting courses relating to a module given module is in db will return list of courses")
    void givenAmoduleIsInTheDatabaseWhenProvidingAModuleIdThenAListOfCoursesIsReturned() {
        String TOKEN_COACH = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);

        UUID moduleId = UUID.fromString("e0e8b090-df45-11ec-9d64-0242ac120002");
        //When
        ModuleWithCoursesDTO moduleWithCoursesDTOActual = RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_COACH)
                .when()
                .get("/modules/" + moduleId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ModuleWithCoursesDTO.class);
        //Then

        List<Course> coursesToCheck = entityManager.createQuery("select distinct c from Course c  join c.modules m where m.id = :id", Course.class).setParameter("id", moduleId).getResultList();
        Assertions.assertThat(moduleWithCoursesDTOActual).extracting("courses")
                .usingRecursiveComparison()
                .isEqualTo(coursesToCheck);
    }
}