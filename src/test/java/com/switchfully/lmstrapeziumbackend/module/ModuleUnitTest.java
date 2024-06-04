package com.switchfully.lmstrapeziumbackend.module;

import com.switchfully.lmstrapeziumbackend.exception.ModuleDoesNotExistException;
import com.switchfully.lmstrapeziumbackend.module.dto.CreateModuleDTO;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ModuleUnitTest {

    @Mock
    ModuleRepository moduleRepo;
    @InjectMocks
    ModuleService moduleService;

    @Test
    @DisplayName("Given create a module with a parent when parent is not in DB then error is thrown")
    void givenCreateModuleDTO_whenParentNotInDB_thenErrorThrown(){
        CreateModuleDTO moduleToTest = new CreateModuleDTO("Chimie", UUID.randomUUID());
        Mockito.when(moduleRepo.findById(moduleToTest.parentModuleId())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> moduleService.createModule(moduleToTest)).isInstanceOf(ModuleDoesNotExistException.class)
                .hasMessage("Module with id '" + moduleToTest.parentModuleId() + "' does not exist");
    }
}
