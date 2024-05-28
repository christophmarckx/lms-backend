package com.switchfully.lmstrapeziumbackend.module;

import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
import org.springframework.stereotype.Component;

@Component
public class ModuleMapper {
    public ModuleDTO toDTO(Module module) {
        ModuleDTO parentModule = null;
        if (module.getParentModule() != null){
            parentModule = this.toDTO(module.getParentModule());
        }
        return new ModuleDTO(
                module.getId(),
                module.getName(),
                parentModule);
    }
}
