package com.switchfully.lmstrapeziumbackend.module;

import com.switchfully.lmstrapeziumbackend.module.dto.CreateModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
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
import org.springframework.http.HttpStatusCode;
import static io.restassured.http.ContentType.JSON;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class ModuleControllerTest {
    private static final String URI = "http://localhost";

    @LocalServerPort
    int localPort;

    @PersistenceContext
    @Autowired
    EntityManager entityManager;

    @Autowired
    ModuleMapper moduleMapper;

    @Test
    @DisplayName("Given connected user, getting all the modules will return a list of module DTO")
    void givenConnectedUser_whenGettingAllModules_thenReturnListOfModuleDTO() {
        ModuleDTO moduleDTO1 = new ModuleDTO(UUID.fromString("e0e8b090-df45-11ec-9d64-0242ac120002"), "Intro to Programming", null);
        ModuleDTO moduleDTO2 = new ModuleDTO(UUID.fromString("e0e8b091-df45-11ec-9d64-0242ac120002"), "Algorithms", moduleDTO1);
        ModuleDTO moduleDTO3 = new ModuleDTO(UUID.fromString("e0e8b092-df45-11ec-9d64-0242ac120002"), "Data Structures", moduleDTO1);
        List<ModuleDTO> actualModuleDTOList = RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .when()
                .get("/modules")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", ModuleDTO.class);

        Assertions.assertThat(actualModuleDTOList).containsExactlyInAnyOrder(moduleDTO1, moduleDTO2, moduleDTO3);
    }

    @Test
    @DisplayName("Given connected user, creating a module will return the module DTO")
    void givenConnectedUser_whenCreatingModule_returnModuleDTO(){
        CreateModuleDTO createModuleDTO = new CreateModuleDTO("Gym", null);

        ModuleDTO actualModuleDTO = RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .accept(JSON)
                .contentType(JSON)
                .body(createModuleDTO)
                .when()
                .post("/modules")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ModuleDTO.class);

        Assertions.assertThat(actualModuleDTO)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*id")
                .isEqualTo(new ModuleDTO(UUID.randomUUID(), "Gym", null));
    }
}