package com.switchfully.lmstrapeziumbackend.module;

import com.switchfully.lmstrapeziumbackend.module.dto.CreateModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ModuleServiceIntegrationTest {
    @Autowired
    EntityManager entityManager;
    @Autowired
    ModuleService moduleService;

    @Test
    @DisplayName("Creating a module without a parent module given correct data will return a module DTO")
    void createModule_whenNoParentModule_thenModuleWellCreated () {
        CreateModuleDTO moduleToCreate = new CreateModuleDTO(
                "Chimie",
                null
        );
        ModuleDTO actualModuleDTO = moduleService.createModule(moduleToCreate);
        Module moduleExpected = entityManager.createQuery("select m from Module m where m.id=:id", Module.class)
                .setParameter("id", actualModuleDTO.id())
                .getSingleResult();
        assertThat(actualModuleDTO).usingRecursiveComparison().isEqualTo(moduleExpected);
    }

    @Test
    @DisplayName("Creating a module with a parent module given correct data will return a module DTO")
    void creatingModule_withParentModule_thenModuleCorrectlyCreated () {
        Module moduleToLink = entityManager.createQuery("select m from Module m ", Module.class).getResultList().getFirst();
        CreateModuleDTO moduleToCreate = new CreateModuleDTO(
                "Chimie",
                moduleToLink.getId()
        );
        ModuleDTO actualModuleDTO = moduleService.createModule(moduleToCreate);
        Module moduleExpected = entityManager.createQuery("select m from Module m where m.id=:id", Module.class)
                .setParameter("id", actualModuleDTO.id())
                .getSingleResult();
        assertThat(actualModuleDTO).usingRecursiveComparison().isEqualTo(moduleExpected);
    }
}
