package com.switchfully.lmstrapeziumbackend.module.dto;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabDTO;

public record ModuleWithCodelabsDTO (   UUID id,
                                        String name,
                                        List<ModuleWithCodelabsDTO> modules,
                                        List<CodelabDTO> codelabs
){
}
