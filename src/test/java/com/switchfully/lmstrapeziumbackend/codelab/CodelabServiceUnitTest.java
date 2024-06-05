package com.switchfully.lmstrapeziumbackend.codelab;

import com.switchfully.lmstrapeziumbackend.exception.CodelabNotFoundException;
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
public class CodelabServiceUnitTest {
    @Mock
    CodelabRepository codelabRepository;

    @InjectMocks
    CodelabService codelabService;

    @Test
    @DisplayName("Given create a codelabDTO with a parent when parent is not in DB then error is thrown")
    void givenCodelabId_whenNotInDb_thenErrorThrown(){
        UUID id = UUID.randomUUID();

        Mockito.when(codelabRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> codelabService.getById(id)).isInstanceOf(CodelabNotFoundException.class)
                .hasMessage("Codelab with id '" + id + "' does not exist");
    }
}
