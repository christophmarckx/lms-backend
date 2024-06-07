package com.switchfully.lmstrapeziumbackend.codelab;

import com.switchfully.lmstrapeziumbackend.TestConstants;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabWithModuleDTO;
import com.switchfully.lmstrapeziumbackend.security.KeycloakService;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.utility.KeycloakTestingUtility;
import io.restassured.RestAssured;
import io.restassured.response.Response;
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

import java.util.List;
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

    @Autowired
    KeycloakService keycloakService;

    @Autowired
    KeycloakTestingUtility keycloakTestingUtility;

    private final static String URI = "http://localhost/codelabs";

    @Test
    @DisplayName("Trying to create a Codelab with correct data should work")
    void givenAValidCodelabDTO_thenShouldReturnACourseDTO() {
        //Given
        String TOKEN_COACH = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);
        //When
        CodelabDTO actualCreatedCodelab = RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_COACH)
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
                .ignoringFields("module")
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Trying to update a codelab name with invalid data should not work")
    void givenAFullyInvalidUpdateCodelabDTO_thenWillReturnAListOfErrors() {
        //Given
        String TOKEN_COACH = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);
        //When
        Response response = RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_COACH)
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

    @Test
    @DisplayName("Getting all codelabs should return a list of codelabDTO")
    void givenAuthenticatedUserWhenGettingAllCodelabsThenReturnListOfCodelabDTO() {
        //Given
        String TOKEN_STUDENT = keycloakTestingUtility.getTokenFromTestingUser(UserRole.STUDENT);
        //When
        List<CodelabDTO> codelabDTOActual = RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_STUDENT)
                .when()
                .get("/codelabs")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", CodelabDTO.class);
        //Then
        List<Codelab> codelabs = entityManager.createQuery("SELECT c FROM Codelab c", Codelab.class).getResultList();
        assertThat(codelabDTOActual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("module").isEqualTo(codelabs);
    }

    @Test
    @DisplayName("Updating the name of a course should work")
    void givenAValidUpdateCodelabDTO_thenShouldReturnACodelabDTO() {

        String TOKEN_COACH = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);
        //When
        CodelabDTO codelabUpdated = RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_COACH)
                .accept(JSON)
                .contentType(JSON)
                .body(TestConstants.UPDATED_CODELAB_1)
                .when()
                .put("/codelabs/" + TestConstants.CODELAB_DTO_1.id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CodelabDTO.class);
        //Then
        Assertions.assertThat(codelabUpdated.name()).isEqualTo(TestConstants.UPDATED_CODELAB_1.name());
        Assertions.assertThat(codelabUpdated.description()).isEqualTo(TestConstants.UPDATED_CODELAB_1.description());
    }


    @Test
    @DisplayName("Getting a codelab by Id should return correct codelab with module")
    void givenCodelabId_thenShouldReturnACodelabWithModuleDTO() {
        //Given
        String TOKEN_COACH = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);

        //When
        CodelabWithModuleDTO codelabWithModuleDTO = RestAssured
                .given()
                .port(port)
                .header("Authorization", "Bearer " + TOKEN_COACH)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .get("/codelabs/" + TestConstants.CODELAB_DTO_1.id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CodelabWithModuleDTO.class);
        //Then
        Assertions.assertThat(codelabWithModuleDTO).isEqualTo(TestConstants.CODELAB_WITH_MODULE_DTO_1);
    }
}