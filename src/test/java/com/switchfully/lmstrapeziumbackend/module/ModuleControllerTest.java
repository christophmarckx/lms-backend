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

import static org.assertj.core.api.Assertions.assertThat;
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
        List<Module> expectedModuleList = entityManager.createQuery("select m from Module m", Module.class).getResultList();
        assertThat(actualModuleDTOList).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedModuleList);
    }

    @Test
    @DisplayName("Given connected user, creating a module will return the module DTO")
    void givenConnectedUser_whenCreatingModule_returnModuleDTO(){
        String domain = "Gym";
        CreateModuleDTO createModuleDTO = new CreateModuleDTO(domain, null);
        Module myModule = new Module(domain, null);

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

        Module dbModule = entityManager.createQuery("select m from Module m where m.name= :name", Module.class)
                .setParameter("name", domain)
                .getSingleResult();


        assertThat(actualModuleDTO)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*id")
                .isEqualTo(new ModuleDTO(UUID.randomUUID(), domain, null));

        assertThat(dbModule).usingRecursiveComparison()
            .ignoringFieldsMatchingRegexes(".*id")
            .isEqualTo(myModule);
    }

}