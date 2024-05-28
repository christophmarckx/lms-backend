package com.switchfully.lmstrapeziumbackend.module;

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

//        List<Module> moduleListExpected = entityManager.createQuery("select m from Module m", Module.class).getResultList();
//        Module module1 = moduleListExpected.get(0);
//        Module module2 = moduleListExpected.get(1);
//        Module module3 = moduleListExpected.get(2);
//
//        Assertions.assertThat(actualModuleDTOList).extracting("id", "name")
//                .contains(tuple(module1.getId(), module1.getName()),
//                        tuple(module2.getId(), module2.getName()),
//                                tuple(module3.getId(), module3.getName()
//                                ));

        Assertions.assertThat(actualModuleDTOList).containsExactlyInAnyOrder(moduleDTO1, moduleDTO2, moduleDTO3);
    }
}