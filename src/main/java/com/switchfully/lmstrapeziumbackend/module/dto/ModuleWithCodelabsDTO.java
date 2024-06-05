package com.switchfully.lmstrapeziumbackend.module.dto;

import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabDTO;

import java.util.List;
import java.util.UUID;

public record ModuleWithCodelabsDTO(UUID id,
                                    String name,
                                    List<ModuleWithCodelabsDTO> modules,
                                    List<CodelabDTO> codelabs
) {
}
