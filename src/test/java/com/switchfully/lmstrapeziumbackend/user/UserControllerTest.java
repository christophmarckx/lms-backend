package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.TestConstants;
import com.switchfully.lmstrapeziumbackend.security.KeycloakService;
import com.switchfully.lmstrapeziumbackend.user.dto.AuthenticatedUserDTO;
import com.switchfully.lmstrapeziumbackend.utility.KeycloakTestingUtility;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
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

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserControllerTest {
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
    void givenAExistingId_whenGetAuthenticatedUser_thenReturnAuthenticatedUserDTO() {
        String token = keycloakTestingUtility.getTokenFromTestingUser(UserRole.STUDENT);

        AuthenticatedUserDTO authenticatedUserDto =
                RestAssured
                        .given()
                        .contentType(ContentType.JSON)
                        .when()
                        .port(port)
                        .header("Authorization", "Bearer " + token)
                        .get("/users/" + TestConstants.TESTING_STUDENT_ID)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(AuthenticatedUserDTO.class);


        Assertions.assertThat(authenticatedUserDto)
                .isEqualTo(TestConstants.AUTHENTICATED_STUDENT_DTO);
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
                .get("/users/" + UUID.randomUUID())
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void givenAExistingId_whenGetAuthenticatedCoach_thenReturnAuthenticatedUserDTO() {
        String token = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);

        AuthenticatedUserDTO authenticatedUserDto =
                RestAssured
                        .given()
                        .contentType(ContentType.JSON)
                        .when()
                        .port(port)
                        .header("Authorization", "Bearer " + token)
                        .get("/users/" + TestConstants.TESTING_COACH.getId())
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(AuthenticatedUserDTO.class);


        Assertions.assertThat(authenticatedUserDto)
                .isEqualTo(TestConstants.AUTHENTICATED_COACH_DTO);
    }

    @Test
    @DisplayName("Given different authentication and userId, when user is coach, then retrieving user by id will return authenticated userDTO")
    void givenAExistingId_whenGetACoachByIdWithWrongIdInPath_thenThrowError() {
        String token = keycloakTestingUtility.getTokenFromTestingUser(UserRole.COACH);
        UUID USER_ID = UUID.fromString("1efd5bca-ce77-4f16-8d31-6f30205dd4e5");
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .port(port)
                .header("Authorization", "Bearer " + token)
                .get("/users/" + USER_ID)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }
}
