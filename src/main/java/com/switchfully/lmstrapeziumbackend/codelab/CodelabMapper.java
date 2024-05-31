package com.switchfully.lmstrapeziumbackend.codelab;

import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CreateCodelabDTO;
import com.switchfully.lmstrapeziumbackend.module.Module;
import com.switchfully.lmstrapeziumbackend.module.ModuleMapper;

public class CodelabMapper {
    public static Codelab toCodelab(CreateCodelabDTO createCodelabDTO, Module codelabParentModule) {
        return new Codelab(
                createCodelabDTO.name(),
                createCodelabDTO.description(),
                codelabParentModule
        );
    }

    public static CodelabDTO toDTO(Codelab savedCodelab) {
        return new CodelabDTO(savedCodelab.getId(),
                savedCodelab.getName(),
                savedCodelab.getDescription(),
                ModuleMapper.toDTO(savedCodelab.getModule()));
    }
}
