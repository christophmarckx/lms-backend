package com.switchfully.lmstrapeziumbackend.module;

import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
import org.springframework.stereotype.Component;

@Component
public class ModuleMapper {
    public ModuleDTO toDTO(Module module) {
        return new ModuleDTO(
                module.getId(),
                module.getName(),
                this.toDTO(module.getParentModule()));
    }
}
